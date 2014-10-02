package edu.clarkson.cs.clientlib.caida.itdk.index

import java.io.File
import scala.io.Source
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.FileInputStream
import org.apache.commons.io.input.CountingInputStream
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

class IndexBuilder {

  def buildNodeLinkIndex(input: File) = {
    val buffer: Buffer[(Int, Long)] = new ArrayBuffer(1000000);
    val cis: CountingInputStream = new CountingInputStream(new FileInputStream(input));
    val parser = new Parser();
    var previousNode = -1;
    var oldoffset = cis.getByteCount();
    for (line <- Source.fromInputStream(cis).getLines) {
      var nl: NodeLink = parser.parse(line);
      if (nl.node != previousNode) {
        // Append
        buffer += new Tuple2(nl.node, oldoffset);
      }
    }
  }
}