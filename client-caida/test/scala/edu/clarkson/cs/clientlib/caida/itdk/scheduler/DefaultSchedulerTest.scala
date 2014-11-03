package edu.clarkson.cs.clientlib.caida.itdk.scheduler

import java.util.concurrent.Executors
import java.util.concurrent.Semaphore

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration

import edu.clarkson.cs.clientlib.caida.itdk.dist.WorkerListener
import edu.clarkson.cs.clientlib.caida.itdk.dist.WorkerNode
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskExecute
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.SubtaskResult
import edu.clarkson.cs.clientlib.caida.itdk.model.Partition
import edu.clarkson.cs.clientlib.caida.itdk.model.routing.DefaultRouting
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskContext
import edu.clarkson.cs.clientlib.caida.itdk.task.TaskStatus
import edu.clarkson.cs.clientlib.common.message.KVStore
import javax.jms.Message

class DefaultSchedulerTest {

  var scheduler: DefaultScheduler = null;

  var partition: Partition = null;

  var workerNode: WorkerNode = null;

  @Before
  def init = {
    scheduler = new DefaultScheduler();
    scheduler.threadPool = Executors.newFixedThreadPool(2);

    partition = new Partition();
    partition.nodeFile = "testdata/nodes";
    partition.linkFile = "testdata/links";
    partition.id = 1;

    var routing = new DefaultRouting();
    routing.routingFile = "testdata/routing";
    partition.routing = routing;
    routing.afterPropertiesSet;
    partition.afterPropertiesSet;

    workerNode = new WorkerNode {
      override def send(dest: String, message: (Object, Message => Unit)) = {
    	  // Do nothing for message sending
      }
    }
    workerNode.machineId = 1;
    workerNode.groupId = 1;
    workerNode.hbInterval = 1000;
    workerNode.afterPropertiesSet;
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
    task.startNodeId = 1;
    task.workerClass = classOf[LocalWorker];
    task.context = new TaskContext(null, partition);

    scheduler.schedule(task);
    semaphore.acquire();

    assertEquals(received.id, task.id);
    assertEquals(TaskStatus.END, received.status);
  }

  @Test
  def testSpawnedTask = {
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
    task.startNodeId = 7;
    task.workerClass = classOf[LocalWorker];
    task.context = new TaskContext(workerNode, partition);

    var targetPartition = 0;
    var targetNodeId = 0;

    workerNode.addListener(new WorkerListener() {
      override def onRequestSent(task: SubtaskExecute) = {
        targetPartition = task.targetPartition;
        targetNodeId = task.targetNodeId;
        semaphore.release();
      }

      override def onResponseReceived(task: SubtaskResult) = {
        scheduler.collect(task.parentId, task.sourcePartitionId, task.result);
        semaphore.release();
      }
    });

    scheduler.schedule(task);
    semaphore.acquire();

    Thread.sleep(1000);
    // Now the task should be suspended for subtask return
    assertEquals(2, targetPartition);
    assertEquals(7, targetNodeId);
    assertEquals(TaskStatus.WAIT_FOR_SUB, task.status);

    var subresult = new SubtaskResult((1, "taskid"), targetPartition, new KVStore());

    workerNode.onResponseReceived(subresult);

    semaphore.acquire(2);

    assertEquals(received.id, task.id);
    assertEquals(TaskStatus.END, received.status);
  }
}