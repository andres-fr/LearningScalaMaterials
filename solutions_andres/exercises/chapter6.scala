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
//////     COMMON COLLECTIONS         //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// LIST: inmutable, single-linked
val numbers:List[Int] = List(1,2,3,4,5)
numbers.size
numbers.head
numbers.tail
numbers(0)// the first elt


// SOME METHODS: FOREACH (procedure), MAP (yields), REDUCE (to 1 item)
numbers.foreach((x:Int)=>println(x))
numbers.map((x:Int)=>2*x)
numbers.reduce((a:Int, b:Int)=>a+b)
numbers // is still the same
// MORE METHODS: COLLECT (map+partial fn), FLATMAP(maps&flattens)
numbers.collect({case 2=>"Ok"})
numbers.flatMap(c=>List(c, c*10))
// MORE REDUCTIONS:

// max, min, product, sum return the corresponding value
// contains, endsWith(sublst), exists(pred), forall(pred),startsWith(sublst)
// the exists is true if AT LEAST ONE satisfies, forall if ALL satisfy


// SET:
val othernums = Set(10, 20, 20, 20, 20, 20, 30, 5)

// MAP:
val myMap = Map("a"->1, "b"->2, "d" -> -123) // myMap[String, Int] doesnt work...
myMap.foreach((x:(String, Int))=> println(x))
myMap -"a" + ("e"-> false) // this works
// destructuring not possible?? myMap.foreach(((a:String,b:Int))=> println(a))
// here its possible!!
for ((t, w)<- myMap) {println(w)}

// The Nil singleton and LIST EATERS:
List()==Nil // true
// All lists point to NIL at the end, which is a singleton instance of type
// List[Nothing]. Since nothing extends all other types, is always compatible
// and it is possible to do wollowing:
var i = List (1, 2, "tres", 4)
while(i!=Nil){println(i.head); i = i.tail}
// now the list has been "eaten"


// ALL OPERATORS ENDING WITH : ARE RIGHT-ASSOCIATIVE.
// THE CONS OPERATOR (::)
val myLst = 1::2::3::Nil // returns same as List(1,2,3)

// :::           PREPEND LIST List(3,4):::List(1,2)
// ++            APPEND LIST List(1,2)++List(3,4)
// +:            EXTEND LIST ("cons to the end")    =O(n) !!!!
// ==            FUNCTIONAL LIST COMPARATOR List("one", "two")==List("one", "two")
// distinct      REMOVES DUPLICATES, returns a LIST
// drop(n)       REMOVES first n elements          // dropRight=O(n) !!!!
// take(n)       TAKES first n elements            // takeRight =O(n) !!!!
// filter(_>18)  REMOVES all elements with predicate==false
// flatten
// partition(_%2==0)     SPLITS into 2 lists, based on a predicate
// reverse
// slice(0, 2)    SUBLIST, a inclusive, b exclusive
// sortBy(_.size) SORTS after fn
// sorted         SORTS by default value of type (f.e. strings lexic.)
// zip(List(...)) MERGES elementweise into list of tuples



// GENERAL DEFINITION OF REDUCE: (example: implement myExists)
def myReduce[A,B](l:List[A], init:B)(fn:(B,A)=>B):B = {
  var result = init
  for (x<-l){result=fn(result, x)}
  result
}
def myReduceBoolean[A](l:List[A], fn:(Boolean, A)=>Boolean) = myReduce(l, false)(fn)
def myExists[A](l:List[A], comparator:A) = {
  myReduceBoolean(l, compare(_:Boolean,_:A)(comparator))
}
myExists(List(1,2,3,4,5), 8) // List(1,2,3,4,5).myExists(9) NOT POSSIBLE (not a method)
  myExists(List("one","two","three"), "twoo")


/// MORE GENERAL: FOLD IS LIKE REDUCE, WITH AN EXPLICIT INIT VALUE:
List(1,2,3,4,5).fold(100000)(_+_) // variants: foldLeft, foldRight
                                  // also reduceLeft, reduceRight
// SCAN IS A FOLD THAT YIELDS THE RESULTS:
List(1,2,3,4,5).scan(100000)(_+_) // left&right variants, check how they work!!
////////////////////////////////////////////////////////////////////////////////
///////////////////// VERY IMPORTANT:
// fold, scan and reduce's return types are limited to the type in the list.
// this does NOT APPLY to the left and right variants. The "vanilla" functions
// have also NO FIXED ORDER, so they may "profit" from parallelization and such.



// CONVERSION INVOLVING COLLECTIONS:
List(1,2,3,4).mkString(", ") // returns "1, 2, 3, 4"
List(1,2,3,4).toString // returns "List(1, 2, 3, 4)"
List(1,2,3,4).toBuffer // converts an immutable to a mutable
Map("a"->1, "b"->2).toList // a list of 2 tuples
List(1->2,3->4).toMap // a list of 2 tuples into a map. works also with sets
List(1,2,2,3,4,1).toSet
// CONVERSION WITH JAVA: scala colls are incompatible by default, but they can
// be manually adjusted. To do so, import:
import collection.JavaConverters._
// with the 2 methods: List(1,2,3).asJava or new java.util.ArrayList(5).asScala


// MATCH AND COLLECTIONS: equality, methods, variables
val statuses = List(500, 404)
val msg = statuses match {
    case List(404, 500) => "well that would be almost right"
    case x if x.contains(500) => "thats an error"
    case List(e, x) => s"$e followed by $x""
    case _ => "not sure if good or bad"
  }

// destructuring:
val head = List("r", "g", "b") match {
    case "w"::xs => "first was red"
    case x::List("g", "b") => "tail was gb"
  }

// tuples, while not officially collections, also support pattern matching and
// value binding. Because a single tuple can support values of different types,
// their pattern-matching capability is at times even more useful than that of
// collections:
val tupmatch = ("a", 3, false) match {
    case (_, 4.567, _) => "case 1"
    case (_, _, false) => "dat is a ly"
    case _ => "whatever"
  }


////////////////////////////////////////////////////////////////////////////////
//////     EXERCISES     ////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// 1)
for (x<- 1 to 20 if (x%2!=0)) yield x // solution with "guarded list comp"
(1 to 20).filter(_%2!=0)              // solution with filter
(0 to 9).map(_*2+1)   // a possible solution with map



// 2) worst integer factorization algo ever
@annotation.tailrec
def myFactors(n:Int, result:List[Int]=List()):List[Int] = {
  val divisors = (2 to n-1).filter(n%_==0) // this is not ok, see comment below
  // primes are also returned, becauseI wanted that myFactors(n).product==n
  if (divisors.size==0) n::result else {
    myFactors(n/divisors(0), divisors(0)::result) // at least tail rec
  }
}
(1 to 100).map(myFactors(_).product) // works as expected

// flatMap is ok... a loop is better to force early termination, for example
// at filtering, when only the first valid result is needed.
def factorsList (l:List[Int]):List[Int]=l.flatMap(myFactors(_).filter(!l.contains(_)))
factorsList(List(9, 11,13,15))

// 7)
val url = "http://api.openweathermap.org/data/2.5/forecast?mode=xml&lat=55&lon=0"
val l: List[String] = io.Source.fromURL(url).getLines.toList // error API needs account
