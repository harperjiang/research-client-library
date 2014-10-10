package edu.clarkson.cs.clientlib.caida.itdk.model;

import scala.collection.mutable.ArrayBuffer

import org.scalatest.FunSuite

class NodeTest extends FunSuite {
  test("Test Assign Node Values") {
    var node = new Node();
    node.ips = Set[String]("123");
  }
}