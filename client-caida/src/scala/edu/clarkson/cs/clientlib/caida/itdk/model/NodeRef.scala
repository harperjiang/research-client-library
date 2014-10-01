package edu.clarkson.cs.clientlib.caida.itdk.model

class NodeRef(n: String) {

  var node: Int = Integer.parseInt(n.substring(1));
  var ip = None: Option[String];

  def this(n: String, i: String) {
    this(n);
    ip = Some(i);
  }
}