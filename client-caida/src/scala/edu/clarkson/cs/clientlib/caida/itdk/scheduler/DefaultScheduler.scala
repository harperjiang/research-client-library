package edu.clarkson.cs.clientlib.caida.itdk.scheduler

import java.util.concurrent.Executors
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.lang.Properties
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ConcurrentHashMap
import org.slf4j.LoggerFactory
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskStatus

class DefaultScheduler extends Scheduler {

  private val PROP = "scheduler.properties"

  private val threadPool = Executors.newWorkStealingPool(
    Properties.load[Int](PROP, "thread_pool_size"));

  private val waitingQueue = new ConcurrentHashMap[(Int, String), Task]();

  private val logger = LoggerFactory.getLogger(getClass());

  /**
   * Schedule a new task
   */
  def schedule(task: Task): Unit = {
    task.status = TaskStatus.ACTIVE;
    threadPool.submit(new TaskRunner(task, (t: Task, e: Exception) => {
      if (t.spawned != 0) {
        // has unreturned spawned 
        t.status = TaskStatus.WAIT_FOR_SUB;
        waitingQueue.put(t.id, t);
      } else {
        t.status = TaskStatus.END;
        this.onTaskEnd(t, e == null);
      }
    }));
  }

  /**
   * Collect result from spawned tasks
   */
  def collect(tid: (Int, String), fromPid: Int, result: String): Unit = {
    if (!waitingQueue.containsKey(tid)) {
      logger.warn("Requested task not found:%s".format(tid));
      return
    }
    var task = waitingQueue.get(tid)
    task.synchronized {
      if (task.spawned == 0) {
        return ;
      }
      task.spawned -= 1
      if (task.spawned == 0) {
        waitingQueue.remove(tid);
      }
      val remain = task.spawned;
      threadPool.submit(new CollectRunner(task, result, (t: Task, e: Exception) => {
        if (remain == 0) {
          t.status = TaskStatus.END;
          this.onTaskEnd(t, t.hasError);
        }
      }));
    };
  }
}