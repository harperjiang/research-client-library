package edu.clarkson.cs.clientlib.caida.itdk.dist.message

class SubtaskResult(pid: (Int, String), res: String) {

  var parentId: (Int, String) = pid;
  var sourcePartitionId: Int = 0;
  var result: String = res;

}