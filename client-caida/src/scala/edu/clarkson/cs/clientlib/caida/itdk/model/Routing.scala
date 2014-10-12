package edu.clarkson.cs.clientlib.caida.itdk.model

trait Routing {

  def route(nodeId: Int): List[Int];
}