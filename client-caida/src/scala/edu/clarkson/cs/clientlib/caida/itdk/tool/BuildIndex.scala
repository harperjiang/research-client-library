package edu.clarkson.cs.clientlib.caida.itdk.tool

import edu.clarkson.cs.clientlib.caida.itdk.index.IndexSet
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import edu.clarkson.cs.clientlib.caida.itdk.index.IndexNode
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink

object BuildIndex extends App {

  var parser = new Parser();
  val ib = new IndexSet("/home/harper/caida_data/topo-data.caida.org/ITDK/ITDK-2014-04/linknodes.index");
  ib.build("/home/harper/caida_data/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.linknodes",
      input=> {var nl = parser.parse(input).asInstanceOf[NodeLink];nl.node});

}