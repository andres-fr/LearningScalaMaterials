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
//////     FIRST-CLASS FUNCTIONS      //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
// They can be used in every segment of the language as just another data type.
// may, as with other data types, be created in literal form without
// ever having been assigned an identifier and stored in a container such
// as a value, variable, or data structure, and be used as a parameter to
// another function or used as the return value from another function.
// higher-order functions: Functions that accept other functions
// as parameters and/or use functions as return values (map, reduce, fold ,filter...)


def times2 (x:Double):Double = {x*2}
// this returns nyFn: Double => Double = <function1>
val myFn : (Double=>Double) = times2
// it cant be declared without explicit type. Use wildcard else:
val myFnWildcard = times2 _ // this returns the same type
myFn(123.456)
myFnWildcard(321.321)
// VOID VALUE FIELDS ARE REPRESENTED AS Unit TYPE OR THE EQUIVALENT ()

// HIGHER-ORDER FUNCTIONS
def safeStringOp(s:String, op:(String=>String)):String={
  if (s!=null) op(s) else s
}
safeStringOp("Ready", _.reverse)


def higherFn(s:String, op:(String=>String)):String={println("I'm high"); op(s)}
def reverserFn(s:String):String = s.reverse.toUpperCase
val reverserFn2:(String=>String) = String.reverse.toUpperCase
// run them together
higherFn(" Are we not drawn onward to new era?", reverserFn)
higherFn("asdf", {(s:String)=>s.reverse})
higherFn("asdf", _.reverse)


def goodWrapperFn(s:String):String = s.reverse
goodWrapperFn _


val otherGoodFn:(String=>String) = goodWrapperFn

def errorWrapperFn:(String=>String) = String.reverse


val s:String = "hello"
s.reverse


def errorWrapperFn:(String=>String) = _.reverse


// LAMBDAS  ANONYMOUS FUNCTIONS    FUNCTION LITERALS
val myFn = (x:Int)=>x*2
val myVal = myFn(22)
val greeter = {(name:String)=>s"hello, $name"} // basically a parametrized expression




////////////////////////////////////////////////////////////////////////////////
//////     PLACEHOLDER SYNTAX         //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

def highFn1(x:Int, y:Int, z:Int, fn:(Int,Int,Int)=>Double)={fn(x,y,z)}
highFn1(10, 20, 30, (a,b,c)=>{a*b/c})
//this compact version works because: 1)TYPES ARE ELSEWHERE DEFINED 2) PARAMETERS USED IN ORDER AND EACH ONCE
highFn1(10, 20, 30, _+_/_)
// GENERIC VERSION:
def highFn2[A, B](x:A, y:A,z:A, fn:(A, A, A)=>B):B = {fn(x,y,z)}
highFn2[Int, Int](100, 10, 3, _+_/_) // returns 103
highFn2[Double, Double](100, 10, 3, _+_/_) // returns 103.3333333333



////////////////////////////////////////////////////////////////////////////////
//////     PARTIAL FN APPLICATION      /////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
def multipleOf(a:Int, b:Int):Boolean = {b%a==0}
def multipleOf3 = multipleOf(3, _:Int)
multipleOf3(12)

////////////////////////////////////////////////////////////////////////////////
//////     CURRYING   //////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// A cleaner way to partially apply functions is to use functions with
// multiple parameter lists. Instead of breaking up a parameter list into
// applied and unapplied parameters, apply the parameters for one list
// while leaving another list unapplied. This is a technique known as
// currying the function. In terms of a function type, a function with
// multiple parameter lists is considered to be a chain of multiple
// functions. Each separate parameter list is considered to be a separate
// function call
def curryMultipleOf(a:Int)(b:Int):Boolean = {b%a==0}
def curryMultipleOf5 = curryMultipleOf(5)_
curryMultipleOf5(12)
// This functionality could be easily implemented: the benefit that
// partially applied functions and curried functions provide is an
// expressive syntax for doing so.


////////////////////////////////////////////////////////////////////////////////
//////     BY-NAME PARAMETERS   ////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// defining a parameter type as (x:=>Int) instead of (x:Int), means that x is
// supposed to return an int. It can be an int itself!
// IMPORTANT: PARAMETERS OF TYPE "T" WILL BE BOUND ONCE AS val WHEN THE FUNCTION
// IS EXECUTED. PARAMETERS OF TYPE "=>T" WILL BE CALLED EACH TIME THEY APPEAR!!
// IN LISP IS LIKE LET VS APPLY
def testbyname(x: =>Int):Int = {
  println(s"testbyname called with x=$x") // this $x forces an eventual new call to the fn
  x*2 // this too
}
def fn(y:Int):Int={println(s"fn was called with $y"); y}
//now call testbyname with an int, and with fn
testbyname(5)
testbyname(fn(5)) // fn is called twice: one per line in testbyname

// HIGHER ORDER+PARAMETER GROUPING:
def highFn1(check:Int)(fn: Int=>Int):Unit = {
  if (check<10) println(fn(check)) else println(s"only Int<10 accepted!") 
}

val multiplierByTwoIfLowerThan10 = highFn1(_:Int)(_*2)
multiplierByTwoIfLowerThan10(9)
val multiplySevenIfLowerThan10 = highFn1(_:Int){s => // this is the same, a fn
    s*7 // {s=>s*7} is equivalent to (_*7)
  }
multiplySevenIfLowerThan10(5)


// Functions that can wrap indiscriminate blocks of code with utilities
// in this way are a major benefit of using the “expression block” style
// of higher-order function invoca‐ tions. Some of the other uses for
// this invocation style include:
// • Managing database transactions, where
// the higher-order function opens the ses‐ sion, invokes the function
// parameter, and then closes the transaction with a commit or rollback.
// • Handling expected errors with retries, by calling the function
// parameter a set num‐ ber of times until it stops causing errors.
// • Conditionally invoking the function parameter based on local, global,
// or external values (e.g., a database setting or environment variable)


////////////////////////////////////////////////////////////////////////////////
//////     PARTIAL FUNCTIONS    ////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
// Scala’s partial functions are function literals that apply a series of
// case patterns to their input, requiring that the input match at least
// one of the given patterns. Invoking one of these partial functions
// with data that does not meet at least one case pattern results in a
// Scala error. NOT THE SAME AS PARTIALLY APPLIED FNs

// useful when working with collections and pattern matching. For
// example, you can “collect” every item in a collection that is accepted
// by a given partial function


////////////////////////////////////////////////////////////////////////////////
//////     EXERCISES     ////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

