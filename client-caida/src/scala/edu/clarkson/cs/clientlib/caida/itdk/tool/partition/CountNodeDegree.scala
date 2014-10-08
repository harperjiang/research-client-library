package edu.clarkson.cs.clientlib.caida.itdk.tool.partition

import scala.io.Source
import java.io.FileOutputStream
import java.io.PrintWriter
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink
import edu.clarkson.cs.clientlib.caida.itdk.tool.Config

object CountNodeDegree extends App {

  var parser = new Parser();

  var nls = Source.fromFile(Config.dir + "kapar-midar-iff.linknodes").getLines().map(x => parser.parse[NodeLink](x))

  var pw = new PrintWriter(new FileOutputStream(Config.dir + "linknodes.degree"))

  var oldnode = -1;
  var counter = 0;
  for (nl <- nls) {
    if (nl.node != oldnode) {
      if (oldnode != -1) {
        pw.println("N%d\t%d".format(oldnode, counter))
      }
      oldnode = nl.node
      counter = 1
    } else {
      counter += 1
    }
  }
  pw.println("N%d\t%d".format(oldnode,counter))

  pw.close

}