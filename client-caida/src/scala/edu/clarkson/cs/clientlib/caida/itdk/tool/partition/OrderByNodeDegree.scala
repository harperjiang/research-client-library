package edu.clarkson.cs.clientlib.caida.itdk.tool.partition

import edu.clarkson.cs.clientlib.caida.itdk.mapreduce.Task
import edu.clarkson.cs.clientlib.caida.itdk.tool.Config
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink
import scala.collection.mutable.ArrayBuffer
import edu.clarkson.cs.clientlib.caida.itdk.mapreduce.Utils
import java.io.File

object OrderByNodeDegree extends App {

  var parser = new Parser();

  var reduce = new Function1[String, Iterator[String]] {

    var lastId = "";

    var degreeBuffer = new ArrayBuffer[Int](1);
    var nodelinkBuffer = new ArrayBuffer[NodeLink](2000);

    def apply(line: String): Iterator[String] = {
      if (line == Task.EOF) {
        return build;
      }
      var id = Utils.fetchKey(line);
      if (id != lastId) {
        var result: Iterator[String] = null;
        if (lastId != "") {
          result = build;
          process(line);
        }
        lastId = id;
        if (null == result)
          return Iterator.empty;
        return result;
      } else {
        process(line);
        return Iterator.empty;
      }
    }

    def process(line: String): Unit = {
      var parts = line.splitAt(line.indexOf(" "));
      parts._1 match {
        case "degree" => {
          degreeBuffer += parts._2.toInt;
        }
        case "data" => {
          nodelinkBuffer += parser.parse[NodeLink](parts._2);
        }
      }
    }

    def build: Iterator[String] = {
      var result = new ArrayBuffer[String]();

      for (degree <- degreeBuffer) {
        for (nodelink <- nodelinkBuffer) {
          result += "N%d L%d %d".format(nodelink.node, nodelink.link, degree)
        }
      }
      // Start a new buffer
      degreeBuffer.clear;
      nodelinkBuffer.clear;
      result.toIterator;
    }
  };

  Task.execute(List(Config.file("nodelinks.degree"), Config.file("kapar-midar-iff.nodelinks")),
    Config.file("nodelink.sorted"),
    (file, line) => {
      new File(file).getName() match {
        case "nodelinks.degree" => {
          var parts = line.split("\\s")
          (parts(0).substring(1), "%s %s".format("degree", line))
        }
        case "kapar-midar-iff.nodelinks" => {
          var nodelink = parser.parse[NodeLink](line)
          ("%d".format(nodelink.node), "%s %s".format("data", line))
        }
      }
    },
    reduce,
    (line1, line2) => {
      line1.splitAt(line1.indexOf(" "))._1 compare line2.splitAt(line2.indexOf(" "))._1
    })
}