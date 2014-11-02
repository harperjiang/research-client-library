package edu.clarkson.cs.clientlib.caida.itdk

import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.junit.Test
import javax.annotation.Resource

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("classpath:test/app-context-worker.xml"))
class WorkUnitTest {

  @Resource
  var wu: WorkerUnit = null;

  @Test
  def testSubmitTask = {

  }

  @Test
  def testReceivedSubtask = {

  }
}