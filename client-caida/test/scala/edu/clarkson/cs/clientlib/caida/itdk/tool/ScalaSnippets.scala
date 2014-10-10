package edu.clarkson.cs.clientlib.caida.itdk.tool

import scala.collection.JavaConversions._
import scala.ref.SoftReference

object ScalaSnippets extends App {

  var a: SoftReference[String] = new SoftReference(null);
  println(a.get)
}