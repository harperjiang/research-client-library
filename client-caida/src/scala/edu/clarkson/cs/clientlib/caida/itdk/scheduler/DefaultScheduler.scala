package edu.clarkson.cs.clientlib.caida.itdk.scheduler

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import org.slf4j.LoggerFactory
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskStatus
import edu.clarkson.cs.clientlib.common.message.KVStore

class DefaultScheduler extends Scheduler {

  var threadPool: ExecutorService = null;
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
  def collect(tid: (Int, String), fromPid: Int, result: KVStore): Unit = {
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