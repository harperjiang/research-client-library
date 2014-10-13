package edu.clarkson.cs.clientlib.lang

import java.io.FileInputStream

object Properties {

  val map = scala.collection.mutable.Map[String, java.util.Properties]();

  def load(file: String, key: String): String = {
    map.getOrElse(file, {
      var prop = new java.util.Properties();
      var instream = new FileInputStream(file);
      prop.load(instream);
      instream.close;
      map += (file -> prop);
      prop
    }).getProperty(key);
  }
}