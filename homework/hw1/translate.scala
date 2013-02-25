import java.util.Scanner
import java.io.File
import scala.collection.JavaConversions._

// Create the translation dictionary
val lines = scala.io.Source.fromFile(args(0)).getLines.toList.map(_.split("\t"))
val translateMap = scala.io.Source.fromFile(args(0)).getLines.map(_.split("\n|\t")).map(fields => fields(0) -> fields(1)).toMap

// Translate the input sentences
for (i <- 1 until args.size){
  println("Portuguese: "+args(i))
  print("English:    ")
  for (j <- 0 until args(i).split(" ").toList.size){
    if (translateMap.contains(args(i).split(" ").toList(j)))
      
      print(translateMap(args(i).split(" ").toList(j))+" ") 
    else 
      print(args(i).split(" ").toList(j).toUpperCase()+" ") 
  }
  println("\n")
}
