package appliednlp.app

import org.apache.log4j.Level
import org.apache.log4j.Logger
import nak.cluster.Point
import nak.cluster.ClusterConfusionMatrix
import nak.cluster.ClusterReport
import nak.cluster.Kmeans
import nak.cluster.DistanceFunction
import nak.cluster.PointTransformer

import _root_.cc.mallet.util._
import _root_.cc.mallet.types._
import _root_.cc.mallet.pipe._
import _root_.cc.mallet.pipe.iterator._
import _root_.cc.mallet.topics._

import _root_.java.util._
import _root_.java.util.regex._
import _root_.java.io._

import appliednlp.cluster._

/**
 * A standalone object with a main method for reading an input file and running
 * k-means with different options.
 */
object TopicModel {

  def main(args: Array[String]) {
    
    // Your code starts here. You'll use and extend it during every problem.
    val inputFile = args(0)
    //val parsedData = new FederalistCreator(false)(inputfile).toList
    //val articles = FederalistArticleExtractor(inputFile).map(_("text"))
    
    val pipeList: ArrayList[Pipe] = new ArrayList()
    pipeList.add( new CharSequenceLowercase() )
    pipeList.add( new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")) );
            pipeList.add( new TokenSequenceRemoveStopwords(new File(inputFile), "UTF-8", false, false, false) );
                      pipeList.add( new TokenSequence2FeatureSequence() )

  }
}
