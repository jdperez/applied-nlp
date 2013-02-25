package appliednlp.cluster

import nak.cluster._
import nak.util.CollectionUtil._
import chalk.util.SimpleTokenizer

import org.apache.log4j.Logger
import org.apache.log4j.Level

/**
 *  Read data and produce data points and their features.
 *
 *  @param filename the name of the file containing the data
 *  @return a triple, the first element of which is a sequence of id's
 *     (unique for each row / data point), the second element is the sequence
 *     of (known) cluster labels, and the third of which is the sequence of
 *     Points to be clustered.
 */
trait PointCreator extends (String => Iterator[(String,String,Point)])

/**
 * Read data in the standard format for use with k-means.
 */
object DirectCreator extends PointCreator {

 def apply(filename: String) = {
   scala.io.Source.fromFile(filename).getLines.map(x => x.split("\\s+") match {case Array(a,b,c,d) => (a,b,Point(IndexedSeq(c.toDouble,d.toDouble)))})
 }
}


/**
 * A standalone object with a main method for converting the achieve.dat rows
 * into a format suitable for input to RunKmeans.
 */
object SchoolsCreator extends PointCreator {

  def apply(filename: String) = {
    scala.io.Source.fromFile(filename).getLines.toList
    .flatMap(x => x.split("\\s\\s+") match {
        case Array(school,read4,math4,read6,math6) => 
          List((school.replace(" ","_")+"_4th","4",Point(IndexedSeq(read4.toDouble,math4.toDouble)))) ++  
          List((school.replace(" ","_")+"_6th","6",Point(IndexedSeq(read6.toDouble,math6.toDouble))))
        })      
    .toIterator 
  }
}

/**
 * A standalone object with a main method for converting the birth.dat rows
 * into a format suitable for input to RunKmeans.
 */
object CountriesCreator extends PointCreator {
  val countryRE = """([A-Z]+ [A-Z]+|[A-Z.]+|[-]?\d+[.]\d)""".r
  //val damnYouCzechRE = """(?:[A-Z]+ [A-Z]+|[A-Z]+|[0-9][0-9]\.[0-9])""".r
  def apply(filename: String) = {
    scala.io.Source.fromFile(filename).getLines
    .map (x => countryRE.findAllIn(x).toArray match {
    //.map (x => x.split("""([A-Z ]+ |[0-9][0-9]\.[0-9])""") match {
        case Array(country, birthRate, deathRate) =>
          (country.replace(" ","_"), "1", (Point(IndexedSeq(birthRate.toDouble, deathRate.toDouble))))
        })
  }  
}

/**
 * A class that converts the raw Federalist
 * papers into rows with a format suitable for input to Cluster. As part of
 * this, it must also perform feature extraction, converting the texts into
 * sets of values for each feature (such as a word count or relative
 * frequency).
 */
class FederalistCreator(simple: Boolean = false) extends PointCreator {

  def apply(filename: String) = {
    val articles = FederalistArticleExtractor(filename)
    val allArticles = articles.map(elem => elem("text")).mkString
    val allWords = SimpleTokenizer(allArticles).filter(e => !e.matches("[().'\",:;]")).map(e => e.toLowerCase)
    val wordList = allWords.distinct
    articles.map(elem => {
      val tokens = SimpleTokenizer(elem("text"))
      val count = 
        if (simple) {
          extractSimple(tokens)
        }
        else {
          val x = extractFull(tokens, wordList)
          //println(x)
          x
        }
      //println(count(0).numDimensions) 
      (elem("id"),elem("author"),count(0))
    }).toIterator
  }

  /**
   * Given the text of an article, compute the frequency of "the", "people"
   * and "which" and return a Point per article that has the frequency of
   * "the" as the value of the first dimension, the frequency of "people"
   * for the second, and the frequency of "which" for the third.
   *
   * @param texts A sequence of Strings, each of which is the text extracted
   *              for an article (i.e. the "text" field produced by
   *              FederalistArticleExtractor).
   */
  def extractSimple(texts: IndexedSeq[String]): IndexedSeq[Point] = {
    val theCnt = texts.filter(e => e.toLowerCase == "the").size
    val peopleCnt = texts.filter(e => e.toLowerCase == "people").size
    val whichCnt = texts.filter(e => e.toLowerCase == "which").size
    IndexedSeq(Point(IndexedSeq(theCnt.toDouble,peopleCnt.toDouble,whichCnt.toDouble)))
  }

  /**
   * Given the text of an article, extract features as best you can to try to
   * get good alignment of the produced clusters with the known authors.
   *
   * @param texts A sequence of Strings, each of which is the text extracted
   *              for an article (i.e. the "text" field produced by
   *              FederalistArticleExtractor).
   */
  def extractFull(articleTokens: IndexedSeq[String], wordList:IndexedSeq[String]): IndexedSeq[Point] = {
    //val x = wordList.map(word => Point(IndexedSeq(getWordCount(word,texts.toList))))
    //x
    IndexedSeq(Point(getWordCount(articleTokens,wordList)))
  }


  def getWordCount(tokens:IndexedSeq[String], wordList: IndexedSeq[String]): IndexedSeq[Double] = {
    //(wordList.count(_ == tokens).toDouble)
    (wordList.map(x => {
        
      tokens.filter(_ == x).size.toDouble
    }))
  }
}

object FederalistArticleExtractor {
  /**
   * A method that takes the raw Federalist papers input and extracts each
   * article into a structured format.
   *
   * @param filename The filename containing the Federalist papers.
   * @return A sequence of Maps (one per article) from attributes (like
   *         "title", "id", and "text") to their values for each article.
   */
  def apply(filename: String): IndexedSeq[Map[String, String]] = {

    // Regex to identify the text portion of a document.
    val JustTextRE = (
      """(?s)\*\*\* START OF THIS PROJECT GUTENBERG.+""" +
      """\*\*\*(.+)\*\*\* END OF THIS PROJECT GUTENBERG""").r

    // Regex to capture different parts of each article.
    val ArticleRE = (
      """(?s)(\d+)\n+""" + // The article number.
      """(.+?)\n+""" + // The title (note non-greedy match).
      """((?:(?:For|From)[^\n]+?)?)\s+""" + // The publication venue (optional).
      """((?:(?:Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday).+\d\d\d\d\.)?)\n+""" + // The date (optional).
      """((?:MAD|HAM|JAY).+?)\n+""" + // The author(s).
      """(To the [^\n]+)""" + // The addressee.
      """(.+)""" // The text.
      ).r

    val book = io.Source.fromFile(filename).mkString
    val text = JustTextRE.findAllIn(book).matchData.next.group(1)
    val rawArticles = text.split("FEDERALIST.? No. ")

    // Use the regular expression to parse the articles.
    val allArticles = rawArticles.flatMap {
      case ArticleRE(id, title, venue, date, author, addressee, text) =>
        Some(Map("id" -> id.trim,
          "title" -> title.replaceAll("\\n+", " ").trim,
          "venue" -> venue.replaceAll("\\n+", " ").trim,
          "date" -> date.replaceAll("\\n+", " ").trim,
          "author" -> author.replaceAll("\\n+", " ").trim,
          "addressee" -> addressee.trim,
          "text" -> text.trim))

      case _ => None
    }.toIndexedSeq

    // Get rid of article 71, which is a duplicate, and return the rest.
    allArticles.take(70) ++ allArticles.slice(71, allArticles.length)
  }

}
