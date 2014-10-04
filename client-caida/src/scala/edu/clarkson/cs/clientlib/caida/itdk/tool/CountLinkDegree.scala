package edu.clarkson.cs.clientlib.caida.itdk.tool

import java.io.FileOutputStream
import java.io.PrintWriter
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import scala.io.Source
import edu.clarkson.cs.clientlib.caida.itdk.model.Link

object CountLinkDegree extends App {

  var parser = new Parser();

  var nls = Source.fromFile(Config.dir + "kapar-midar-iff.links").getLines().filter(!_.startsWith("#")).map(x => parser.parse[Link](x))

  var pw = new PrintWriter(new FileOutputStream(Config.dir + "links.degree"))

  for (nl <- nls) {
    pw.println("L%d\t%d".format(nl.id, nl.nodes.length))
  }

  pw.close
}