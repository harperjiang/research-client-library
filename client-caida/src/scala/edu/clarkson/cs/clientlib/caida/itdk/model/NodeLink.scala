package edu.clarkson.cs.clientlib.caida.itdk.model

class NodeLink(nr: (Int, String), l: Int) {
  val nodeRef = nr
  var link = l

  def this(nr: (String, String), l: String) = {
    this((Integer.parseInt(nr._1.substring(1)), nr._2), Integer.parseInt(l.substring(1)))
  }

  def node = nodeRef._1
  def ip = nodeRef._2

}