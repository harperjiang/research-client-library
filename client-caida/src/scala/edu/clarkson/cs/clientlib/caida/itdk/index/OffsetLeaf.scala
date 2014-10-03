package edu.clarkson.cs.clientlib.caida.itdk.index

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

class OffsetLeaf(degree: Int) extends IndexNode(degree) {

  datas = null;
  var offsets: Buffer[(Int, Long)] = new ArrayBuffer[(Int, Long)](degree);

  def append(rec: (Int, Long)): Unit = {
    offsets += rec;
  }

  override def min = offsets(0)._1;
  override def max = offsets.last._1;
  override def depth = 1

  override def find(target: Int): Long = {
    for (i <- offsets)
      if (i._1 == target)
        return i._2;
    return -1;
  }

  override def refresh(ctn: IndexSet): Unit = {
    container = Some(ctn);
  }

  override def size: Int = {
    this.offsets size
  }
}