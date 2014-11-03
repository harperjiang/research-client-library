package edu.clarkson.cs.clientlib.caida.itdk.dist.message

import scala.beans.BeanProperty
import edu.clarkson.cs.clientlib.common.message.KVStore

class SubtaskResult {
  @BeanProperty
  var parentMachine = 0;
  @BeanProperty
  var parentTaskId = "";
  @BeanProperty
  var sourcePartitionId: Int = 0;
  @BeanProperty
  var result: KVStore = null;

  def this(pid: (Int, String), spid: Int, res: KVStore) = {
    this();
    this.parentMachine = pid._1;
    this.parentTaskId = pid._2;
    this.sourcePartitionId = spid;
    this.result = res;
  }
  def parentId = (parentMachine, parentTaskId);

}