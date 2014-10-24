package edu.clarkson.cs.clientlib.caida.itdk.dist

import org.junit.Test
import edu.clarkson.cs.clientlib.caida.itdk.dist.message.Heartbeat
import org.junit.Assert._;

class MasterNodeTest {

  @Test
  def testInvalidRange = {

    var masterNode = new MasterNode();
    try {
      masterNode.onHeartbeat(new Heartbeat(0, -1));
      fail("");
    } catch {
      case ex: Exception => {
      }
    }
  }

  @Test
  def testStateChange = {
    var masterNode = new MasterNode();

    assertEquals(MachineStatus.UNKNOWN, masterNode.status(1));
    masterNode.onHeartbeat(new Heartbeat(0, 1))
    assertEquals(MachineStatus.UNSTABLE, masterNode.status(1));
    for (i <- 1 to 59) {
      masterNode.onHeartbeat(new Heartbeat(0, 1))
      assertEquals(MachineStatus.UNSTABLE, masterNode.status(1));
    }
    masterNode.onHeartbeat(new Heartbeat(0, 1))
    assertEquals(MachineStatus.NORMAL, masterNode.status(1));

    Thread.sleep(4000);
    masterNode.onHeartbeat(new Heartbeat(0, 1))
    assertEquals(MachineStatus.UNSTABLE, masterNode.status(1));

  }

}