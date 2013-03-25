package appliednlp.classify

/**
 * An application that takes a gold labeled file and a file containing
 * predictions, and then computes the accuracy for the top-third most
 * confident instances and the accuracy for the bottom-third (least 
 * confident instances).
 */
object ConfidenceScorer {

  def main(args: Array[String]) {
    val testSet = readTestSet(args(0))
    val predictionSet = readPredictionSet(args(1)).sorted.reverse
    val thirdSize = ((predictionSet.length.toDouble)/3.0).toInt
    val initialThird = predictionSet.length - thirdSize*2
    

    val high = predictionSet.take(initialThird) 
    val mid = predictionSet.drop(initialThird).take(thirdSize)
    val low = predictionSet.drop(initialThird + thirdSize)

    val highAcc = high.map(x => if(testSet(x._2) ==  x._1._2) 1.0 else 0.0).sum/high.length.toDouble
    val midAcc = mid.map(x => if(testSet(x._2) ==  x._1._2) 1.0 else 0.0).sum/mid.length.toDouble
    val lowAcc = low.map(x => if(testSet(x._2) ==  x._1._2) 1.0 else 0.0).sum/low.length.toDouble

    println("High confidence accuracy: " + highAcc*100.0)
    println("Mid confidence accuracy: " + midAcc*100.0)
    println("Low confidence accuracy: " + lowAcc*100.0)
  } 
  
  def readTestSet(filename: String) = io.Source
                                        .fromFile(filename)
                                        .getLines
                                        .map(_.split(",").last)
                                        .toIndexedSeq
                                        .zipWithIndex
                                        .map(x => (x._2, x._1))
                                        .toMap

  def readPredictionSet(filename: String) = io.Source
                                              .fromFile(filename)
                                              .getLines
                                              .map(x => {
                                                   val IndexedSeq(conf, label) = x.split("\\s+").take(2).toIndexedSeq.reverse
                                                   (conf.toDouble, label)
                                                }).toIndexedSeq
                                              .zipWithIndex

}
