package edu.clarkson.cs.clientlib.message

import java.util.HashMap

trait Sender {

  var outportMap = new HashMap[String, OutPort]();

  def send(port: String, message: Object) = {

  }
}

trait OutPort {
  def send(message: Object);
}