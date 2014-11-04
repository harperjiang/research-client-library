package edu.clarkson.cs.clientlib.caida.itdk

import org.springframework.context.support.ClassPathXmlApplicationContext

import edu.clarkson.cs.clientlib.caida.itdk.dist.MasterNode

class MasterUnit {

  var node: MasterNode = null;
}

object RunMaster extends App {
  var appContext = new ClassPathXmlApplicationContext("app-context-master.xml");
}