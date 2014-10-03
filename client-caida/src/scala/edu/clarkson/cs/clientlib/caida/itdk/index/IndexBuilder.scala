package edu.clarkson.cs.clientlib.caida.itdk.index

import java.io.File
import java.io.FileInputStream
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import org.apache.commons.io.input.CountingInputStream
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import scala.collection.mutable.Buffer
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class IndexBuilder {

  val buffer = new ArrayBuffer[Buffer[IndexNode]](10);
  val degree = 100;
  for (i <- 1 to 5) buffer += new ArrayBuffer[IndexNode](degree);

  def buildNodeLinkIndex(input: File): Unit = {

    // Initialize buffer structure

    val cis: CountingInputStream = new CountingInputStream(new FileInputStream(input));

    val parser = new Parser();

    var previousNode = -1;
    var oldoffset = cis.getByteCount();
    var currentLeaf = new OffsetLeaf(degree);

    for (line <- Source.fromInputStream(cis).getLines) {
      var nl: NodeLink = parser.parse(line);
      if (nl.node != previousNode) {
        // Check current leaf and append
        if (currentLeaf.size == degree) {
          buffer(0) += currentLeaf;
          merge(0)
          currentLeaf = new OffsetLeaf(degree);
        }
        currentLeaf.append((nl.node, oldoffset));
        previousNode = nl.node;
      }
      oldoffset = cis.getByteCount();
    }

    for (i <- 0 to 3) {
      if (buffer(i).size > 1) {
        var node = new IndexNode(degree);
        node buildon buffer(i);
        buffer(i + 1) += node
        buffer(i) clear
      }
    }

    var oop = new ObjectOutputStream(new FileOutputStream("local"));
    oop.writeObject(buffer(2)(0))
    oop.close
    return
  }

  /**
   * Merge the node buffer and generate high level node when necessary
   */
  def merge(i: Int): Unit = {
    if (buffer(i).size == degree) {
      var node = new IndexNode(degree);
      node buildon buffer(i)
      buffer(i).clear
      buffer(i + 1) += node
      merge(i + 1)
    }
  }
}