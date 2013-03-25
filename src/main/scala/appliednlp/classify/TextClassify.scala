package appliednlp.classify

import java.io._

object TextClassify {
  def main(args: Array[String]) = {
    io.Source.fromFile(args(0)).getLines.map(line => {
       val values = line.trim.split(",").toIndexedSeq
       val features = values.zipWithIndex.take(values.length - 1).map(x => x._2+"="+x._1.trim).mkString(",")
       features + ","+values.last
    }).foreach(println) 
  }
}


object SelectCandidates {
  def main(args: Array[String]) = {
    val predictions = readPredictionSet(args(0)).sorted.map(x => x._2)
    val candidates = readDataSet(args(1))
    val lowestPredictions = predictions.take(1000)
    val remaining = predictions.drop(1000)
    
    lowestPredictions.foreach(i => println(candidates(i)))
    writeTextToFile(args(1), remaining.map(i => candidates(i)).mkString("\n"))
  }
  
  //A function to create and write to files
  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }

  //A simple function for writing strings to a file
  def writeTextToFile(filename: String, text: String) = {
    printToFile(new File(filename))(p => {
      p.println(text)
    })
  }

  def readDataSet(filename: String) = io.Source
                                        .fromFile(filename)
                                        .getLines
                                        .toIndexedSeq
                                        .zipWithIndex
                                        .map(x => (x._2, x._1))
                                        .toMap

  def readPredictionSet(filename: String) = io.Source
                                              .fromFile(filename)
                                              .getLines
                                              .map(x => {
                                                   val IndexedSeq(conf, label) = x.trim.split("\\s+").take(2).toIndexedSeq.reverse
                                                   conf.toDouble
                                                }).toIndexedSeq
                                              .zipWithIndex
}
