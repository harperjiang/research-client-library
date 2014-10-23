package edu.clarkson.cs.clientlib.caida.itdk

import edu.clarkson.cs.clientlib.caida.itdk.dist.WorkerNode
import edu.clarkson.cs.clientlib.caida.itdk.model.Partition
import edu.clarkson.cs.clientlib.caida.itdk.scheduler.DefaultScheduler
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.dist.WorkerListener
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskResult
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskExecute
import edu.clarkson.cs.clientlib.caida.itdk.scheduler.SchedulerListener
import edu.clarkson.cs.clientlib.caida.itdk.scheduler.SchedulerEvent
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskContext

class WorkerUnit extends WorkerListener with SchedulerListener {

  val node = new WorkerNode();
  val partition = new Partition();
  val scheduler = new DefaultScheduler();

  node.addListener(this);
  scheduler.addListener(this);

  def submit(task: Task) {
    var ctx = new TaskContext(node, partition);
    task.context = ctx;
    // Submit the task into scheduler
    scheduler.schedule(task);
  }

  /**
   *  WorkerNode Listeners
   */
  override def onTaskSubmitted(task: SubtaskExecute) = {
    // submit the subtask to schedule

  }

  override def onTaskReturned(subtask: SubtaskResult) = {
    // Send the result to scheduler
    scheduler.collect(subtask.parentId, subtask.sourcePartitionId, subtask.result);
  }

  /**
   * Scheduler Listeners
   */
  def onTaskEnd(e: SchedulerEvent) = {
    //	On the completion of task
    // If this is a subtask, return it to original caller
    
  }
}