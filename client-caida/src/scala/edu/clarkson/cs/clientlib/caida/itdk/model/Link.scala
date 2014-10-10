package edu.clarkson.cs.clientlib.caida.itdk.model

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

/**
 * A link consists of multiple nodes and it works like a shared bus between these links.
 * Each node may have a IP dedicated to that link.
 * This means that if this IP appears, the traffic for sure goes to this link.
 */
class Link(lid: Int) {

  var id = lid;
  val nodes = scala.collection.mutable.Map[String, Buffer[Int]]();

  def this(sid: String, nodes: java.util.List[(String, String)]) = {
    this(Integer.parseInt(sid.substring(1)));

    nodes.foreach(a => {
      var id = a._1.substring(1).toInt;
      var ip = a._2;

      if (!nodes.contains(ip)) {
        this.nodes.put(ip, new ArrayBuffer[Int]())
      }
      this.nodes.get(ip).get += id;
    });
  }

}