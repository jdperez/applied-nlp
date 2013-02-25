// Here is the War of the Worlds passage
val wotwRaw = """After the glimpse I had had of the Martians emerging from the cylinder in which they had come to the earth from their planet, a kind of fascination paralysed my actions. I remained standing knee-deep in the heather, staring at the mound that hid them. I was a battleground of fear and curiosity.

I did not dare to go back towards the pit, but I felt a passionate longing to peer into it. I began walking, therefore, in a big curve, seeking some point of vantage and continually looking at the sand heaps that hid these new-comers to our earth. Once a leash of thin black whips, like the arms of an octopus, flashed across the sunset and was immediately withdrawn, and afterwards a thin rod rose up, joint by joint, bearing at its apex a circular disk that spun with a wobbling motion. What could be going on there?"""


// Start your work here. Don't change the lines that print Part (a), etc.

val oneSyllableRegex = """\b+[bcdfghjklmnpqrstwxyzBCDFGHJKLMNPQRSTVWXYZ]*[aeiouAEIOU]+[bcdfghjklmnpqrstwxyzBCDFGHJKLMNPQRSTVWXYZ]*\b+""".r

// Part (a)
println("\nPart (a)")
val words = oneSyllableRegex.findAllIn(wotwRaw)
println("Number of one syllable words on full String: " + words.toList.size)



// Part (b)
println("\nPart (b)")
val wotwList = wotwRaw.split(" +|\n").toList
val oneSyllableListRegex = """[bcdfghjklmnpqrstwxyzBCDFGHJKLMNPQRSTVWXYZ]*[aeiouAEIOU]+[bcdfghjklmnpqrstwxyzBCDFGHJKLMNPQRSTVWXYZ]*[.?,]*""".r

var cnt = 0
for (i <- 0 until wotwList.size){
  if (wotwList(i).contains("-")){
    val hyphenWord = wotwList(i).split("-").toList.foreach (
      x => if (oneSyllableListRegex.pattern.matcher(x).matches)
        cnt += 1
    )
  }
  else {
    if (oneSyllableListRegex.pattern.matcher(wotwList(i)).matches){
      cnt += 1
    }
  }
}
println("Number of one syllable words on split String: "+ cnt)
