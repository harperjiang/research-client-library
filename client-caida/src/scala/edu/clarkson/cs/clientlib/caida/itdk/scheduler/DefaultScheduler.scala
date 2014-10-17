package edu.clarkson.cs.clientlib.caida.itdk.scheduler

import java.util.concurrent.Executors

import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.lang.Properties

class DefaultScheduler extends Scheduler {

  private val PROP = "scheduler.properties"

  private val threadPool = Executors.newWorkStealingPool(
    Properties.load[Int](PROP, "thread_pool_size"));

  def schedule(task: Task) = {
    threadPool.submit(new TaskRunner(task, (success: Boolean) => {
      this.onTaskEnd(task, success, task.getWorker.context);
    }));
  }

  def collect(tid: String, pid: Int, result: Any) = {

  }
}