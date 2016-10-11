// 1) compile with scalac app8.scala in the same dir
//    generates Cat.class and Cat$.class
//    and Cat$$anonfun$main$1.class   (???)

// the command "scala Cat 1 2 3" should print:
// hello cat
// 1, 2, 3

object Cat {
  def main(args: Array[String]) {
    println("hello cat")
    println(args.mkString(", "))
  }
}



// alternative cat body to interact with date obj:
// call it with scala Cat Date.scala

/*
 for (arg <- args) {
println( io.Source.fromFile(arg).mkString )
}


 object Date {
def main(args: Array[String]) {
println(new java.util.Date)
}
}


 
 */
