package edu.clarkson.cs.clientlib.caida.itdk

import java.util.UUID
import org.springframework.beans.factory.InitializingBean
import edu.clarkson.cs.clientlib.caida.itdk.dist.WorkerListener
import edu.clarkson.cs.clientlib.caida.itdk.dist.WorkerNode
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskExecute
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskResult
import edu.clarkson.cs.clientlib.caida.itdk.model.Partition
import edu.clarkson.cs.clientlib.caida.itdk.scheduler.Scheduler
import edu.clarkson.cs.clientlib.caida.itdk.scheduler.SchedulerEvent
import edu.clarkson.cs.clientlib.caida.itdk.scheduler.SchedulerListener
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskContext
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskWorker
import org.springframework.context.support.ClassPathXmlApplicationContext

class WorkerUnit extends WorkerListener with SchedulerListener with InitializingBean {

  var node: WorkerNode = null;
  var partition: Partition = null;
  var scheduler: Scheduler = null;

  def afterPropertiesSet() = {
    node.addListener(this);
    scheduler.addListener(this);
  }

  def submit(task: Task): Unit = {
    // Assign valid task id
    task.id = taskId
    var ctx = new TaskContext(node, partition);
    task.context = ctx;
    // Submit the task into scheduler
    scheduler.schedule(task);
  }

  def submit(worker: Class[TaskWorker], startNode: Int): Unit = {
    var task = new Task;
    task.startNodeId = startNode;
    submit(task);
  }

  /**
   *  WorkerNode Listeners
   */
  override def onRequestReceived(stask: SubtaskExecute) = {
    // submit the subtask to schedule
    var subtask = new Task(stask.parentId);
    subtask.workerClass = Class.forName(stask.workerClass).asInstanceOf[Class[TaskWorker]];
    subtask.startNodeId = stask.targetNodeId;

    submit(subtask);
  }

  override def onResponseReceived(subtask: SubtaskResult) = {
    // Send the result to scheduler
    scheduler.collect(subtask.parentId, subtask.sourcePartitionId, subtask.result);
  }

  /**
   * Scheduler Listeners
   */
  def onTaskEnd(e: SchedulerEvent) = {
    // On the completion of task
    // If this is a subtask, return it to original caller
    e.task.parent match {
      case pid if (pid != null) => {
        // Non-empty parent, subtask, should be returned to original partition
        var resp = new SubtaskResult(pid, partition.id, e.task.context.result);
        node.sendSubtaskResponse(resp);
      }
      case _ => {
        // Normal task, no need to handle it now

      }
    }
  }

  def taskId: (Int, String) = {
    (node.machineId, UUID.randomUUID().toString())
  }
}


object RunWorker extends App {
  var appContext = new ClassPathXmlApplicationContext("app-context-worker.xml");
}