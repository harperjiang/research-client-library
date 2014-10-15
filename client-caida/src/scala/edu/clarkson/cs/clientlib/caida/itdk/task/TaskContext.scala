package edu.clarkson.cs.clientlib.caida.itdk.task

import edu.clarkson.cs.clientlib.caida.itdk.dist.WorkerNode
import edu.clarkson.cs.clientlib.caida.itdk.model.Partition
import edu.clarkson.cs.clientlib.caida.itdk.scheduler.Scheduler

class TaskContext(w: WorkerNode, part: Partition) {

  val worker = w;
  val partition = part;

}