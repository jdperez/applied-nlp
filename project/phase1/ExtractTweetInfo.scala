// Read in JSON tweets and output username, follower count and text
// for each.

//json file is .dos file, so might have to execute fromdos to remove trailing chars
val rawTweets = scala.io.Source.fromFile(args(0)).getLines.toList
for (i <- 0 until rawTweets.size){
  val user = """.*"text":"(.*)","source.*screen_name":"(\w+)".*"followers_count":(\d+),.*""".r
  val user(text,screen_name,followCnt) = rawTweets(i)
  println(screen_name+" "+followCnt+" "+text)
}
