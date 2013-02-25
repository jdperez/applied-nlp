// Default values of room number assignments you will build on. Don't
// change the next line.
val defaultRoomNumbers = Map("Sam" -> "312", "Ted" -> "325", "Jane" -> "312")


// Check that there are an even number of command line
// arguments. Print a warning and exit if there aren't.
if (args.length % 2 != 0) {
  println("Please supply an even number of arguments.")
  System.exit(0)
}

// Add the command line information to defaultRoomNumbers to create
// the roomNumbers map.

val initlist = (args.toList.grouped(2).flatMap{ case List(a,b) => Some(a,b); case _ => None}.toMap)
val combinelist = initlist ++ defaultRoomNumbers


// Print out the people and the room they are in, sorted
// alphabetically by name.
println("\nPart (a)")
val keys = combinelist.keys.toList.sorted
// Create a new Map roomsToPeople that maps room numbers to lists of
// the people who are in them.
for (i <- 0 until combinelist.size)
  println(keys(i)+": Room "+(combinelist(keys(i))))


// For each room, print the list of who is in it.
println("\nPart (b)")

val values = combinelist.values.toList
val keys2 = combinelist.keys.toList
val newMap = values.zip(keys2).toList.sorted
val newMap2 = newMap.groupBy(_._1).map { case(k,v) => (k, v.map(_._2))}
val keys3 = newMap2.keys.toList.sorted
for (i <- 0 until newMap2.size){
  print("Room "+keys3(i)+": ")
  for (j <- 0 until newMap2(keys3(i)).size){
    if (j == newMap2(keys3(i)).size -1)
      print(newMap2(keys3(i))(j))
    else
      print(newMap2(keys3(i))(j)+ ",")
  }
  println()
}
