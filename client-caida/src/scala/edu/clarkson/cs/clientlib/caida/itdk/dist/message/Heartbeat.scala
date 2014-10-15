package edu.clarkson.cs.clientlib.caida.itdk.dist.message

class Heartbeat(gid: Int, mid: Int) {

  val machineId = mid;
  val groupId = gid;

}