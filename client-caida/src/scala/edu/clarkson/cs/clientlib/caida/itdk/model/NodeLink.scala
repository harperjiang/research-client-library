package edu.clarkson.cs.clientlib.caida.itdk.model

class NodeLink(nr: NodeRef, l: Int) {
  val nodeRef = nr
  var link = l

  def this(nr: NodeRef, l: String) = {
    this(nr, Integer.parseInt(l.substring(1)))
  }
}