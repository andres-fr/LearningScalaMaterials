// see http://ensime.github.io/build_tools/sbt/
// 1) build/configure project once (cd, then sbt, then ensimeConfig)
// 2) create empty build.sbt file
// 3) make any .scala file and start the ensime server with M-x ensime
// 4) C-c C-v z to start REPL session
// 5) C-c C-v b to eval buffer, C-c C-v C-r to eval region (notice that it is C-r)


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
//////     FUNCTIONS      //////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
// FUNCTIONS: named, reusable expressions. May be parameterized or return a
// value, but neither requrired. In functional programming a pure function:
// 1) has 1+ input parameters
// 2) performs calculations based on the input pars
// 3) returns a value
// 4) Always returns the same value for the same input
// 5) Does not use or affect any data outside the function
// 6) Is not affected by any data outside the function
// basically like the math functions, they are uncorruptible and noncorrupting
// expressions of pure logic



def multiplier(x:Int, y:Int):Int = {x*y}
println(multiplier(2,3))

// early termination with return:
def safeTrim(s:String) :String = {
  if (s==null) return null
  s.trim()
}
val str :String = safeTrim(null)


// PROCEDURES
// A procedure is a function that doesn’t have a return value. Any
// function that ends with a statement, such as a println() call, is also
// a procedure. If you have a simple function without an explicit return
// type that ends with a statement, the Scala compiler will infer the
// return type of the function to be Unit , the lack of a value. For
// procedures greater than a single line, an explicit unit type of Unit
// will clearly indicate to readers that there is no return value
def proc1 (d:Double) : Unit = {println(s"the num is $d")}
proc1(543.21)

// An alternate but now unofficially deprecated syntax:
def proc2 (d:Double) {println(s"the num is $d")}
proc2(1234.5678)


// NO-PARAMETER FUNCTIONS: ALL ALLOWED EXCEPT ONE THING:
def test1 : String = "foo"
def test2() : String = "bar"
test1
test2
//test1() // THIS isnt allowed: defined without () cant be called with ()
test2()
// This rule prevents confusion from invoking a function without
// parentheses versus invoking the return value of that function as a
// function. A Scala convention for input-less functions is that they
//should be defined with empty parentheses if they have side effects


// INVOKE FN WITH EXPRESSION BLOCK: do calculations at fn call
def formatEuro(amt: Double) = f"€$amt%.2f"
formatEuro {val rate = 1.32; 0.235 + 0.7123 + rate * 5.32}


// RECURSION AND TAIL-RECURSION: the Scala compiler can optimize some
// recursive functions with tail-recursion so that recursive calls do not
// use additional stack space. With tail- recursion–optimized functions,
// recursive invocation doesn’t create new stack space but instead uses
// the current function’s stack space. Only functions whose last
// statement is the recursive invocation can be optimized for
// tail-recursion by the Scala compiler. If the result of invoking itself
// is used for anything but the direct return value, a function can’t be
// optimized.

// to mark a def as tail-rec,  add the following annotation and it wont
// compile if tailrec not possible. This would throw an error:
@annotation.tailrec
def power (x:Int, a:Int):Long = {if (a>=1) x*power(x, a-1) else 1}

// neither this is OK (because the multiplication is the last statement)
@annotation.tailrec
def power (x:Int, a:Int):Long = {if (a<1) 1 else x*power(x, a-1)}

// this should do:
@annotation.tailrec
def power (x:Int, a:Int, result:Long=1):Long = {
  if (a<1) result else x*power(x, a-1, x*result)}
power(2, 16)

// PARAMETER KEYS, DEFAULTS...
def greet(prefix: String, name: String="asdf") = s"$prefix $name"
val greeting = greet(prefix="Mr")

// VARARG PARAMETERS: ADD * AFTER PARAM TYPE
// The vararg parameter cannot be followed by a
// nonvararg parameter because there would be no way to distinguish
// them. Inside the function, the vararg parameter, implemented as a
// collection (which we’ll study in Chapter 6), can be used as an
// iterator in for loops.
def sum(items:Int*)= {var x=0;for(i<-items){x+=i}; x}
println(sum(1,2,3,4,5))

// PARAMETER GROUPS
def max(x:Int)(y:Int):Int = {if (x>=y) x else y}
max(10)(20)
// why not just keep all of the parameters together in one group? The
// real benefits come when you use them with function literals


// TYPE PARAMETERS: following doesnt work:
def identity(x:Any){x}
//val s:String = identity("asdf") // any cant be casted to string!!

// SOLUTION: parametrize type


def identity[A](a:A) = {a}
val s:String = identity("asdf")
val i:Int = identity(2345)
// also explicit
val d:Double = identity[Double](1234.5678f) // this casting works


// METHOD INFIX VS. DOT NOTATION:
val d = 1234.5678
println(d.+(1000))
println(d + 1000)
println(d.compare(1000))
println(d compare 1000)
println("starting" substring (0, 4)) // inclusive->exclusive
// evaluation order: 1+2+3 => (1+2)+3
// EVERY SINGLE PARAMETER METHOD CAN BE USED AS OPERATOR





////////////////////////////////////////////////////////////////////////////////
//////     EXERCISES      //////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// 3)
@annotation.tailrec
def ex3(current:Int=5):Unit = {
  if (current<=50){print(s"$current, "); ex3(current+5)}}
ex3()


//4)
def ex4(num:Double):String = { // Double because every AnyVal can cast to it
  val ms = num % 1000
  val s = (num.toInt/1000)%60
  val min = (num.toInt/60000)%60
  val h = (num.toInt/3600000)%24
  val d = num.toInt/86400000
  s"${d}d, ${h}h, ${min}min, ${s}s, "+ f"${ms}%.2fms"
}
val x = ex4(1234555567.890235345)

//5)
def ex5_1(base:Double, exponent:Double):Double = {Math.pow(base, exponent)}
ex5_1(Math.PI, Math.E)

//6) solution avoiding libraries
def ex6(point1:(Double, Double), point2:(Double, Double)):Double={
  val dx = point1._1-point2._1
  val dy = point1._2-point2._2
  Math.sqrt(dx*dx+dy*dy)
}
ex6((0, 0), (1, 1))

//7)
def ex7(point:(AnyVal, AnyVal)):(AnyVal, AnyVal)={
  point._2 match{
    case c : Int => point._2->point._1
    case _ => point
  }
}
ex7(1.11, 2)
ex7(2, 3.45f)

//8)

def ex8[A,B,C](tuple:(A, B, C)):(A, String, B, String, C, String)={
  (tuple._1, tuple._1.toString, tuple._2, tuple._2.toString, tuple._3, tuple._3.toString)
}
ex8(12.34f, "asdf", true)
