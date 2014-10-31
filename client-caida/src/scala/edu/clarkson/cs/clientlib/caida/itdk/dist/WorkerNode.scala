package edu.clarkson.cs.clientlib.caida.itdk.dist

import scala.collection.mutable.ArrayBuffer
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.Heartbeat
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskExecute
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskResult
import edu.clarkson.cs.clientlib.lang.Properties
import edu.clarkson.cs.clientlib.message.Sender
import edu.clarkson.cs.clientlib.lang.EventListenerSupport
import java.util.EventListener
import org.slf4j.LoggerFactory
import javax.jms.Message
import org.springframework.beans.factory.InitializingBean

class WorkerNode extends Sender with EventListenerSupport[WorkerListener] with InitializingBean {

  var groupId = 0;
  var machineId = 0;
  var hbInterval = 2000;

  private val logger = LoggerFactory.getLogger(getClass());

  def afterPropertiesSet() = {
    val heartbeatThread = new Thread() {
      {
        setName("Worker-Heartbeat");
        setDaemon(true);
      }

      override def run = {
        while (true) {
          try {
            sendHeartbeat;
            Thread.sleep(hbInterval);
          } catch {
            case e: Exception => {
              logger.error("Error occurred in heartbeat thread", e);
            }
          }
        }
      }
    };
    heartbeatThread.start();
  }

  def sendHeartbeat = {
    var hb = new Heartbeat(groupId, machineId);
    send("heartbeatDest", hb);
  }

  def sendSubtaskRequest(task: SubtaskExecute) = {
    send("workRequestDest", task)(m => { m.setIntProperty("targetPartition", task.targetPartition) });
    listeners.foreach(_.onRequestSent(task));
  }

  def sendSubtaskResponse(task: SubtaskResult) = {
    send("workResponseDest", task)(m => { m.setIntProperty("targetMachine", task.parentId._1) });
    listeners.foreach(_.onResponseSent(task));
  }

  def onRequestReceived(task: SubtaskExecute) = {
    listeners.foreach(_.onRequestReceived(task));
  }

  def onResponseReceived(task: SubtaskResult) = {
    listeners.foreach(_.onResponseReceived(task));
  }
}

trait WorkerListener extends EventListener {

  def onRequestSent(task: SubtaskExecute) = {};

  def onRequestReceived(task: SubtaskExecute) = {};

  def onResponseSent(task: SubtaskResult) = {};

  def onResponseReceived(task: SubtaskResult) = {};
}