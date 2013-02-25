import scala.collection.mutable.ListBuffer

// Read streaming tweets from standard input and calculate the top
// hashtags for each batch.
val IntegerPattern = """-?\d+""".r
if (args.length != 1 || args.exists(arg => !IntegerPattern.pattern.matcher(arg).matches)) {
  println("Incorrect argument to StreamingCounter.scala. Please provide an integer.")
  System.exit(0)
}
var curSize = 0
val batchSize = args(0).toInt
var globalList:Map[String,Int] = Map()

while(true){
  curSize += batchSize
  var batchList = new ListBuffer[String]()
  var tweetCount = 0
  while(tweetCount < batchSize){
    var line = readLine
    if(line matches ".*screen_name.*")
      batchList += line
      tweetCount += 1
  } 
  var textList = new ListBuffer[String]() 
   for (i <- 0 until batchList.size){
    val user = """.*"text":"(.*)","source.*screen_name":"(\w+)".*"followers_count":(\d+),.*""".r
    val user(text,_,_) = batchList(i)
    textList += text+" "
  }
  println("\nNumber of tweets seen: "+curSize+"\n")
  val rawTextTokens = textList.mkString.split(" +").toList
  val tokenCounts = rawTextTokens.groupBy(_.toString).map{p => (p._1,p._2.length)}.toList.map{p => (p._2,p._1)}.sorted.reverse

  if (curSize == batchSize){
    globalList = tokenCounts.map{p => (p._2,p._1)}.toMap
  } else {
    for (i <- 0 until tokenCounts.size){
      if (globalList contains tokenCounts(i)._2){
        globalList = globalList + (tokenCounts(i)._2 -> (globalList(tokenCounts(i)._2) + tokenCounts(i)._1))
      } else {
        globalList = globalList + (tokenCounts(i)._2 -> tokenCounts(i)._1)
      }
    }    
  }

  val batchCounts = tokenCounts.filter(s => s._2.startsWith("#"))
  print("\n[Batch] ")
  for (i <- 0 until batchCounts.size
       if i < 10){
    print(batchCounts(i)._2+":"+batchCounts(i)._1+ " ")
  } 
  println("\n")
 

  val globalCounts = globalList.toList.map{p => (p._2,p._1)}.sorted.reverse.filter(s => s._2.startsWith("#")) 
  print("\n[Global] ")
  for (i <- 0 until globalCounts.size
         if i < 10){
    print(globalCounts(i)._2+":"+globalCounts(i)._1+ " ")
  } 
  println("\n\n")
}
