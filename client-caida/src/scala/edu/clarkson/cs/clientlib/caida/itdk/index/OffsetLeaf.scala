package edu.clarkson.cs.clientlib.caida.itdk.index

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

class OffsetLeaf(degree: Int) extends IndexNode(degree) {

  var offsets: Buffer[Long] = new ArrayBuffer[Long](degree);
  nodes = null;

  def append(rec: (Int, Long)): Unit = {
    this.values += rec._1;
    this.offsets += rec._2;
  }

  override def depth = 1

  def size: Int = {
    this.values size
  }
}