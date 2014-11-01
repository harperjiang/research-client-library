package edu.clarkson.cs.clientlib.caida.itdk.dist.message

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskWorker
import scala.beans.BeanProperty

class SubtaskExecute(parent: Task, tpid: Int, tnid: Int) {
  @BeanProperty
  var parentId: (Int, String) = parent.id;
  @BeanProperty
  var workerClass: Class[TaskWorker] = parent.workerClass;
  @BeanProperty
  var targetPartition: Int = tpid;
  @BeanProperty
  var targetNodeId: Int = tnid;

}