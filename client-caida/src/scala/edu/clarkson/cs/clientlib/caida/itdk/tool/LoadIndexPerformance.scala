package edu.clarkson.cs.clientlib.caida.itdk.tool

import edu.clarkson.cs.clientlib.caida.itdk.index.IndexSet
import scala.util.Random

object LoadIndexPerformance extends App {

  val nodeIndex = new IndexSet(Config.file("nodelinks.index"))
  val range = nodeIndex.range
  
  var sum = 0l;
  val loop = 100000;
  val random = new Random(System.currentTimeMillis)
  for(i <- 0 to loop) {
    var start = System.currentTimeMillis;
    var target = random.nextInt(range._2);
    nodeIndex.find(target);
    var elapse = System.currentTimeMillis - start;
    sum+= elapse;
  }
  
  println(sum/loop)
}