// Variables exercise

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

// Add the numbers and print the result.
val sumResult = num1 + num2
println(num1+ " + " + num2+" = "+sumResult)

// Multiply the numbers and print the result.
val multResult = num1 * num2
println(num1+ " * " + num2+" = "+multResult)

// Compare the two numbers and set the "smaller" and "larger"
// variables appropriately.
val smaller = if (num1 < num2) num1 else num2
val larger = if (num1 > num2) num1 else num2

// Print out which number is smaller and which is larger.
println("Smaller: "+smaller)
println("Larger: "+larger)

// Calculate the value of adding the two numbers and multiplying the
// result by the smaller number.
val multiOpResult = (num1 + num2) * smaller
println("("+num1+" + "+num2+") * "+smaller+" = "+multiOpResult) 
