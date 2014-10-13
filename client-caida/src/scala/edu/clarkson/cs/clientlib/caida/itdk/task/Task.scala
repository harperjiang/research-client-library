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
  def id: Int;
  
  /**
   *
   */
  def parent: Int;
  
  /**
   * The start node id.
   */
  def start: Int;

  /**
   * Current Status
   */
  def status: TaskStatus.TaskStatus;
  
  /**
   * Work on the current node
   */
  def execute(partition: Partition, node: Node);

  /**
   * The next node id(s) it wants to execute on
   */
  def propagate: List[Node];

  /**
   * Whether the task is finished
   */
  def done: Boolean;
}
