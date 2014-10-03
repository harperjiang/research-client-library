package edu.clarkson.cs.clientlib.caida.itdk.tool

import edu.clarkson.cs.clientlib.caida.itdk.index.IndexSet

object BuildIndex extends App {

  val ib = new IndexSet("/home/harper/caida_data/topo-data.caida.org/ITDK/ITDK-2014-04/linknodes.index");
  ib.build("/home/harper/caida_data/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.linknodes");

}