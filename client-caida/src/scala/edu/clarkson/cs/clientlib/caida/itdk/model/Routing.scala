package edu.clarkson.cs.clientlib.caida.itdk.model

trait Routing {

  def init;

  def route(nodeId: Int): Iterable[Int];
}