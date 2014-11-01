package edu.clarkson.cs.clientlib.caida.itdk.tool

import edu.clarkson.cs.clientlib.caida.itdk.index.IndexSet
import scala.util.Random

object LoadIndexPerformance extends App {

  val nodeIndex = new IndexSet(Config.file("nodes.index"))
  val range = nodeIndex.range

  var sum = 0l;
  var success = 0l;
  val loop = 1000000;
  val random = new Random(System.currentTimeMillis)
  for (i <- 1 to loop) {
    var start = System.currentTimeMillis;
    var target = random.nextInt(range._2);
    var result = nodeIndex.find(target);
    if (result._1 != -1)
      success += 1;
    var elapse = System.currentTimeMillis - start;
    sum += elapse;
  }

  println(sum.toFloat / loop);
  println(success.toFloat / loop);
}