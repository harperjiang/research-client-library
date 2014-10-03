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

class IndexSet private () {

  var folder: String = "";

  private val refLevel = 10;

  private val rootFile = "root"
  private val leafFile = "leaf";
  private val buffer = new ArrayBuffer[Buffer[IndexNode]](refLevel);

  private var degree = 100;
  private var fileLevel = 4;

  private var root: IndexNode = null;
  private var leafCounter = 0;

  def this(fld: String) = {
    this();
    folder = fld;
    var fd = new File(folder);
    if (!fd.exists()) fd.mkdir();
    if (!fd.isDirectory()) throw new IllegalArgumentException();
  }

  def this(fld: String, dge: Int) = {
    this(fld);
    degree = dge;
  }

  def load(input: String) = {
    var ois = new ObjectInputStream(new FileInputStream(filename(rootFile)));
    root = ois.readObject.asInstanceOf[IndexNode]
    ois.close
  }

  def build(input: String) = {
    // Initialize buffer structure
    buffer += new ArrayBuffer[IndexNode](degree);

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
    var oop = new ObjectOutputStream(new FileOutputStream(filename(rootFile)));
    merge(0, (size, lvl) => size >= 1 && lvl <= curlevel)
    curlevel = level
    if (buffer(curlevel).size > 1) {
      merge(curlevel, (size, lvl) => lvl == curlevel);
    }
    root = buffer(level)(0);
    oop writeObject root
    oop.close
  }

  private def merge(i: Int, workon: (Int, Int) => Boolean): Unit = {
    if (workon(buffer(i).size, i)) {
      var node = new IndexNode(buffer(i).size);
      node buildon buffer(i)
      buffer(i).clear
      bufferon(i + 1) += wrap(node, i + 1)
      merge(i + 1, workon)
    }
  }

  private def bufferon(index: Int): Buffer[IndexNode] = {
    while (buffer.size <= index) {
      buffer += new ArrayBuffer[IndexNode]()
    }
    buffer(index)
  }

  private def level: Int = {
    buffer.size - 1
  }

  private def wrap(node: IndexNode, lvl: Int): IndexNode = {
    if (lvl == fileLevel) {
      var currentLeaf = leafCounter;
      leafCounter += 1;
      new FileLeaf("%s_%d".format(leafFile, currentLeaf), folder, node)
    }
    node
  }

  protected def filename(fn: String): String = "%s%s%s".format(folder, File.separator, fn);
}