package edu.clarkson.cs.clientlib.caida.itdk.dist

import edu.clarkson.cs.clientlib.caida.itdk.dist.message.Heartbeat
import java.util.concurrent.ConcurrentHashMap
import edu.clarkson.cs.clientlib.lang.Properties
import scala.collection.JavaConversions._
import org.slf4j.LoggerFactory

/**
 * <code>MasterNode</code> is the control plane of the system. It can report status of each machine.
 */
class MasterNode {

  private val MASTER_PROP = "master.properties";

  private val machineStatus = new ConcurrentHashMap[Int, MachineState]();

  private val maxMachineId = Properties.load[Int](MASTER_PROP, "max_machine_id");

  private val abnormalInterval = Properties.load[Long](MASTER_PROP, "abnormal_interval");

  private val downInterval = Properties.load[Long](MASTER_PROP, "down_interval");

  private val stableCount = Properties.load[Int](MASTER_PROP, "stable_count");

  private val logger = LoggerFactory.getLogger(getClass());

  for (i <- 1 to maxMachineId)
    machineStatus.put(i, new MachineState(MachineStatus.UNKNOWN, -1, 0));

  // Start a monitor thread
  new DaemonThread(() => {
    detectdown
  }).start();

  def status(machineId: Int): MachineStatus.T = {
    if (machineId > maxMachineId || machineId < 0)
      throw new IllegalArgumentException("Out of range");
    return machineStatus.get(machineId).status;
  }

  def onHeartbeat(hb: Heartbeat): Unit = {
    if (hb.machineId > maxMachineId || hb.machineId < 0) {
      var msg = "Invalid machine id received:%d".format(hb.machineId)
      logger.warn(msg);
      throw new IllegalArgumentException(msg);
    }
    val record = machineStatus.get(hb.machineId);

    record.synchronized({
      update(record);
    })
  }

  private def update(record: MachineState): Unit = {
    val current = System.currentTimeMillis();
    val interval = current - record.lastUpdate;
    record.status match {
      case MachineStatus.UNKNOWN => { record.update(MachineStatus.UNSTABLE, current, 1) }
      case MachineStatus.NORMAL => {
        if (interval < abnormalInterval)
          record.update(MachineStatus.NORMAL, current, record.counter + 1)
        else record.update(MachineStatus.UNSTABLE, current, 1)
      }
      case MachineStatus.DOWN => { record.update(MachineStatus.UNSTABLE, current, 1) }
      case MachineStatus.UNSTABLE => {
        if (interval < abnormalInterval) {
          if (record.counter >= stableCount) record.update(MachineStatus.NORMAL, current, 1)
          else record.update(MachineStatus.UNSTABLE, current, record.counter + 1)
        } else record.update(MachineStatus.UNSTABLE, current, 1);
      }
    }
  }

  private def detectdown = {
    val current = System.currentTimeMillis();
    for (record <- machineStatus.values()) {
      record.synchronized({
        if (current - record.lastUpdate > downInterval) {
          record.update(MachineStatus.DOWN, current, 1)
        }
      })
    }
  }
}

class MachineState(mt: MachineStatus.T, lu: Long, c: Int) {
  var status: MachineStatus.T = mt
  var lastUpdate = lu
  var counter = c

  def update(mt: MachineStatus.T, lu: Long, c: Int) = {
    status = mt;
    lastUpdate = lu;
    counter = c;
  }
}

class DaemonThread(f: () => Unit) extends Thread {

  private val task = f;
  private val interval = 5000;
  setDaemon(true);
  setName("MasterNode-DaemonThread");

  override def run = {
    while (true) {
      Thread.sleep(interval);
      task
    }
  }
}

object MachineStatus extends Enumeration {
  type T = Value
  val NORMAL, DOWN, UNSTABLE, UNKNOWN = Value
};