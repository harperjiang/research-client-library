package edu.clarkson.cs.clientlib.caida.itdk.model.routing

import edu.clarkson.cs.clientlib.caida.itdk.model.Routing

class DefaultRouting extends Routing {

  def route(nodeId: Int): List[Int] = {
    throw new RuntimeException("Not implemented");
  }
}