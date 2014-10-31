package edu.clarkson.cs.clientlib.caida.itdk.dist

import org.junit.Assert._
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.Heartbeat
import javax.annotation.Resource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import edu.clarkson.cs.clientlib.caida.itdk.DummyJmsTemplate

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("classpath:test/app-context-worker.xml"))
class WorkerNodeTest {

  @Resource(name = "workerNode")
  var workerNode: WorkerNode = null;

  @Resource(name = "")
  var djt: DummyJmsTemplate = null;

  @Test
  def testHeartbeat = {
    Thread.sleep(5000);
    assertEquals(3, djt.getCounter().get("heartbeatDest"));
  }

}