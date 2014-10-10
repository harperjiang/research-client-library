package edu.clarkson.cs.clientlib.caida.itdk.model

import edu.clarkson.cs.clientlib.caida.itdk.model.routing.DefaultRouting

/**
 * Partition is the manager of everything in a machine
 */
class Partition {

  /**
   * Mapping from IP address to node
   */
  val nodes = scala.collection.mutable.Map[String,Node]();
  /**
   * Routing table 
   */
  var routing : Routing = new DefaultRouting;
  
  
  
  
  
  def init = {
    
  }
}