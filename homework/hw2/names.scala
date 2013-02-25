// Part (a)
val book = scala.io.Source.fromFile(args(0)).getLines.mkString("\n")
val JustTextRE = """(?s).*\*\*\* START OF THIS PROJECT GUTENBERG EBOOK COMPLETE PG TWAIN \*\*\*(.*)\*\*\* END OF THIS PROJECT GUTENBERG EBOOK.*""".r
val JustTextRE(text) = book
// Part (b)

//val NameRegex = """(?s)(Mr|Mrs|Ms|Miss|Prof|Dr|Rev)[\.]?(\s+[A-Z][\.]?[a-z]*){1,3}""".r
val NameRegex = """(?s)(Mr|Mrs|Ms|Miss|Prof|Dr|Rev)[\.]?(\s+[A-Z][a-z]+)(\s+[A-Z]\.)?(\s+[A-Z][a-z]+)?""".r
//val NameRegex = """(?s)(Mr|Mrs|Ms|Miss|Prof|Dr|Rev)[\.]?((\s+[A-Z][a-z]+){1,2})|\s+[A-Z][a-z]+\s+[A-Z]\.\s+[A-Z][a-z]+)""".r
//val NameRegex = """(?s)(Mr|Mrs|Ms|Miss|Prof|Dr|Rev)[\.]?(\s+[A-Z][a-z]+\s+[A-Z]\.\s+[A-Z][a-z]+)""".r
//val NameRegex = """(?s)(Mr|Mrs|Ms|Miss|Prof|Dr|Rev)[\.]?((\s+[A-Z][a-z]+){1,2})""".r
//NameRegex.findAllIn(book).foreach{matchStr => println(matchStr.replace("\n","").replace("\\s+", " "))}
NameRegex.findAllIn(book).foreach{matchStr => 
  var parse = matchStr.replace("\n"," ")
  var parse2 = parse.replaceAll(" +", " ")
  println(parse2)
}
