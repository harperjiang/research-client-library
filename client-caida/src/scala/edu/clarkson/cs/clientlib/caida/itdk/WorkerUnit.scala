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
import java.util.UUID
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskWorker

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

  def submit(worker: Class[TaskWorker]) = {

  }

  /**
   *  WorkerNode Listeners
   */
  override def onTaskSubmitted(stask: SubtaskExecute) = {
    // submit the subtask to schedule
    var subtask = new Task(taskId, stask.parentId);
    subtask.workerClass = stask.workerClass;
    subtask.startNodeId = stask.targetNodeId;

    submit(subtask);
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
    e.task.parent match {
      case pid if (!pid.isEmpty()) => {
        // Non-empty parent, subtask, should be returned to original partition
    	
        
      }
      case _ => {
        // Normal task, no need to handle it now
        
      }
    }
  }

  def taskId: String = {
    "%d_%s".format(partition.id, UUID.randomUUID().toString())
  }
}