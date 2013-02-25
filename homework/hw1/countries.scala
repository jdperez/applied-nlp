// Here are some lists of countries in North America and South
// America. (Don't change the next two lines.)
val NorthAmerica = List("USA", "Mexico", "Canada")
val SouthAmerica = 
  List("Venezuela", "Bolivia", "Argentina", "French Guiana", "Paraguay", "Chile", 
       "Ecuador", "Brazil", "Guyana", "Peru", "Uruguay", "Colombia", "Suriname")


////////////////////////////////////////////////////////////////////////
// Start your work here

// Get the arguments from the command line, convert them to a sorted
// List that is stored in the variable "countries" and print them.
println("Part (a)")
val argList = List(args: _*).sorted
print("Considering: ")
for (i <- 0 until argList.size) print(argList(i) + " ")
println()

// For each test country, see whether it is in North America, South
// America, or if we don't have any information about it (i.e. it is
// an "unknown" country).
println("\nPart (b)")

val reverseList = argList.reverse
for (i <- 0 until argList.size) {
  print(reverseList(i))
  if (NorthAmerica.contains(reverseList(i))) println(": North America")
  else if (SouthAmerica.contains(reverseList(i))) println(": South America")
  else println(": ???")
}
  

// Print out how many unknown countries were given. Make sure that
// you deal with English agreement appropriately.
println("\nPart (c)")
val unionList = NorthAmerica.union(SouthAmerica)
val intersect = argList.intersect(unionList)
val unknownNum = argList.size - intersect.size
if (unknownNum == 1) println("There was 1 unknown country.")
else println("There were 2 unknown countries.")


