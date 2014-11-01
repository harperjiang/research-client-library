package edu.clarkson.cs.clientlib.caida.itdk.task

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.model.Partition
import edu.clarkson.cs.clientlib.common.message.KVStore

/**
 * <code>TaskWorker</code> is the interface provided to users who want to
 * implement their own vertex programs.
 */
trait TaskWorker {

  /**
   * Callback when the worker is to be started
   */
  def start(t: Task);

  /**
   * Work on the current node.
   * Return The next node(s) it wants to execute on
   */
  def execute(t: Task, node: Node): Iterable[Node];

  /**
   * Collect result from spawned processes
   */
  def collect(t: Task, result: KVStore);

  /**
   * Callback when the task is done
   */
  def done(t: Task);
}