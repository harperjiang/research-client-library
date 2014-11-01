package edu.clarkson.cs.clientlib.caida.itdk.dist.message

import scala.beans.BeanProperty

class Heartbeat(gid: Int, mid: Int) {

  @BeanProperty
  val machineId = mid;
  @BeanProperty
  val groupId = gid;

}