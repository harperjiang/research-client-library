package edu.clarkson.cs.clientlib.caida.itdk.scheduler

import java.util.EventListener
import java.util.EventObject

import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskContext
import edu.clarkson.cs.clientlib.lang.EventListenerSupport

trait Scheduler extends EventListenerSupport[SchedulerListener] {

  def schedule(task: Task);

  def collect(taskId: (Int, String), fromPartition: Int, result: String);

  protected def onTaskEnd(task: Task, success: Boolean) {
    val e = new SchedulerEvent(this, task, success);
    listeners.foreach(l => l.onTaskEnd(e));
  }
}

trait SchedulerListener extends EventListener {
  def onTaskEnd(event: SchedulerEvent);
}

class SchedulerEvent(scheduler: Scheduler, t: Task, suc: Boolean)
  extends EventObject(scheduler) {
  val success = suc;
  val task = t;
}