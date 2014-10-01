package edu.clarkson.cs.clientlib.caida.itdk.model

import scala.collection.JavaConversions._

class Link(lid: Int) {
  var id = lid;
  var nodes: List[NodeRef] = List();

  def this(sid: String) = {
    this(Integer.parseInt(sid.substring(1)));
  }

  def setNodes(newnodes: java.util.List[NodeRef]) = {
    nodes = newnodes.toList;
  }

}