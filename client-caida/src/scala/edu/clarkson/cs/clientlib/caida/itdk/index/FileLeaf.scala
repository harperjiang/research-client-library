package edu.clarkson.cs.clientlib.caida.itdk.index

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer
import scala.ref.SoftReference
import java.io.FileInputStream
import java.io.ObjectInputStream

class FileLeaf(r: (Int, Int), fn: String) extends IndexNode(1) {

  var file: String = fn;
  var range = r;
  var node: SoftReference[IndexNode] = null;

  nodes = null;
  values = null;

  override def min = range._1;
  override def max = range._2;

  def fetch: Unit = {
    var ois = new ObjectInputStream(new FileInputStream(file));
    var filenode: IndexNode = ois.readObject().asInstanceOf[IndexNode]
    node = new SoftReference[IndexNode](filenode);
    ois.close
  }

}