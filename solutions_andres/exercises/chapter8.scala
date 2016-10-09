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
//////     OBJECT-ORIENTED SCALA    ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

class User(val name:String) { // if declared with "val", becomes a class field
  def greet = s"Hello im $name"// constructors also accept named pars: new User(name="...")
  override def toString = s"User($name)"
}

val u = new User("asdf") // REPL prints the overriden toString
u.greet // hello im asdf
u.name
val isAnyRef = u.isInstanceOf[AnyRef] // true
u == new User // false
u == u // true

// custom classes can be well used in collections!

// INHERITANCE: extends, override, this, super || abstract etc see further
class A {
  def hi = "Hello from A"
  override def toString = getClass.getName
}
class B extends A
class C extends B {override def hi = "hi C -> " + super.hi}
val test1:A = new B // this is possible because b inherits from A and supports all his ops
//val test2:B = new A // THIS IS NOT POSSIBLE
List(new A, new B, new C).map(_.hi) // List(Hello from A, Hello from A, hi C -> Hello from A)

// passing constructor parameters:
class Car(val make: String, var reserved: Boolean) {
  def reserve(r: Boolean): Unit = { reserved = r }
}
class Lotus(val color:String, reserved:Boolean=false) extends Car("Lotus", reserved)
val l = new Lotus("black")
l.make
l.reserved
l.color


// CREATE OWN COLLECTION WITH OWN TYPE-PARAMETER POSSIBILITY, like in:
def example[A](x:A) = List[A](x, x, x) // try with example(2); example("two")
// Traversable is the parent of iterable. lets take it to define a monadic collection:
class Singular[A](element:A) extends Traversable[A]{
  def foreach[B](f:A=>B) = f(element)
}
val s = new Singular("asdf")
s.tail // the traversable methods are there



// ABSTRACT CLASSES: not much new to say

// ANONYMOUS CLASSES: useful when a class extending an abstract class is needed
// only for a single instance. For example: runnables, listeners...
abstract class Listener {def trigger} // unimplemented method
val b = List.newBuilder[Listener] // whatever container that holds listeners and works on them
b += new Listener{def trigger = println(s"Trigger at ${new java.util.Date}")} // do this many times
b.result.foreach(_.trigger)

// OVERLOADED METHODS: optional/defaul/curried parameters are recommended, save code

// APPLY METHODS: can be invoked without name, using parentheses only
class Multiplier(a:Int){def apply(b:Int)={b*a}}
val multBy3 = new Multiplier(3)
multBy3(4) // same as multBy3.apply(4), returns 12

// lazy values: created when first accessed, not at instantiation time.
class LazySlowal{
  lazy val three = {for (i<- 1 to 1e9.toInt){}; 3}
}
val v = new LazySlowal // this has no delay
v.three // this takes a while, but only the first time


// PACKAGING: like in java, reversed domain+folders-> pathwith
// points. Use "package <IDENTIFIER>" at the beginning of the
// document. A packaged class can be accessed by its full
// period-delimited package path and class name. This may be
// IMPORTED: "import package.Class". Unlike in java, this is an
// statement, you may place it "wherever" you want:
println("Your new UUID is " + {import java.util.UUID; UUID.randomUUID})
// THIS MAY HELP TO: 1) understand better the goal of the import,
// 2) avoid name collisions by restricting the scope of the import
// ALSO BECAUSE IF IMPORTING CLASE WITH SAME NAME,OLD ONE ISNT AVALIABLE ANYMORE

// CUMULATIVE IMPORTS: import java.util shortcuts the name, but doesnt
// import every java util. for that, use the wildcard: import java.util._


// IMPORT GROUP AND ALIASING:
import collection.mutable.{Queue,ArrayBuffer, Map=>MutMap} // so we avoid collisions with immutable map


// PACKAGING SYNTAX: when compiling scala, is it possible to have many packages
// in the same file (it doesnt work using `scala` command or in the REPL)
// PRIVACY: protected, private work as usual. ACCESS MODIFIERS: in square brackets
package com {
  package webpage {
    private[webpage] class Config (val baseUrl: String = "http://localhost") // "package protected" class
    class Test2 {private[this] var passwd = "password"} // this is a private field to Test2
  }
}

// FINAL ACCESSOR: prevents field to be overriden, or classes to be extended
// ensuring that only the given implementation will be used

// SEALED: class accessor less restrictive than final, allows inheritance only
// among all classes IN THE SAME FILE AND MARKED WITH SEALED. For example,
// Option is sealed with None and Some, and cant be extended further

////////////////////////////////////////////////////////////////////////////////
//////     EXERCISES     ///////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// 1) a,b,c,d
class Console(var make:String, var model:String,
              var formatsSupported:List[String], var maxVideoRes:(Int, Int),
              var debutDate:java.util.Date=null, var wifiType:String=""){
  override def toString = {
    val fmts = formatsSupported.mkString(", ")
    s"Console(make=$make, model=$model, debutDate=$debutDate, "++
    s"wifiType=$wifiType, formatsSupported=[$fmts], maxVideores=$maxVideoRes)"
  }
}

class Game(var name:String, var maker:String, var consolesSupported:List[(String, String)]){
  def isSupported(c:Console):Boolean = {consolesSupported.contains((c.make, c.model))}
}

val c1 = new Console("playstation", "fünf", List("HDMI", "VIH"), (1080, 720))
val c2 = new Console("gameboy", "brick", List(), (8, 8), wifiType="ultraFastEthernet")
val g = new Game("extreme train simulator", "STFGames", List(("playstation", "three"), ("gameboy", "brick")))
g.isSupported(c2) // true
g.isSupported(c1) // false


// 2)

class Container[A](val content:A, val nxt:Container[A]=null){
  override def toString = content.toString
}
class MyList[A](val self:A*){
  var pointer = new Container(self.last)
  for (x<-self.reverse.tail){pointer = new Container(x, pointer)}
  pointer
  private def genericIterator[B](fn:A=>B)(c:Container[A]=pointer){
    if(c.content!=null){fn(c.content); util.Try(genericIterator(fn)(c.nxt))}
  }
  def foreach[B](fn:A=>B) = genericIterator(fn)(pointer)
  def printContents = foreach(println)
  def apply(n:Int):A={
    var cell = pointer
    for (i<- 1 to n) cell = cell.nxt
    cell.content
  }
}
val x = new MyList(1,2,3,4)
x.foreach(_*2)
x.printContents
x(3)


// CREATE OWN COLLECTION WITH OWN TYPE-PARAMETER POSSIBILITY, like in:
def example[A](x:A) = List[A](x, x, x) // try with example(2); example("two")
// Traversable is the parent of iterable. lets take it to define a monadic collection:
class Singular[A](element:A) extends Traversable[A]{
  def foreach[B](f:A=>B) = f(element)
}
val s = new Singular("asdf")
s.tail // the traversable methods are there


Seq(1)



// 4)
val synth = javax.sound.midi.MidiSystem.getSynthesizer

synth.open()
val channel = synth.getChannels.head
channel.noteOn(50, 80) // notenumber, velocity
Thread.sleep(250)
channel.noteOff(50) // notenumber
Thread.sleep(1000)
synth.close()



def playSeq(seq:Seq[Int], synth:javax.sound.midi.Synthesizer, dur:Int=250):Unit={
  val velocity = 127 // max midi volume
  lazy val ch = synth.getChannels.head
  /*
  val threads = for (i<- seq) yield new Thread(){
      override def run(){channel.noteOn(i, 127); Thread.sleep(duration); channel.noteOff(i)}
    }
   */
  synth.open()
 // threads.foreach(t => {t.start(); t.join()})
  seq.foreach(t => {ch.noteOn(t, velocity); Thread.sleep(dur); ch.noteOff(t)})
  synth.close()
}
var synth:javax.sound.midi.Synthesizer = null
synth = Option(synth).getOrElse(javax.sound.midi.MidiSystem.getSynthesizer)
playSeq(Seq(60, 62, 64, 65, 67, 69, 71, 72), synth)
playSeq(50 to 100 by 5, synth, 100)
playSeq(10 to 120 by 3, synth, 30)

// it isn't possible to systematically limit the amount of instances of a class
// only by ussing the access controls (private, sealed, final, etc), that's
// what scala's object (singleton) is for.
