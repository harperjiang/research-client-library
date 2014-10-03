package edu.clarkson.cs.clientlib.caida.itdk.index

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer
import scala.ref.SoftReference
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.File

class FileLeaf(fn: String, r: (Int, Int)) extends IndexNode(1) {

  var file: String = fn;
  var range = r;
  var node: SoftReference[IndexNode] = null;

  nodes = null;
  values = null;

  override def min = range._1;
  override def max = range._2;

  def this(name: String, folder: String, n: IndexNode) = {
    this(name, (n.min, n.max));
    var oop = new ObjectOutputStream(new FileOutputStream("%s%s%s".format(folder, File.separator, name)));
    oop writeObject n
    oop.close
    this.node = new SoftReference[IndexNode](n);
  }

  protected def fetch(folder: String): Unit = {
    var ois = new ObjectInputStream(new FileInputStream("%s%s%s".format(folder, File.separator, file)));
    var filenode: IndexNode = ois.readObject().asInstanceOf[IndexNode]
    ois.close
    node = new SoftReference[IndexNode](filenode);
  }

}