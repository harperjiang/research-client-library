package edu.clarkson.cs.clientlib.caida.itdk.tool.partition

import java.io.FileOutputStream
import java.io.PrintWriter

import scala.io.Source

import edu.clarkson.cs.clientlib.caida.itdk.model.Link
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import edu.clarkson.cs.clientlib.caida.itdk.tool.Config

object CountLinkDegree extends App {

  var parser = new Parser();

  var nls = Source.fromFile(Config.dir + "kapar-midar-iff.links").getLines().filter(!_.startsWith("#")).map(x => parser.parse[Link](x))

  var pw = new PrintWriter(new FileOutputStream(Config.dir + "links.degree"))

  for (nl <- nls) {
    pw.println("L%d\t%d".format(nl.id, nl.nodes.length))
  }

  pw.close
}