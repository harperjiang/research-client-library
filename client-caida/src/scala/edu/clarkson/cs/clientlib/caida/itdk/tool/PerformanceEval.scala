package edu.clarkson.cs.clientlib.caida.itdk.tool

import java.io.PrintWriter
import java_cup.parser
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink
import java.io.File
import scala.io.Source
import java.util.Comparator
import scala.collection.JavaConversions._
import edu.clarkson.cs.clientlib.caida.itdk.parser.Parser

object PerformanceEval extends App {
  
  val parser = new Parser();
  
  val inputFileName = "/home/harper/caida_data/topo-data.caida.org/ITDK/ITDK-2014-04/linknodes.p0";
  val outputFileName = "/home/harper/caida_data/topo-data.caida.org/ITDK/ITDK-2014-04/linknodes.p0out";
  
  println(System.currentTimeMillis());
  heapSortJava(new File(inputFileName),new File(outputFileName));
  println(System.currentTimeMillis());
  
  def heapSort(fileName: File, output: File): Unit = {
    var fileList = Source.fromFile(fileName).getLines
    var dataList: List[NodeLink] = fileList.map[NodeLink](x => parser parse[NodeLink] x) toList;
    
    var sortedList: java.util.List[NodeLink] = new HeapSorter().sort(dataList, 
        (a: NodeLink, b: NodeLink)=> {a.node compare b.node
    });
    var pw = new PrintWriter(output);
    sortedList foreach (x => 
      if(x.nodeRef.ip.isEmpty) 
        pw println("nodelink N%d:L%d".format(x.node,x.link))
      else 
        pw println("nodelink N%d:%s:L%d".format(x.node,x.nodeRef.ip.get,x.link)));

    pw close
  }
  
  def heapSortJava(fileName: File, output: File): Unit = {
    var fileList = Source.fromFile(fileName).getLines
    var dataList: List[NodeLink] = fileList.map[NodeLink](x => parser parse[NodeLink] x) toList;
    
    var sortedList: java.util.List[NodeLink] = new edu.clarkson.cs.clientlib.lang.sort.HeapSorter(false).sort(dataList, 
        new Comparator[NodeLink] {
    	def compare(a: NodeLink, b: NodeLink)= {a.node compare b.node}
    });
    var pw = new PrintWriter(output);
    sortedList foreach (x => 
      if(x.nodeRef.ip.isEmpty) 
        pw println("nodelink N%d:L%d".format(x.node,x.link))
      else 
        pw println("nodelink N%d:%s:L%d".format(x.node,x.nodeRef.ip.get,x.link)));

    pw close
  }
}