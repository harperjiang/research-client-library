package edu.clarkson.cs.clientlib.caida.itdk.model

import java.io.FileInputStream
import java.util.Properties
import scala.io.Source
import edu.clarkson.cs.clientlib.caida.itdk.index.IndexSet
import edu.clarkson.cs.clientlib.caida.itdk.model.routing.DefaultRouting
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import edu.clarkson.cs.clientlib.caida.itdk.task.Task

/**
 * Partition is the manager of everything in a machine
 */
class Partition {

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

    // Load Id from configuration
    var prop = new Properties();
    var propFile = new FileInputStream("partition.properties");
    prop.load(propFile);
    propFile.close();

    this.id = prop.get("partition_id").toString().toInt;

    // Load indices
    nodeIndex = new IndexSet(prop.get("node_index_file").toString);
    linkIndex = new IndexSet(prop.get("link_index_file").toString);

    // Load nodes, links from file
    var nodeFile = "%s_%d".format(prop.get("node_file"), this.id);
    var nodeLinkFile = "%s_%d".format(prop.get("nodelink_file"), this.id);
    var linkFile = "%s_%d".format(prop.get("link_file"), this.id);

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
  }

  /**
   * Submit a  task
   */
  def submit(task: Task) = {

  }

  /**
   * Propagate a task to other partitions
   */
  def propagate(targets: List[Int]) = {

  }

}