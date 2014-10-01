package edu.clarkson.cs.clientlib.caida.itdk.model;

import org.scalatest.FunSuite

class NodeTest extends FunSuite {
  test("Test Assign Node Values") {
    var node = new Node();
    node.ips = List("123");
  }
}