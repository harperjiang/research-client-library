package edu.clarkson.cs.clientlib.caida.itdk.mapreduce

import scala.io.Source
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.PrintWriter
import java_cup.parser
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink
import java.io.File
import scala.io.Source
import edu.clarkson.cs.clientlib.lang.HeapSorter
import scala.sys.process.stringToProcess

object Sorting {

  def sort(fileName: String, output: String)(comparator: (String, String) => Int): Unit = {
    val f = new File(fileName);
    val line = ("cat " + fileName) #| ("wc -l") !!
    val level = 5
    val split = Math.pow(2, level).toInt;
    val fileLine = Integer.parseInt(line trim) / split;
    val smallFiles = new Array[File](split);
    val smallSorted = new Array[File](split);

    var lines =
      for (line <- Source.fromFile(f).getLines) yield line;

    var count = 0;
    for (i <- 0 to split - 1) {
      smallFiles(i) = new File(fileName + ".p" + i);
      smallSorted(i) = new File(fileName + ".ps0_" + i);
      // Fill in the file
      var pw = new PrintWriter(smallFiles(i));

      if (i == split - 1) {
        lines foreach (pw println _)
      } else {
        for (k <- 0 to fileLine - 1)
          pw println lines.next
      }
      pw.close

      heapSort(smallFiles(i), smallSorted(i), comparator)
    }
    for (round <- 0 to level - 1)
      for (fi <- 0 to Math.pow(2, level - round).intValue - 1 by 2)
        mergeSort(fileName + ".ps" + round + "_" + fi,
          fileName + ".ps" + round + "_" + (fi + 1),
          fileName + ".ps" + (round + 1) + "_" + (fi / 2), comparator);
    // Rename the merged file
    "mv %s.ps%d_%d %s".format(fileName, level, 0, output) !;
    // Delete intermediate files
    "rm %s.p*".format(fileName) !;
  }

  private def mergeSort(fileA: String, fileB: String, output: String, comparator: (String, String) => Int) {
    var aline = Source.fromFile(fileA).getLines;
    var bline = Source.fromFile(fileB).getLines;

    var pw = new PrintWriter(new File(output));

    var a = None: Option[String];
    var b = None: Option[String];

    while (aline.hasNext && bline.hasNext) {
      if (a isEmpty) a = Some(aline.next);
      if (b isEmpty) b = Some(bline.next);
      comparator(a get, b get) match {
        case x if x <= 0 => {
          pw println (a.get)
          a = None;
        }
        case x if x >= 0 => {
          pw println (b.get)
          b = None;
        }
        case _ => {}
      }
    }
    if (!(a isEmpty)) {
      pw println (a get)
    }
    if (!(b isEmpty)) {
      pw println (b get)
    }
    if (aline.hasNext) {
      aline foreach (pw println _);
    } else {
      bline foreach (pw println _);
    }

    pw.close
  }

  private def heapSort(fileName: File, output: File, comparator: (String, String) => Int): Unit = {
    var dataList = Source.fromFile(fileName).getLines.toList

    var sortedList = new HeapSorter().sort(dataList, comparator);
    var pw = new PrintWriter(output);
    sortedList foreach (pw.println(_))
    pw close
  }

}