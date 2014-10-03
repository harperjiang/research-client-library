package edu.clarkson.cs.clientlib.caida.itdk.index

import scala.collection.mutable.Buffer
import scala.collection.mutable.ArrayBuffer

/**
 * Each index node has a list of values and a list of nodes.
 *
 * If a value is between v_i(inclusive) and v_{i+1}(exclusive), then its node can be found in node_i
 */
class IndexNode(degree: Int) extends Serializable {

  var values: Buffer[Int] = new ArrayBuffer[Int](degree);
  var nodes: Buffer[IndexNode] = new ArrayBuffer[IndexNode](degree);

  def min: Int = values(0)
  def max: Int = values.last

  def buildon(input: Buffer[IndexNode]): Unit = {
    nodes ++= input;
    for (n <- nodes) values += n.min
  }

  def depth: Int = {
    if (nodes.isEmpty)
      return 1;
    return nodes(0).depth + 1
  }
}