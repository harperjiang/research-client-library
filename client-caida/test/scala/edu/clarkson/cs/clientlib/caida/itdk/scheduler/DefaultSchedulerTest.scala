package edu.clarkson.cs.clientlib.caida.itdk.scheduler

import org.junit.Assert._
import java.util.concurrent.Executors
import org.junit.Before
import org.junit.Test
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskContext
import java.util.concurrent.Semaphore
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskStatus

class DefaultSchedulerTest {

  var scheduler: DefaultScheduler = null;

  @Before
  def init = {
    scheduler = new DefaultScheduler();
    scheduler.threadPool = Executors.newFixedThreadPool(2);
  }

  @Test
  def testLocalTask = {

    var semaphore = new Semaphore(0);
    var received: Task = null;
    var slistener = new SchedulerListener() {
      def onTaskEnd(event: SchedulerEvent) = {
        received = event.task;
        semaphore.release();
      }
    };
    scheduler.addListener(slistener)

    var task = new Task((1, "taskid"), null);
    task.workerClass = classOf[LocalWorker];
    task.context = new TaskContext(null, null);

    scheduler.schedule(task);
    semaphore.acquire();

    assertEquals(received.id, task.id);
    assertEquals(TaskStatus.END,received.status);
  }
  
  @Test
  def testSpawnedTask = {
    
  }
}