package edu.clarkson.cs.clientlib.caida.itdk.tool

import java.io.File

import edu.clarkson.cs.clientlib.caida.itdk.index.IndexBuilder

object BuildIndex extends App {

  val ib = new IndexBuilder();
  ib.buildNodeLinkIndex(new File("/home/harper/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.linknodes"));
}