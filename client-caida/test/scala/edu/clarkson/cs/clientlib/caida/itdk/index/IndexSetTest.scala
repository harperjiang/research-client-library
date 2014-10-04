package edu.clarkson.cs.clientlib.caida.itdk.index;

import java.io.FileInputStream
import java.io.ObjectInputStream
import org.junit.Assert.assertEquals
import org.junit.Test
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink

class IndexSetTest {

  @Test
  def testBuild = {
    var indexSet = new IndexSet("testdata/index", 10);
    var parser = new Parser();
    indexSet build ("testdata/kapar-midar-iff.linknodes",
      line => { !line.startsWith("#") },
      input => { var nl = parser.parse(input).asInstanceOf[NodeLink]; nl.node })

    var ois = new ObjectInputStream(new FileInputStream("testdata/index/root"));

    var head = ois.readObject().asInstanceOf[IndexNode];
    ois.close
    assertEquals(10210764, head.max)
    assertEquals(1, head.min)

    assertEquals(2, head.depth)

    var leaf1 = new ObjectInputStream(new FileInputStream("testdata/index/leaf_0"));
    var leaf1head = leaf1.readObject.asInstanceOf[IndexNode];
    leaf1.close
    assertEquals(5, leaf1head.depth);
  }
}
