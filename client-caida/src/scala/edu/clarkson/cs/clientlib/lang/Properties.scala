package edu.clarkson.cs.clientlib.lang

import java.io.FileInputStream

object Properties {

  val map = scala.collection.mutable.Map[String, java.util.Properties]();

  def load[T](file: String, key: String)(implicit m: Manifest[T]): T = {
    var stringval = map.getOrElse(file, {
      var prop = new java.util.Properties();
      var instream = new FileInputStream(file);
      prop.load(instream);
      instream.close;
      map += (file -> prop);
      prop
    }).getProperty(key);

    return stringval.asInstanceOf[T];
  }
}