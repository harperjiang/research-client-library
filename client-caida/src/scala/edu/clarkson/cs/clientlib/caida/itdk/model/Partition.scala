package edu.clarkson.cs.clientlib.caida.itdk.model

import java.io.FileInputStream
import scala.io.Source
import edu.clarkson.cs.clientlib.caida.itdk.index.IndexSet
import edu.clarkson.cs.clientlib.caida.itdk.model.routing.DefaultRouting
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.lang.Properties
import org.springframework.beans.factory.InitializingBean

/**
 * Partition is the manager of everything in a machine
 */
class Partition extends InitializingBean {

  var id = 0;
  var nodeFile = "";
  var linkFile = "";
  /**
   * Mapping from IP address to node
   */
  val nodeIpMap = scala.collection.mutable.Map[String, Node]();
  val nodeMap = scala.collection.mutable.Map[Int, Node]();

  /**
   * Routing table
   */
  var routing: Routing = null;

  def afterPropertiesSet = {
    var parser = new Parser();

    // Load nodes, links from file
    var nfName = "%s_%d".format(nodeFile, this.id);
    var lfName = "%s_%d".format(linkFile, this.id);

    Source.fromFile(nfName).getLines.filter(!_.startsWith("#"))
      .map[Node](line => { parser.parse[Node](line) })
      .foreach(node => {
        nodeMap += (node.id -> node);
        node.ips.foreach(ip => { nodeIpMap += (ip -> node) })
      });

    var nodeNotFound = { node_id: Int => { throw new IllegalArgumentException("Node not found: %d".format(node_id)); } };

    Source.fromFile(lfName).getLines.filter(!_.startsWith("#"))
      .map[Link](line => { parser.parse[Link](line) })
      .foreach(link => {
        // Attach Link with nodes
        link.attachNodes(nodeMap);
        // Attach node with links
        link.namedNodeIds.foreach(entry => {
          var node = nodeMap.get(entry._2).getOrElse(nodeNotFound(entry._2));
          node.appendLink(link, entry._1);
        });
        link.anonymousNodeIds.foreach(node_id => {
          var node = nodeMap.get(node_id).getOrElse(nodeNotFound(node_id));
          node.appendLink(link);
        });
      });
  }

  def queryPartition(node: Node): Iterable[Int] = {
    routing.route(node.id);
  }

}