package edu.clarkson.cs.clientlib.caida.itdk.model

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import scala.math.BigDecimal.int2bigDecimal
import scala.collection.mutable.Buffer
/**
 * A node represents a router
 */
class Node {

  val defaultIp = "-"

  var id = 0;
  var asNum = 0;
  var asMethod = 0;
  var continent = "";
  var countryCode = "";
  var region = "";
  var city = "";
  var latitude: BigDecimal = 0;
  var longitude: BigDecimal = 0;

  var ips = Set[String]();
  val links = scala.collection.mutable.Map[String, List[String]]();

  def this(nid: Int) = {
    this();
    id = nid;
  }

  def this(sid: String, ipList: java.util.List[String]) = {
    this(Integer.parseInt(sid.substring(1)));
    ips = ipList.toSet
  }
}