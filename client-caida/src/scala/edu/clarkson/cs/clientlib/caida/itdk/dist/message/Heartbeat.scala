package edu.clarkson.cs.clientlib.caida.itdk.dist.message

import scala.beans.BeanProperty

class Heartbeat {

  @BeanProperty
  var machineId = 0;
  @BeanProperty
  var groupId = 0;

  def this(gid: Int, mid: Int) = {
    this();
    machineId = mid;
    groupId = gid;
  }
}