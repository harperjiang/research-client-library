package edu.clarkson.cs.clientlib.caida.itdk.dist.message

import scala.beans.BeanProperty
import edu.clarkson.cs.clientlib.common.message.KVStore

class SubtaskResult(pid: (Int, String), res: KVStore) {
  @BeanProperty
  var parentId: (Int, String) = pid;
  @BeanProperty
  var sourcePartitionId: Int = 0;
  @BeanProperty
  var result = res;

}