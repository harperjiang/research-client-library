package edu.clarkson.cs.clientlib.caida.itdk.model

import edu.clarkson.cs.clientlib.caida.itdk.model.routing.DefaultRouting
import java.util.Properties
import java.io.FileInputStream
import edu.clarkson.cs.clientlib.caida.itdk.index.IndexSet
import scala.io.Source
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser

/**
 * Partition is the manager of everything in a machine
 */
class Partition {

  var id = 0;
  /**
   * Mapping from IP address to node
   */
  val nodeMap = scala.collection.mutable.Map[String, Node]();

  val linkMap1 = scala.collection.mutable.Map[(Int, String), Link]();
  val linkMap2 = scala.collection.mutable.Map[Int, List[Link]]();

  val nodelinkMap1 = scala.collection.mutable.Map[(Int, String), Node]();
  val nodelinkMap2 = scala.collection.mutable.Map[Int, List[Node]]();
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

    // Load nodes
    var nodeFile = "%s_%d".format(prop.get("node_file"), this.id);
    var nodeLinkFile = "%s_%d".format(prop.get("nodelink_file"), this.id);
    var linkFile = "%s_%d".format(prop.get("link_file"), this.id);

    Source.fromFile(nodeFile).getLines.filter(!_.startsWith("#"))
      .map[Node](line => { parser.parse[Node](line) })
      .foreach(node => {
        node.ips.foreach(ip => { nodeMap += (ip -> node) })
      });

//    Source.fromFile(linkFile).getLines.filter(!_.startsWith("#"))
//      .map[Link](line => { parser.parse[Link](line) })
//      .foreach(link => { linkMap += (link.id -> link) });
//
//    Source.fromFile(nodeLinkFile).getLines
//      .map[NodeLink](line => parser.parse[NodeLink](line))
//      .foreach(nodelink => {
//        // TODO Here
//      });
  }

  def find(fromIp: String, toIp: String, step: Int) {

  }

}