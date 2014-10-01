package edu.clarkson.cs.clientlib.caida.itdk.tool

import scala.io.Source
import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex
import edu.clarkson.cs.clientlib.caida.itdk.parser.NodeParser

object LoadNode extends App {

  val nodeList = new ListBuffer[Node]();
  val nodeFileName = "/home/harper/caidaDav/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.nodes";
  val nodeASFileName = "/home/harper/caidaDav/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.nodes.as";
  val nodeGeoFileName = "/home/harper/caidaDav/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.nodes.geo";
  val nodeParser = new NodeParser();

  for (
    line <- Source.fromFile(nodeFileName).getLines.map { l => l.trim } if line.startsWith("node") if !line.startsWith("#")
  ) {
    var node = nodeParser.parse(line);
    if (null != node)
      nodeList += node;
  }
}