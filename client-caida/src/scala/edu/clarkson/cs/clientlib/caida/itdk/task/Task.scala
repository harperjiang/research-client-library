package edu.clarkson.cs.clientlib.caida.itdk.task

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.model.Partition

/**
 * A Task is a vertex program
 */

object TaskStatus extends Enumeration {
  type TaskStatus = Value
  val READY, ACTIVE, WAIT_FOR_SUB, END, ERROR = Value
};

trait Task {

  /**
   * Task identification
   */
  def id: String;

  /**
   * Subtask only, parent id
   */
  def parent: String;

  /**
   * The start node id.
   */
  def start: Int;

  /**
   * Current Status
   */
  def status: TaskStatus.TaskStatus;

  /**
   * Update Status
   */
  def changeStatus(status: TaskStatus.TaskStatus);

  /**
   * Worker for task execution
   */
  def worker: TaskWorker;

}
