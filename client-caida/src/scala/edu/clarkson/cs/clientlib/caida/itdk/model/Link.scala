package edu.clarkson.cs.clientlib.caida.itdk.model

import scala.collection.JavaConversions._

class Link(lid: Int) {
  var id = lid;
  var nodes: List[(Int, String)] = List();

  def this(sid: String, nodes: java.util.List[(String, String)]) = {
    this(Integer.parseInt(sid.substring(1)));
    this.nodes = nodes.map(a => (a._1.substring(1).toInt, a._2)).toList;
  }

}