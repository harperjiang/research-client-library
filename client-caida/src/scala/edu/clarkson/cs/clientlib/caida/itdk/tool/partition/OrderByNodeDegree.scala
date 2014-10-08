package edu.clarkson.cs.clientlib.caida.itdk.tool.partition

import edu.clarkson.cs.clientlib.caida.itdk.mapreduce.Task
import edu.clarkson.cs.clientlib.caida.itdk.tool.Config
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink

object OrderByNodeDegree extends App {

  var parser = new Parser();

  var reduce = new Function1[Iterator[String], Iterator[String]] {
    def apply(input: Iterator[String]): Iterator[String] = {
      return input;
    }
  };

  Task.execute(List(Config.file("nodelinks.degree"), Config.file("kapar-midar-iff.nodelinks")),
    Config.file("nodelink.sorted"),
    (file, line) => {
      file match {
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
    reduce)
}