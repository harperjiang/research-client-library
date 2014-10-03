package edu.clarkson.cs.clientlib.caida.itdk.index;

import org.junit.Test

class IndexSetTest {

  @Test
  def testBuild = {
    var indexSet = new IndexSet("testdata/index", 10);
    indexSet build ("testdata/kapar-midar-iff.linknodes")
  }
}
