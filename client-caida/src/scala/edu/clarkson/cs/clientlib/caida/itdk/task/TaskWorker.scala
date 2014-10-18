package edu.clarkson.cs.clientlib.caida.itdk.task

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.model.Partition

trait TaskWorker {

  /**
   * Callback when the worker is to be started
   */
  def start(t: Task);

  /**
   * Work on the current node.
   * Return The next node id(s) it wants to execute on
   */
  def execute(t: Task, node: Node): Iterable[Node];

  /**
   * Collect result from spawned processes
   */
  def collect(t: Task, result: Any);

  /**
   * Callback when the task is done
   */
  def done(t: Task);
}