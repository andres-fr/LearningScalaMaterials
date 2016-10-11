// see http://ensime.github.io/build_tools/sbt/
// 1) build/configure project once (cd, then sbt, then ensimeConfig)
// 2) create empty build.sbt file
// 3) make any .scala file and start the ensime server with M-x ensime
// 4) C-c C-v z to start REPL session
// 5) C-c C-v b to eval buffer, C-c C-v C-r to eval region (notice that it is C-r)


// (setf ensime-sem-high-enabled-p t)
// (setf ensime-typecheck-when-idle nil)


// added this to init.el, to use C-M-RET and C-, C-RET for repl interaction
// ;;;;;;;;;;;;;;;;;;;;;;;;
// ;;;;; SCALA CONFIG
// ;;;;;;;;;;;;;;;;;;;;;;;;
// (add-hook 'ensime-mode-hook
//           (lambda ()
//             (local-set-key (kbd "C-c M-l") #'my-ensime-clear-repl)
//             (local-set-key (kbd "C-, <C-return>") #'my-ensime-eval-last-paragraph)
//             (local-set-key (kbd "C-M-<return>") #'my-ensime-eval-current-line)))


// nice definition of variable : In computer science the term variable
// typically refers to a unique identifier corresponding to an allocated
// or reserved memory space, into which values can be stored and from
// which values can be retrieved




////////////////////////////////////////////////////////////////////////////////
//////     MORE COLLECTIONS         ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// MUTABLE COLLECTIONS:
// are "safe" when mutation happens only within a fn, then converted to immutable
// collections of *.immutable.* are by default in the namespace; for mutable:
collection.mutable.Buffer
collection.mutable.Set
collection.mutable.Map
// !IMPORTANT! to convert back to immut. use toList, toSet, toMap respectively
// to convert immut. to mut, use toBuffer

// BUFFER: general-purpose mutable sequence, supports adding to its beginning, middle, and end
val nums = collection.mutable.Buffer[Int](1,2)
for(i<- 3 to 20) nums+=i
nums.mkString(", ")
nums.trimStart(1) // removes n elements from the beginning


// COLLECTION BUILDER: a buffer may be too flexible: builder supports appending only
// use newBuilder, example with an immutable Set:
val b = Set.newBuilder[Char] // the type is kind of important
b += 'h'
b ++= List('e','l', 'l', 'o')
val helloSet = b.result
helloSet


// ARRAYS: IMMUTABLE SIZE, MUTABLE CONTENT
// USAGE NOT RECOMMENDED (SEE OTHER MUTABLE COLLECTIONS).It’s not officially a
// collection, be‐ cause it isn’t in the “scala.collections” package and
// doesn’t extend from the root Itera ble type (although it has all of
// the Iterable operations like map and filter ). The Array type is
// actually just a wrapper around Java’s array type with an advanced
// feature called an implicit class allowing it to be used like a
// sequence. Scala provides the Array type for compatibility with JVM
// libraries and Java code and as a backing store for indexed col‐
// lections, which really require an array to be useful.
val colors = Array("red", "green", "blue")
colors(0) = "pink"
println(colors.mkString(", ")) // mkString is necessary to print the contents

val files = new java.io.File(".").listFiles // returns an array with files here
val check = files.map(_.getName).exists(_.endsWith(".scala"))
val check = files.map(_.getName).forall(_.endsWith(".scala"))

// Seq(IndexedSeq(Vector, Range), LinearSeq(Queue/Stack, List, Stream)).
// arrays could be considered as indexed, since they have access in O(1).
// Nil inherits from all linear ones
// Seq is abstract, so the Seq constructor makes a List actually.
// Same for IndexedSeq, which makes a Vector(list backed by an array instance).
// Stream is a lazy list: elements are added as they are accessed.
// STRING is also an inmutable collection of chars:
val hi = "Hello, " ++ "worldly" take 12 replaceAll ("w","W")



// STREAMS: starting elts+rec fn. Elts are added when accessed, and cached for later use.
//theoretically infinite collection, having head and tail. Construction:
def inc(i: Int): Stream[Int] = Stream.cons(i, inc(i+1)) // same as i #:: inc(i+1)
val s = inc(1) // s is now Stream(1, ?)
s.take(5).toList // returns the list 1,2,3,4,5
s // s is now Stream(1,2,3,4,5,?)
s.take(5).toList


// MONADIC COLLECTIONS: support transformative operations like the
// ones in Iterable but can contain no more than one element

// // OPTION COLLECTIONS: GENERAL PURPOSE, ALSO KIND OF REPLACEMENT FOR NULL
// As a collection whose size will never be larger than one, the Option
// type represents the presence or absence of a single value. This
// potentially missing value (e.g., it was never initialized, or could
// not be calculated) can thus be wrapped in an Option collection and
// have its potential absence clearly advertised.  Some developers see
// Option as a safe replacement for null values, notifying users that the
// value may be missing and reducing the likelihood that its use will
// trigger a Null PointerException . Others see it as a safer way to
// build chains of operations, ensuring that only valid values will
// persist for the duration of the chain.  The Option type is itself
// unimplemented but relies on two subtypes for the implemen‐ tation:
// Some , a type-parameterized collection of one element; and None , an
// empty col‐ lection. The None type has no type parameters because it
// never contains contents. You can work with these types directly, or
// invoke Option() to detect null values and let it choose the
// appropriate subtype
Option("asdf") // returns Option[String] = Some("asdf")
Option(null)   // returns Option[Null] = None

// operations to check if defined (some vs none):
val a = Option(null)
a.isDefined //false
a.isEmpty // true

// example: AVOID Option.get() !!!!!!!!!!!!!!! methods: fold, getOrElse, orElse, match
def div(a:Double, b:Double):Option[Double] = {if (b==0) None else Option(a/b)}
div(1, 0) // None
div(10, 6) //1.6666666667

// A function that returns a value wrapped in the Option collection is
// signifying that it may not have been applicable to the input data, and
// as such may not have been able to return a valid result. It offers a
// clear warning to callers that its value is only potential, and ensures
// that its results will need to be carefully handled. In this way,
// Option provides a type-safe option for handling function results, far
// safer than the Java standard of returning null values to indicate
// missing data.


// TRY COLLECTIONS: FOR SUCCESSFUL VALUES
throw new Exception("this is a custom exception")

// to catch it, wrap it with the util.Try monadic collection:
// scala supports try/catch blocks, but this monadic approach is recommended:
// like Option, it has two implementations: Success (fn result) and Failure (exception)
def optionalException(b:Boolean):String = {
  if (b) "this is a string" else throw new Exception("failed!!!")}

val v = util.Try(optionalException(true)) // Success("this is a string")

// handling errors:
def nextError = util.Try{ 1 / util.Random.nextInt(2) } // this fn generates one 50%
nextError.flatMap{_=> nextError} // in case of success, calls itself again: error 75%
nextError.foreach(x=> println(s"success $x")) // executes only in case of success
nextError.getOrElse(-1) // 1 or -1, 50%
nextError.orElse(nextError) // iferror, try again. Error rate: 25%
nextError.toOption // becomes Some/None. May lose the embedded Exception
nextError.map(_*5) // in case of success, executes fn. else throws exc.

nextError match{
  case util.Success(x) => x;
  case util.Failure(error) => -1;
}

// COMMON EXAMPLE:
val input = "        123asdf   "
val result = util.Try(input.toInt) orElse util.Try(input.trim.toInt) orElse util.Try(321)
result.foreach{x=>println(s"input was parsed to $x")}

// APPROACH MAPPING TO OPTION (can be completed with some more print statements or so
val input = "        123asdf   "
val result = util.Try(input.trim.toInt)
val x = result match{
    case util.Success(x) => Some(x)
    case util.Failure(ex) => {println(s"exception: $ex"); None}
  }
//THIS IS FUNCTIONALLY THE SAME AS toOption:
x==result.toOption





// FUTURE COLLECTIONS: FOR EVENTUAL VALUES   concurrent.Future
// Also a monadic collection, provides many safe operations.
// Invoking a future with a function will execute that function in a
// separate JVM thread while the current thread continues to operate.

// Before creating the future, it is necessary to specify the “context”
// in the current session or application for running functions
// concurrently. The default “global” context makes use of Java’s thread
// library:
import concurrent.ExecutionContext.Implicits.global
val f = concurrent.Future { println("hi") }
val f = concurrent.Future { Thread.sleep(5000); println("hi") } // SLEEP NOT EFFICIENT!!



// // ASYNCHRONOUS: "main" and "future" thread in "parallel" A future
// handled this way will eventually return a util.Try containing either
// its function’s return value or an exception. In case of success
// (with a return value), the chained function or future will be passed
// to the return value and converted into a future to return its own
// success or failure. In case of a failure (i.e., an exception was
// thrown), no additional functions or futures will be executed. In
// this way, the future-as-monadic-collection is just a chain in a
// sequence of operations that carry an embedded value. This is similar
// to Try , which breaks the chain when a failure is reached, and Option
// , which breaks the chain when the value is no longer present.
def nextFtr(i:Int=0) = concurrent.Future{ // i is kind of the ID, i guess
    def rand(x:Int) = util.Random.nextInt(x)
    Thread.sleep(rand(5000)) // sleeps between 0 and 5s
    if(rand(3)>0) (i+1) else throw new Exception // 33% throws exception
  }
//CALLBACK FUNCTION: like onComplete, onSuccess

nextFtr(1).fallbackTo(nextFtr(2)) // Chains the second future to the first and returns a new overall future. If the first is unsuccessful, the second is invoked.
nextFtr(1).flatMap(nextFtr) // Chains the second future to the first and returns a new overall future. If the first is successful, its return value will be used to invoke the second.
nextFtr(1).map(_*2) // Chains the given function to the future and returns a new overall future. If the future is successful, its return value will be used to invoke the function.
nextFtr().onComplete{_ getOrElse 0} // After the future’s task completes, the given function will be invoked with a util.Try containing a value (if success) or an exception (if failure).
nextFtr().onFailure{case _ => "Error!"} // If the future’s task throws an exception, the given function will be invoked with that exception.
nextFtr().onSuccess{ case x => s"Got $x" } // If the future’s task completes successfully, the given function will be invoked with the return value.
concurrent.Future.sequence(List(nextFtr(1),nextFtr(5))) // Runs the futures in the given sequence concurrently, returning a new future. If all futures in the sequence are successful, a list of their return values will be returned. Otherwise the first exception that occurs across the futures will be returned.


// // SYNCHRONOUS: To block the current thread and wait for another
// thread to complete, use concurrent.Await.result() , which takes the
// background thread and a maximum amount of time to wait. If the future
// completes in less time than the given duration, its result is
// returned, but a future that doesn’t complete in time will result in a
// java.util.concurrent.TimeoutException.

import concurrent.duration._ // for specifying time spans as well as the types for their units
val maxTime = Duration(10, SECONDS) // like this

def nextFtr(i:Int=0) = concurrent.Future{ // i is kind of the ID, i guess
    def rand(x:Int) = util.Random.nextInt(x)
    Thread.sleep(rand(5000)) // sleeps between 0 and 5s
    if(rand(3)>0) (i+1) else throw new Exception // 33% throws exception
  }
val amount = concurrent.Await.result(nextFtr(5), maxTime)



////////////////////////////////////////////////////////////////////////////////
//////     EXERCISES     ///////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////



// 1) Both solutions in O(n)

def fib1(n:Int):List[Int] = {
  val b = collection.mutable.Buffer(1, 1)
  if (n>2) for (x<-1 to n-2){(b(0)+b(1))+=:b}
  b.toList.reverse
} // a builder wouldn't be appropiate since doesn't support +=: (left consing)

@annotation.tailrec
def fib2(n:Int, result:List[Int]=List(1,1)):List[Int]={
  if (n<=2) result else fib2(n-1, (result(0)+result(1))::result)
}


// 4)

def stringProd1 (a:String, b:String) = {
  val x: util.Try[Double] = util.Try(a.trim.toDouble)
  val y: util.Try[Double] = util.Try(b.trim.toDouble)
  util.Try(x.get*y.get) //.getOrElse(x : util.Failure =>  println(x))
  //z match {
   // case util.Success(x) => x
    //case util.Failure(ex) => {println(s"exception: $ex"); None}
  //}
}

stringProd1(" 3.243432  ", "   1211 ").flatten


// 5)
def safeGetProperty(s:String):String = {
  util.Try(System.getProperty(s.trim)) match{
    case util.Failure(_)| util.Success(null) => "unknown property!!"
    case c => c.get
  }
}
safeGetProperty("") // exception
safeGetProperty("asdf") // null
safeGetProperty("java.home") // ok


// 6)
val url = "https://github.com/andres-fr/bachelor-thesis/commits/master.atom"

io.Source.fromURL(url).getLines.map(_.trim).foreach(println _)



def urlToList(s:String)= {
  val s = io.Source.fromURL(url)
  val text = s.getLines.map(_.trim).mkString("")
  val regx = """(<[\?\/]?[^>]*\??>)([^<]*)"""
  val rawList = regx.r.findAllIn(text).matchData.flatMap(_.subgroups).toList
  rawList.filter(!_.matches(""))
}
urlToList(url).foreach(println _)

//a)

urlToList(url).sliding(4).collect {
  case List("<title>", x, "</title>", _) => x
  case List("<author>", "<name>", x, "</name>") => x
}.foreach(println _)


// 7) see chapter7script.sh:
// call it with ./chapter7script.sh "https://github.com/andres-fr/bachelor-thesis/commits/master.atom"

/*

#!/bin/sh
exec scala "$0" "$@"
!#
object HelloWorld {
  def main(args: Array[String]) {
    val url = args(0)
    val s = io.Source.fromURL(url)
    val text = s.getLines.map(_.trim).mkString("")
    val regx = """(<[\?\/]?[^>]*\??>)([^<]*)"""
    val rawList = regx.r.findAllIn(text).matchData.flatMap(_.subgroups).toList
    rawList.filter(!_.matches("")).sliding(4).collect {
      case List("<title>", x, "</title>", _) => x
      case List("<author>", "<name>", x, "</name>") => x
    }.foreach(println _)
  }
}
HelloWorld.main(args)


 */
