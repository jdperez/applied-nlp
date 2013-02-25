// Read data and compute counts
val removeFirst2TokensInit = """^\w+ \d+ """.r
val removeFirst2TokensRest = """\n\w+ \d+ """.r

val extractedOutput = scala.io.Source.fromFile(args(0)).mkString
val regex1Applied = removeFirst2TokensInit.replaceAllIn(extractedOutput,"")
val regex2Applied = removeFirst2TokensRest.replaceAllIn(regex1Applied," ")
val tokenized = regex2Applied.split(" +|\n").toList.groupBy(_.toString).map{p => (p._1, p._2.length)}
val tokenList = tokenized.toList.map{p => (p._2, p._1)}.sorted.reverse
val hashtagList = tokenList.filter(s => s._2.startsWith("#")).sorted.reverse
val mentionList = tokenList.filter(s => s._2.startsWith("@")).sorted.reverse

// Print output
println("Tokens:")
for (i<- 0 to 9)
  print(tokenList(i)._2+ ":"+tokenList(i)._1 + " ")
println()

println("Hashtags:")
for (i<- 0 to 9)
  print(hashtagList(i)._2 + ":"+hashtagList(i)._1+" ")
println()

println("Mentions:")
for (i<- 0 to 9)
  print(mentionList(i)._2 + ":"+mentionList(i)._1+ " ")
println()
