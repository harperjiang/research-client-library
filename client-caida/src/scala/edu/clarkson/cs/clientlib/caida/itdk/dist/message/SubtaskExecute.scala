package edu.clarkson.cs.clientlib.caida.itdk.dist.message

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.task.Task

class SubtaskExecute(parent: Task) {

  var taskClass: Class[AnyRef] = null;
  var targetPartition: Int = 0;
  var targetNode: Node = null;

}