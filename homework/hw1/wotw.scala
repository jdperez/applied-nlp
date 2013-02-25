// Here is the War of the Worlds passage
val wotwRaw = """After the glimpse I had had of the Martians emerging from the cylinder in which they had come to the earth from their planet, a kind of fascination paralysed my actions. I remained standing knee-deep in the heather, staring at the mound that hid them. I was a battleground of fear and curiosity.

I did not dare to go back towards the pit, but I felt a passionate longing to peer into it. I began walking, therefore, in a big curve, seeking some point of vantage and continually looking at the sand heaps that hid these new-comers to our earth. Once a leash of thin black whips, like the arms of an octopus, flashed across the sunset and was immediately withdrawn, and afterwards a thin rod rose up, joint by joint, bearing at its apex a circular disk that spun with a wobbling motion. What could be going on there?"""


// Start your work here. Don't change the lines that print Part (a), etc.



// Part (a)
println("Part (a)")
val count = wotwRaw.split(" ").toList.count(e => e.endsWith("ing"))
println("Number of words ending in 'ing': "+count)

// Part (b)
println("\nPart (b)")
val chopped = wotwRaw.split(" ").toList map (e => e.slice(0,e.size-2))
for (i <- 0 until chopped.size)
  print(chopped(i) + " ")
println()

// Part (c)
println("\nPart (c)")
val slidelist = wotwRaw.split(" ").toList.sliding(2).toList
val atcount = wotwRaw.split(" ").toList.count(e => e == "at")
val atthecount = slidelist.count(e => e(0) == "at" && e(1) == "the")
println("P(the|at) = "+atthecount.toDouble/atcount.toDouble)
