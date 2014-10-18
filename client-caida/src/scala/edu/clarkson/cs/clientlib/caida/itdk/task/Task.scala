package edu.clarkson.cs.clientlib.caida.itdk.task

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

/**
 * A Task is a vertex program
 */

object TaskStatus extends Enumeration {
  type T = Value
  val READY, ACTIVE, WAIT_FOR_SUB, END = Value
};

class Task(tid: String, pid: String) {

  /**
   * Task identification
   */
  var id: String = tid;

  /**
   * Subtask only, parent id
   */
  var parent: String = pid;

  /**
   * Task Status
   */
  var status: TaskStatus.T = TaskStatus.READY;

  /**
   * How many items has been spawned from it
   */
  var spawned: Int = 0;

  /**
   * Has error in execution
   */
  var hasError: Boolean = false;
  
  /**
   * Worker for task execution
   */
  var workerClass: Class[TaskWorker] = null;

  /**
   * Task Context
   */
  var context: TaskContext = null;

  @transient
  private var worker: Option[TaskWorker] = None;

  def getWorker: TaskWorker = {
    worker.getOrElse({ worker = Some(workerClass.newInstance()); worker.get; });
  }
}
