//to test: scala dates.scala "09/07/2011" "09-07-2011" "the 7th of September, 2011"

val monthMap = Map("Janurary" -> 1,"Feburary" ->2,"March"->3,"April"->4,"May"->5,"June"->6,"July"->7,"August"->8,"September"->9,"October"->10,"November"->11,"December"->12)
// Part (a)
val FullFormDate = """(January|February|March|April|May|June|July|August|September|October|November|December) ([1-9]|[1-3][0-9]), (19[0-9][0-9]|20[0-9][0-9])""".r


// Part (b)

val ShortDate = """(0*([1-9][0-2]*)/([0-3][0-9])/(19[0-9][0-9]|20[0-9][0-9])|0*([1-9][0-2]*)-([0-3][0-9])-(19[0-9][0-9]|20[0-9][0-9]))""".r 


// Part (c)


val OrdinalDate = """the ([1-9]|[1-2][0-9]|3[0-1])(st|nd|rd|th) of (January|February|March|April|May|June|July|August|September|October|November|December), (19[0-9][0-9]|20[0-9][0-9])""".r 

// Part (d)

def normalizeDate(inputStr: String): String = {
  if (FullFormDate.pattern.matcher(inputStr).matches) {
    val FullFormDate(month,day,year) = inputStr
    return year+"-"+monthMap(month)+"-"+day 
  }
  else if (ShortDate.pattern.matcher(inputStr).matches) {
    val ShortDate(_,month,day,year,_,_,_) = inputStr 
    if (month == null){
      val ShortDate(_,_,_,_,month,day,year) = inputStr 
      return year+"-"+month+"-"+day
    }
    return year+"-"+month+"-"+day
  }

  else if (OrdinalDate.pattern.matcher(inputStr).matches) {
    val OrdinalDate(day,_,month,year) = inputStr
    return year+"-"+monthMap(month)+"-"+day 
  }
  else
    return "pattern not found on "+ inputStr
}

for (i <- 0 until args.size){
  val normalizeInput = normalizeDate(args(i))
  println(normalizeInput)
}

