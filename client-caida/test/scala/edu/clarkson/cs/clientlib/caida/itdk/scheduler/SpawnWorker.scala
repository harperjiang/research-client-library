package edu.clarkson.cs.clientlib.caida.itdk.scheduler

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskWorker
import edu.clarkson.cs.clientlib.common.message.KVStore

class SpawnWorker extends TaskWorker {

  def start(t: Task) = {

  }
  /**
   * Work on the current node.
   * Return The next node(s) it wants to execute on
   */
  def execute(t: Task, node: Node): Iterable[Node] = {
    return List[Node]();
  }

  /**
   * Collect result from spawned processes
   */
  def collect(t: Task, result: KVStore) = {

  }

  /**
   * Callback when the task is done
   */
  def done(t: Task) = {

  }
}