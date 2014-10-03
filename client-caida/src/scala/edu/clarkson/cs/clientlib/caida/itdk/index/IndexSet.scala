package edu.clarkson.cs.clientlib.caida.itdk.index

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer
import scala.io.Source
import org.apache.commons.io.input.CountingInputStream
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import java.io.ObjectInputStream

class IndexSet(fld: String) {

  var folder: String = fld;
  if (!new File(folder).isDirectory()) throw new IllegalArgumentException();

  private var root: IndexNode = null;
  private val maxLevel = 10;
  private val buffer = new ArrayBuffer[Buffer[IndexNode]](maxLevel);
  private val degree = 100;
  private val fileLevel = 4;
  private var leafCounter = 0;
  private var level = 0;

  private val rootFile = "root"
  private val leafFile = "leaf";

  def load(input: String) = {
    var ois = new ObjectInputStream(new FileInputStream("%s%s%s".format(folder, File.separator, rootFile)));
    root = ois.readObject.asInstanceOf[IndexNode]
    ois.close
  }

  def build(input: String) = {
    // Initialize buffer structure
    for (i <- 1 to 5) buffer += new ArrayBuffer[IndexNode](degree);

    val cis = new CountingInputStream(new FileInputStream(input));
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
          merge(0, (size, lvl) => size >= degree)
          currentLeaf = new OffsetLeaf(degree);
        }
        currentLeaf.append((nl.node, oldoffset));
        previousNode = nl.node;
      }
      oldoffset = cis.getByteCount();
    }

    // Final cleanup and write root
    var curlevel = level;
    var oop = new ObjectOutputStream(new FileOutputStream(rootFile));
    merge(0, (size, lvl) => size >= 1 && lvl <= level)
    if (buffer(level).size > 1) {
      merge(level, (size, lvl) => lvl == level);
    }
    root = buffer(level)(0);
    oop writeObject root
    oop.close
  }

  private def merge(i: Int, workon: (Int, Int) => Boolean): Unit = {
    if (level < i) level = i;
    if (workon(buffer(i).size, i)) {
      var node = new IndexNode(buffer(i).size);
      node buildon buffer(i)
      buffer(i).clear
      buffer(i + 1) += wrap(node, i + 1)
      merge(i + 1, workon)
    }
  }

  private def wrap(node: IndexNode, level: Int): IndexNode = {
    if (level == fileLevel) {
      var currentLeaf = leafCounter;
      leafCounter += 1;
      new FileLeaf("%s_%d".format(leafFile, currentLeaf), folder, node)
    }
    node
  }
}