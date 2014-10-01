package edu.clarkson.cs.clientlib.caida.itdk.tool

import scala.io.Source
import edu.clarkson.cs.clientlib.caida.itdk.model.Node
import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex
import edu.clarkson.cs.clientlib.caida.itdk.parser.NodeParser
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser

object LoadNode extends App {

  val nodeList = new ListBuffer[Node]();
  val nodeFileName = "/home/harper/caidaDav/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.nodes";
  val nodeASFileName = "/home/harper/caidaDav/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.nodes.as";
  val nodeGeoFileName = "/home/harper/caidaDav/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.nodes.geo";
  val parser = new Parser();

  for (
    line <- Source.fromFile(nodeFileName).getLines.map { l => l.trim } if line.startsWith("node") if !line.startsWith("#")
  ) {
    var node = parser.parseNode(line);
    if (null != node)
      nodeList += node;
    if (nodeList.size % 100000 == 0)
      println(nodeList.size)
  }

}