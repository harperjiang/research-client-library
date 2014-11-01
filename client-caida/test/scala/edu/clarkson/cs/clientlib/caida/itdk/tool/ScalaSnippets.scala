package edu.clarkson.cs.clientlib.caida.itdk.tool

import com.google.gson.GsonBuilder

object ScalaSnippets extends App {

  var gb = new GsonBuilder();
  var gson = gb.create();

  var json = "{\"a\":3,\"b\":{\"x\":3,\"d\":5}}";
  var res = gson.fromJson(json, classOf[java.util.Map[String, _]]);
  println(res);
}