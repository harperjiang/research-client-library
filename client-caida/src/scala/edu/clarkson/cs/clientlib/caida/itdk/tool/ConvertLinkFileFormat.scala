package edu.clarkson.cs.clientlib.caida.itdk.tool

import java.io.File
import java.io.PrintWriter
import java.text.MessageFormat
import scala.io.Source
import edu.clarkson.cs.clientlib.caida.itdk.model.Link
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink
import sys.process._

/**
 * Link data is originally in the format of
 * link <link_id> (<node_id>(:<ip>)?[ ])+
 *
 * This tool convert it into the format of
 * nodelinks <node_id>(:<ip>)? : (<link_id>[ ])+
 */
object ConvertLinkFileFormat extends App {

  val linkFileName = "/home/harper/caidaDav/topo-data.caida.org/ITDK/ITDK-2014-04/kapar-midar-iff.links";
  val parser = new Parser();
  val destFileName = new File(linkFileName).getParent() + File.separator + "linknodes";
  val printer = new PrintWriter(destFileName);

  for (line <- Source.fromFile(linkFileName).getLines map (l => l.trim()) if !line.startsWith("#")) {
    var link: Link = parser.parse(line);
    for (n <- link.nodes) {
      var sb = new StringBuilder();
      sb append "nodelinks " append "N" append n.node
      if (!n.ip.isEmpty) {
        sb append ":" append n.ip.get;
      }
      sb append " "
      sb append "L" append link.id
      printer.println(sb toString);
    }
  }

  printer close

  mergeSort(destFileName)((a, b) => {
    var nl1: NodeLink = parser.parse(a)
    var nl2: NodeLink = parser.parse(b)
    nl1.nodeRef.node compare nl2.nodeRef.node
  })

  def mergeSort(fileName: String)(comparator: (String, String) => Int): Unit = {
    val f = new File(fileName);
    val line = "cat " + fileName #| "wc -l" !!
    val split = 20;
    val fileLine = Integer.parseInt(line) / split;
    val smallFiles = new Array[File](split);
    val smallSorted = new Array[File](split);
    for (i <- 1 to split) {
      smallFiles(i) = new File(fileName + ".p" + i);
      smallSorted(i) = new File(fileName + ".s" + i);
      
    }
  }

  def heapSort(fileName: File, output: File)(comparator: (String, String) => Int): Unit = {

  }
}