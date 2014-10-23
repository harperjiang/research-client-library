package edu.clarkson.cs.clientlib.caida.itdk.dist

import scala.collection.mutable.ArrayBuffer
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.Heartbeat
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskExecute
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskResult
import edu.clarkson.cs.clientlib.lang.Properties
import edu.clarkson.cs.clientlib.message.Sender
import edu.clarkson.cs.clientlib.lang.EventListenerSupport
import java.util.EventListener

class WorkerNode extends Sender with EventListenerSupport[WorkerListener] {

  private val WORKER_PROP = "worker.properties";

  val groupId = Properties.load[Int](WORKER_PROP, "group_id");
  val machineId = Properties.load[Int](WORKER_PROP, "machine_id");

  def sendHeartbeat = {
    var hb = new Heartbeat(groupId, machineId);
    send(hb);
  }

  def sendSubtask(task: SubtaskExecute) = {
    send(task);
    listeners.foreach(_.onTaskSent(task));
  }

  def onSubtaskSubmitted(task: SubtaskExecute) = {
    listeners.foreach(_.onTaskSubmitted(task));
  }

  def onSubtaskReturned(task: SubtaskResult) = {
    listeners.foreach(_.onTaskReturned(task));
  }
}

trait WorkerListener extends EventListener {

  def onTaskSent(task: SubtaskExecute) = {};

  def onTaskSubmitted(task: SubtaskExecute) = {};

  def onTaskReturned(task: SubtaskResult) = {};

}