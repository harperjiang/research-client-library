package edu.clarkson.cs.clientlib.caida.itdk.dist

import scala.collection.mutable.ArrayBuffer

import edu.clarkson.cs.clientlib.caida.itdk.dist.message.Heartbeat
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskExecute
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskResult
import edu.clarkson.cs.clientlib.lang.Properties
import edu.clarkson.cs.clientlib.message.Sender

class WorkerNode extends Sender {

  private val WORKER_PROP = "worker.properties";

  val groupId = Properties.load[Int](WORKER_PROP, "group_id");
  val machineId = Properties.load[Int](WORKER_PROP, "machine_id");

  var listeners = new ArrayBuffer[WorkerListener]();

  def sendHeartbeat = {
    var hb = new Heartbeat(groupId, machineId);
    send(hb);
  }

  def sendSubtask(task: SubtaskExecute) = {
    send(task);
    for (listener <- listeners)
      listener.taskSent(task);
  }

  def onSubtask(task: SubtaskResult) = {
    for (listener <- listeners)
      listener.taskReceived(task);
  }
}

trait WorkerListener {

  def taskSent(task: SubtaskExecute);

  def taskReceived(task: SubtaskResult);

}