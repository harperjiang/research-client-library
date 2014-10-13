package edu.clarkson.cs.clientlib.caida.itdk.model

/**
 * A Task is a vertex program
 */

trait Task {

  /**
   * The start node id.
   */
  def start: Int;

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