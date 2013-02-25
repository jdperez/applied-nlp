// Get the arguments from the command line and check there are two of
// them and that they are all integers. (Don't change the next block
// of code.)
val IntegerPattern = """-?\d+""".r
if (args.length != 2 || args.exists(arg => !IntegerPattern.pattern.matcher(arg).matches)) {
  println("Incorrect arguments to variables.scala. Please provide two integers.")
  System.exit(0)
}

////////////////////////////////////////////////////////////////////////
// Start your work here

// Obtain the numbers, converting them from Strings to Ints while
// doing so. 
val num1 = args(0).toInt
val num2 = args(1).toInt

// Check that the first number is smaller than the second. If it
// isn't, print a warning message and exit.
if (num2 < num1) {println("Please make sure that the first number is smaller than the second."); System.exit(0)}

// Create a range using "to" and the numbers provided as arguments. 
def sum(args: Int*) : Int = {
if (args.length == 0) 0
else args.head + sum(args.tail : _*)
}

val result = sum(num1 to num2: _*)

// Print the numbers in the range with "+" in between each one (hint:
// use mkString) and the result of adding them all together (hint: use
// sum)
for (i <- num1 to num2) if (i != num2) print(i + " + ") else print(i)
println(" = " + result)
