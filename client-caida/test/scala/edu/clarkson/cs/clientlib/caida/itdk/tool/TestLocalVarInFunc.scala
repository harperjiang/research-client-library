package edu.clarkson.cs.clientlib.caida.itdk.tool

import scala.collection.JavaConversions._

object TestLocalVarInFunc extends App {

  var datas = List(1, 3, 5, 5, 3, 43, 3, 2, 5, 4)

  var func = new Function1[Iterator[Int], Iterator[Int]] {

    def apply(datas: Iterator[Int]): Iterator[Int] = {
      var result = for (data <- datas if ({ datas.isEmpty })) yield data
      result
    }
  }

  func(datas.toIterator) foreach (println _)

}