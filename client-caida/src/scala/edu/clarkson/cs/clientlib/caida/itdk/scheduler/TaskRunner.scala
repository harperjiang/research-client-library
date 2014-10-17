package edu.clarkson.cs.clientlib.caida.itdk.scheduler

import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import scala.collection.mutable.ArrayBuffer
import edu.clarkson.cs.clientlib.caida.itdk.model.Node

class TaskRunner(t: Task, cb: (Boolean) => Unit) extends Runnable {

  private val task: Task = t;

  private val callback = cb;

  private var spawned = false;

  override def run = {
    val worker = task.getWorker;
    val context = worker.context;
    val partition = context.partition;
    val com = context.worker ;
    worker.start();

    var toexecute = new ArrayBuffer[Node]();

    while (!toexecute.isEmpty) {
      var newnodes = worker.execute(toexecute.remove(0));
      newnodes.foreach(nn => {
        val tospawn = partition.queryPartition(nn);
        tospawn.foreach(dest => {
        	
        });
      })
    }

    var success = false;
    worker.done(success);
    cb(success);
  }
}