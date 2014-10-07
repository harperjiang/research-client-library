package edu.clarkson.cs.clientlib.caida.itdk.tool

import scala.io.Source

import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser

object RunEachLine extends App {

  val parser = new Parser();

  println(System.currentTimeMillis());
  Source.fromFile("/home/harper/caida_data/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.nodes")
    .getLines.filter(!_.startsWith("#")).map[Node](line => parser.parse[Node](line))
  println(System.currentTimeMillis());
  
  println(System.currentTimeMillis());
  Source.fromFile("/home/harper/caida_data/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.links")
    .getLines.filter(!_.startsWith("#")).map[Node](line => parser.parse[Node](line))
  println(System.currentTimeMillis());
  
  
}