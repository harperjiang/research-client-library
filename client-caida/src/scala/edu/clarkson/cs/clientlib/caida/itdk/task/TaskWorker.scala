package edu.clarkson.cs.clientlib.caida.itdk.task

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.model.Partition

trait TaskWorker {

  var context: TaskContext;
  /**
   * Work on the current node.
   * Return The next node id(s) it wants to execute on
   */
  def execute(partition: Partition, node: Node): List[Node];

}