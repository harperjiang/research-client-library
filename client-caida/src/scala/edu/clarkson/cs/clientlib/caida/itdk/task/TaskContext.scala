package edu.clarkson.cs.clientlib.caida.itdk.task

import edu.clarkson.cs.clientlib.caida.itdk.dist.WorkerNode
import edu.clarkson.cs.clientlib.caida.itdk.model.Partition
import edu.clarkson.cs.clientlib.caida.itdk.scheduler.Scheduler
import java.util.HashMap
import edu.clarkson.cs.clientlib.common.message.KVStore

class TaskContext(w: WorkerNode, part: Partition) {

  val worker = w;
  val partition = part;
  val result = new KVStore;
}