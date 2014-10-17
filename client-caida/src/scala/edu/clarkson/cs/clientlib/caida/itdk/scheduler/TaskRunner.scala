package edu.clarkson.cs.clientlib.caida.itdk.scheduler

import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import scala.collection.mutable.ArrayBuffer
import edu.clarkson.cs.clientlib.caida.itdk.model.Node

class TaskRunner(t: Task, cb: (Boolean) => Unit) extends Runnable {

  private val task: Task = t;

  private val callback = cb;

  override def run = {
    var worker = task.getWorker;
    var context = worker.context;
    context.partition
    
    worker.start();

    var toexecute = new ArrayBuffer[Node]();

    while (!toexecute.isEmpty) {
      var newnodes = worker.execute(toexecute.remove(0));
      newnodes.foreach(nn => {
    	  
      })
    }

    var success = false;
    worker.done(success);
    cb(success);
  }
}