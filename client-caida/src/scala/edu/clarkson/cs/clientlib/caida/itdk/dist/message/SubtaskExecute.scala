package edu.clarkson.cs.clientlib.caida.itdk.dist.message

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskWorker
import scala.beans.BeanProperty

class SubtaskExecute {
  @BeanProperty
  var parentMachine = 0;
  @BeanProperty
  var parentTaskId = "";
  @BeanProperty
  var workerClass = "";
  @BeanProperty
  var targetPartition = 0;
  @BeanProperty
  var targetNodeId = 0;

  def this(parent: Task, tpid: Int, tnid: Int) = {
    this();
    this.parentMachine = parent.id._1;
    this.parentTaskId = parent.id._2;
    this.workerClass = parent.workerClass.getName();
    this.targetNodeId = tnid;
    this.targetPartition = tpid;
  }

  def parentId = (parentMachine, parentTaskId);
}