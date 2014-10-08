package edu.clarkson.cs.clientlib.caida.itdk.mapreduce

object Utils {

  def fetchKey(line: String): String = {
	line.splitAt(line.indexOf(" "))._1
  }
}