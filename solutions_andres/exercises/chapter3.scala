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
