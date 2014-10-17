package edu.clarkson.cs.clientlib.caida.itdk.task

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

/**
 * A Task is a vertex program
 */

object TaskStatus extends Enumeration {
  type T = Value
  val READY, ACTIVE, WAIT_FOR_SUB, END, ERROR = Value
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

  var status: TaskStatus.T = TaskStatus.READY;

  /**
   * Worker for task execution
   */
  var workerClass: Class[TaskWorker] = null;

  @transient
  private var worker: Option[TaskWorker] = None;

  def getWorker: TaskWorker = {
    worker.getOrElse({ worker = Some(workerClass.newInstance()); worker.get; });
  }
}
