package edu.clarkson.cs.clientlib.caida.itdk.tool

import java.io.FileInputStream
import java.io.ObjectInputStream

import edu.clarkson.cs.clientlib.caida.itdk.model._

object AnalyzeIndex extends App {

  var ois = new ObjectInputStream(new FileInputStream("/home/harper/root"));
  var head = ois.readObject();
  var a = 1
}