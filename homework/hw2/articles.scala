// Part (a)

val book = scala.io.Source.fromFile(args(0)).getLines.mkString("\n")
val JustTextRE = """(?s).*\*\*\* START OF THIS PROJECT GUTENBERG EBOOK THE FEDERALIST PAPERS \*\*\*(.*)\*\*\* END OF THIS PROJECT GUTENBERG EBOOK.*""".r
val JustTextRE(text) = book

val rawArticles = text.toString.split("FEDERALIST.? No..").drop(1)
// Part (c)

/*
val ArticleRE = (
  """(?s)^([1-9][0-9]?)"""+ //gets id 
  """\n{2,4}((?:[A-Za-z\-\(\)]+| +[A-Za-z\-\(\)]+[,]?|\n{1,2}[A-EG-Za-z\-]+|\n\n\([A-Za-z\-i\)]+)+)"""+ //gets title 
  """(?:(?:\n\n\(.+\)|\s+\(.+\))?\n{1,4}((?:From the|For the|From M[cC]LEAN'[Ss]| +[A-Za-z]+[.,]?)+\.))?"""+  // gets venue
  //"""(?:(?:\n\n| ){1}((?:Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday),.+, [1-9][1-9][1-9][1-9]\.))?"""+ // gets date
  """(?:(?:\n\n| ){1}((?:Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday),.+, [1-9][1-9][1-9][1-9]\.))?.*""" // gets date
  //"""\n{3,}[A-Z]+.*""" //gets name
).r
*/
  //println("id is "+id + "\ntitle is "+title +"\nvenue is " + venue + "\ndate is " + date + "\nauthor is "+author+ "\naddress is "+addressee+"\ntxt is:\n"+txt+"\n")

val ArticleRE = (
  """(?s)^([1-9][0-9]?)"""+ //gets id 
  """\n{2,4}((?:[A-Za-z\-\(\)]+| +[A-Za-z\-\(\)]+[,]?|\n{1,2}[A-Za-z\-]+|\n\n\([A-Za-z\-i\)]+)+)"""+ //gets title 
  """(?:\n{1,4}((?:From the|For the|From M[cC]LEAN'[Ss]| +[A-Za-z]+[.,]?)+\.))?"""+  // gets venue
  """(?:(?:\n\n| ){1}((?:Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)?,? ?[A-Za-z]+ [1-9][0-9]?, [1-9][1-9][1-9][1-9][.]?))?"""+ // gets date
  """\n+((?:[A-Z]+| [A-Z]+)+)"""+ //gets author
  """\n+((?:[A-Za-z]+| *[A-Za-z]+)+)[.:]?"""+ //gets addressee
  """\n+(.*)"""//gets text 
).r

def createMap(input:String): Map[String,String] = {
  val ArticleRE(id,title,venue,date,author,addressee, txt) = input
  return Map("id:"->id,"title:"->title.replace("\n"," "),"venue:"->venue,"date:"->date, "author:"->author,"addressee:"->addressee,"text:"->txt)
}

val articles = rawArticles.map(article => createMap(article))


// Part (d)

println()
for (i <- 0 until articles.size){
  articles(i).foreach( 
    e =>
    if (e._2 == null)
      println(e._1 + " ")
    else if(e._1 == "text:")
      println(e._1 + " "+e._2.substring(0,300))
    else
      println(e._1 + " "+e._2)
       
  )
  println()
}

