package edu.clarkson.cs.clientlib.caida.itdk.index

import scala.collection.mutable.Buffer
import scala.collection.mutable.ArrayBuffer

class IndexNode {

  var values: Buffer[Int] = new ArrayBuffer[Int]();
  var nodes: Buffer[IndexNode] = new ArrayBuffer[IndexNode]();

}