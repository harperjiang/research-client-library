package edu.clarkson.cs.clientlib.caida.itdk.task

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.model.Partition

trait TaskWorker {

  var context: TaskContext;

  /**
   * Callback when the worker is to be started
   */
  def start();

  /**
   * Work on the current node.
   * Return The next node id(s) it wants to execute on
   */
  def execute(node: Node): Iterable[Node];

  /**
   * Callback when the task is done
   */
  def done(error: Boolean);
}