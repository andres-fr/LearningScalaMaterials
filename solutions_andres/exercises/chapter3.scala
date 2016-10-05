// see http://ensime.github.io/build_tools/sbt/

// 1) build/configure project once (cd, then sbt, then ensimeConfig)
// 2) create empty build.sbt file
// 3) make any .scala file and start the ensime server with M-x ensime
// 4) C-c C-v z to start REPL session
// 5) C-c C-v b to eval buffer, C-c C-v C-r to eval region (notice that it is C-r)

// nice definition of variable : In computer science the term variable
// typically refers to a unique identifier corresponding to an allocated
// or reserved memory space, into which values can be stored and from
// which values can be retrieved

// EXPRESSIONS:

/*
expression := Unit of code that returns a value after it has been
executed. Multiple expressions can be combined using curly braces ( {
and } ) to create a single expression block. An expression has its own
scope, and may contain values and variables local to the expression
block. THE LAST EXPRESSION IN THE BLOCK IS THE RETURN VALUE FOR THE
ENTIRE BLOCK. blocks are also nestable, with each level of expression
block having its own scoped values and variables
 */
val amount = { val x = 5 * 20; x + 10 } // returns amount: Int = 110


// STATEMENTS:

/*
 A statement is just an expression that doesn’t return a
value. Statements have a return type of Unit , the type that indicates
the lack of a value. Some common statements used in Scala programming
include calls to println() and value and variable definitions
 */

// IF ELSE:
if ( 47 % 3 == 0 ) println("Not a multiple of 3") else println ("indeed multiple my friend")


// MATCHING EXPRESSIONS (switch)

/*
 The traditional “switch” statement is limited to matching by value,
 but Scala’s match expressions are an amazingly flexible device that
 also enables matching such diverse items as types, regular
 expressions, numeric ranges, and data structure contents.

 <expression> match {
 case <pattern match> => <expression>
 [case...]
 }
 */

val myNum = (10>20) match {
    case true  => {println("match true"); 43.21d}
    case false => {println("match false"); 1234}
  } // returns myNum: Double = 1234.0 because it inferes the common type from all possible outcomes

val kind = "FRI" match {
    case "MON" | "TUE" | "WED" | "THU" | "FRI" => "weekday"
    case "SAT" | "SUN" => "weekend"
    //case _ => {println(s"couldn't parse $<YOUR VARIABLE HERE>")}
    //case other => println(s"couldn't parse $other")
  }


// MATCHING WITH PATTERN GUARDS (cond with "let"+"if" instead of predicates)

val asdf_test = "ASDF"
val asdf = asdf_test match {
    case w if w != null => println(s"received $w") // s is local
    case s => println("error! received null response")
  }

// MATCHING WITH (POLYMORPHIC) TYPES:
val x : Int = 1234
val y : Any = x
y match {
  case c: String => s"'x'"
  case c: Double => f"$x%.5f"
  case c: Int => s"${x}i"
} // here type of y was Any, but the value conserved the "Int" differentiation (polym.)


// LOOPS (see example with and wout yield)
1 to 7 // this is a range (returns scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, 5, 6, 7))
val lst = for (x <- 1 to 7) {println(s"Day $x:"); s"$x"} // loop is done, void returned
for (x <- 1 to 7) yield { s"Day $x:" }// with yield the whole collection is returned (list comp.)

// ITERATOR GUARDS (FILTER) for+if
for (i <- 1 to 20 if i % 3 == 0) yield i

// MULTI-LINE, GUARDED FOR LOOP:
for {x <- 1 to 50
     if x%5==0
     if x%3==0
} yield x

// NESTED ITERATION
for {x<-1 to 3
     y<- 1 to 4
} yield (x, y) //  scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((1,1), (1,2), (1,3), (1,4), (2,1)...

// LOOP WITH BOUND VALUES:
for (i<-0 to 5; pow=1<<i) yield pow

// WHILE, DOWHILE: THEY ARE NOT EXPRESSIONS (CANNOT BE USED TO YIELD VALUES)
var x = 10
while (x>0){println(x); x-=1}
do println("whatever") while (false)


////////////////////////////////////////////////////////////////////////////////
//////     EXERCISES      //////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

//1)
val name = readLine
name match{
  case c if (c!=null & c!="") => c
  case _ => "n/a"
}

//3)
val color = readLine
val result : Int = color match{
    case c if (c=="cyan") => 0xAAAAAA
    case c if (c=="magenta") => 0xBBBBBB
    case c if (c=="yellow") => 0xCCCCCC
    case _ => {println(s"couldnt handle value $color"); -1}
  }

//4)
for {i <- 1 to 100;
     comma = if(i%100==0) "" else ","
     spazer = if (i%5==0) "\n" else " "
}{print(s"$i$comma$spazer")}


//5)
for {i <- 1 to 100;
     t = if(i%3==0) "type" else "";
     s = if (i%5==0) "safe" else ""
}{print(s"($i)$t$s, ")}


//6)
for{i<-1 to 100; t=if(i%3==0)"type" else ""; s=if(i%5==0)"safe" else ""}{print(s"($i)$t$s, ")}
