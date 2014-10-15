package edu.clarkson.cs.clientlib.caida.itdk.scheduler

import java.util.EventListener
import java.util.EventObject

import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskContext
import edu.clarkson.cs.clientlib.lang.EventListenerSupport

trait Scheduler extends EventListenerSupport[SchedulerListener] {

  def schedule(task: Task, context: TaskContext);

  def collect(taskId: String, fromPartition: Int, result: Any);

  protected def onTaskEnd(task: Task, success: Boolean, context: TaskContext) {
    val e = new SchedulerEvent(this, task, success, context);
    listeners.foreach(l => l.onTaskEnd(e));
  }
}

trait SchedulerListener extends EventListener {
  def onTaskEnd(event: SchedulerEvent);
}

class SchedulerEvent(scheduler: Scheduler, t: Task, suc: Boolean, c: TaskContext)
  extends EventObject(scheduler) {
  val success = suc;
  val task = t;
  val context = c;
}