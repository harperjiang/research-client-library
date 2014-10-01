package edu.clarkson.cs.clientlib.caida.itdk.model

import java.util.ArrayList
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
/**
 * A node represents a router
 */
class Node {
  var id = 0;
  var asNum = 0;
  var asMethod = 0;
  var continent = "";
  var countryCode = "";
  var region = "";
  var city = "";
  var latitude: BigDecimal = 0;
  var longitude: BigDecimal = 0;
  var ips: List[String] = List();
  var links: List[Link] = List();

  def this(nid: Int) = {
    this();
    id = nid;
  }

  def this(sid: String) = {
    this(Integer.parseInt(sid.substring(1)));
  }

  def setIps(newips: java.util.List[String]): Unit = {
    ips = newips.toList
  }
}