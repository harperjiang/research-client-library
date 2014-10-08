package edu.clarkson.cs.clientlib.caida.itdk.mapreduce

import java.io.FileOutputStream
import java.io.PrintWriter

import scala.io.Source

object Task {

  def execute(files: List[String], result: String,
    map: (String, String) => (String, String),
    reduce: Iterator[String] => Iterator[String],
    sort: (String, String) => Int = (line1, line2) => {
      var key1 = line1.splitAt(line1.indexOf(" "))._1
      var key2 = line2.splitAt(line2.indexOf(" "))._1
      key1 compare key2
    }) = {

    var mapname = "/tmp/%l.map".format(System currentTimeMillis)
    var sortname = "/tmp/%l.sort".format(System currentTimeMillis)
    var output = new PrintWriter(new FileOutputStream(mapname));

    // Map
    for (file <- files) {
      Source.fromFile(file).getLines foreach (line => {
        var result = map(file, line)
        output.println("%s %s".format(result._1, result._2))
      })
    }
    output.close

    // Sort
    Sorting.sort(mapname, sortname)(sort)

    // Reduce
    var finalout = new PrintWriter(new FileOutputStream(result));
    reduce(Source.fromFile(sortname).getLines.map(line => line.splitAt(line.indexOf(" "))._2))
      .foreach(finalout.println(_));
    finalout.close();
  }
}

