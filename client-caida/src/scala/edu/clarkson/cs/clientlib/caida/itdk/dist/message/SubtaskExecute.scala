package edu.clarkson.cs.clientlib.caida.itdk.dist.message

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskWorker

class SubtaskExecute(parent: Task) {

  var parentId: String = null;
  var workerClass: Class[TaskWorker] = null;
  var targetPartition: Int = 0;
  var targetNodeId: Int = 0;

}