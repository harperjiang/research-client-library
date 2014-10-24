package edu.clarkson.cs.clientlib.caida.itdk.model

import java.io.FileInputStream
import scala.io.Source
import edu.clarkson.cs.clientlib.caida.itdk.index.IndexSet
import edu.clarkson.cs.clientlib.caida.itdk.model.routing.DefaultRouting
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import edu.clarkson.cs.clientlib.caida.itdk.task.Task
import edu.clarkson.cs.clientlib.lang.Properties

/**
 * Partition is the manager of everything in a machine
 */
class Partition {

  private val PROP = "partition.properties";

  var id = 0;
  /**
   * Mapping from IP address to node
   */
  val nodeIpMap = scala.collection.mutable.Map[String, Node]();
  val nodeMap = scala.collection.mutable.Map[Int, Node]();

  /**
   * Routing table
   */
  var routing: Routing = new DefaultRouting;

  /**
   * Indices
   */
  var nodeIndex: IndexSet = null;
  var linkIndex: IndexSet = null;
  init;

  def init = {
    var parser = new Parser();

    this.id = Properties.load(PROP, "partition_id");

    // Load nodes, links from file
    var nodeFile = "%s_%d".format(Properties.load[String](PROP, "node_file"), this.id);
    var linkFile = "%s_%d".format(Properties.load[String](PROP, "link_file"), this.id);

    Source.fromFile(nodeFile).getLines.filter(!_.startsWith("#"))
      .map[Node](line => { parser.parse[Node](line) })
      .foreach(node => {
        nodeMap += (node.id -> node);
        node.ips.foreach(ip => { nodeIpMap += (ip -> node) })
      });

    var nodeNotFound = { node_id: Int => { throw new IllegalArgumentException("Node not found: %d".format(node_id)); } };

    Source.fromFile(linkFile).getLines.filter(!_.startsWith("#"))
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

    routing.init;
  }

  def queryPartition(node: Node): Iterable[Int] = {
    routing.route(node.id);
  }

}