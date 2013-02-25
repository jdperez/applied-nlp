// Get the argument from the command line and check that it is an
// integer.  (Don't change the next block of code.)
val IntegerPattern = """-?\d+""".r
if (args.length != 1 || !IntegerPattern.pattern.matcher(args(0)).matches) {
  println("Incorrect arguments to variables.scala. Please provide one integer.")
  System.exit(0)
}

////////////////////////////////////////////////////////////////////////
// Start your work here

// Obtain the number, converting it from a String to an Int.
val num = args(0).toInt

// Check that the number is in the right range. If it isn't, print a
// warning message and exit.
if (num < 0) {println("Please supply a number greater than or equal to 0."); System.exit(0)}

// Compute the factorial using recursion.
def fact(n:Int) : Int = {
  if (n == 0) 1 else if (n == 1) 1 else n * fact(n-1)
}

val result = fact(num)
println(num + "! = " + result)

// Print the result

