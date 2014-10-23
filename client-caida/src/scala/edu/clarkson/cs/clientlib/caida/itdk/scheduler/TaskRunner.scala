package edu.clarkson.cs.clientlib.caida.itdk.scheduler

import scala.collection.mutable.ArrayBuffer
import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskExecute

class TaskRunner(t: Task, cb: (Task, Exception) => Unit) extends Runnable {

  val task: Task = t;

  val callback = cb;

  override def run = {
    val worker = task.getWorker;
    val context = task.context;
    val partition = context.partition;
    val com = context.worker;
    worker.start(t);

    var toexecute = new ArrayBuffer[Node]();
    var exception: Exception = null;
    try {
      while (!toexecute.isEmpty) {
        var target = toexecute.remove(0);

        var tospawn = partition.queryPartition(target);
        if (!tospawn.isEmpty) {
          spawn(target.id, tospawn)
        }
        toexecute ++= worker.execute(t, target);
      }
    } catch {
      case e: Exception => {
        exception = e;
      }
    }
    task.hasError = (exception != null);

    if (task.spawned == 0) { worker.done(t); }
    callback(task, exception);
  }

  def spawn(nid: Int, dests: Iterable[Int]) = {
    // The destination may contain local partition number, need to skip it
    val localId = this.task.context.partition.id;

    dests.filter(_ != localId).foreach(dest => {
      // Create spawning task
      var sub = new SubtaskExecute(task, dest, nid);
      task.context.worker.sendSubtask(sub);
      task.spawned += 1;
    });
  }
}