// Part a
val strList = args(0).split(" ").toList.sortWith((s,t) => s.size < t.size)
val sortzip = strList.sortWith((s,t) => s.size < t.size || (s.size == t.size && s.charAt(0) < t.charAt(0)))
print("\nSorted: ")
for (i <- 0 until sortzip.size)
  print(sortzip(i)+":"+sortzip(i).size+ " ")
println("\n")

// Part b
val lessthree = sortzip filter (_.length <= 3)
val gt_three_lt_eleven = sortzip filter (_.length > 3) filter (_.length < 11)
val rev = gt_three_lt_eleven.reverse
val gt_eleven = sortzip filter (_.length >= 11)
print("Remixed: ")
for (i <- 0 until lessthree.size)
  print(lessthree(i)+":"+lessthree(i).size+ " ")
for (i <- 0 until rev.size)
  print(rev(i)+":"+rev(i).size+ " ")
for (i <- 0 until gt_eleven.size)
  print(gt_eleven(i) +":"+gt_eleven(i).size+" ")
println()
