package edu.clarkson.cs.clientlib.caida.itdk.dist.message

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskWorker

class SubtaskExecute(parent: Task, tpid: Int, tnid: Int) {

  var parentId: String = parent.id;
  var workerClass: Class[TaskWorker] = parent.workerClass;
  var targetPartition: Int = tpid;
  var targetNodeId: Int = tnid;

}