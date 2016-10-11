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
//////     OBJECTS, CASE-CLASSES, TRAITS  //////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// OBJECT: a type of class that cant have more than one instance (singleton).
// instead of instantiating, simply access it by its name. It gets automatically
// instantiated the first time it's accessed in a running JVM.
// it helps the design to separate STATICS, wich go to objects, from
// INSTANCE-BASED which go in classes. As "static", objects have no init params,
// BUT THEY CAN INHERIT from other classes making their fields and methods
// globally avaliable. The opposite is not true: it doesn't make much sense
// to extend an object, since only one of its "type" will exist: add the extra
// functionality to the object itself!

object Hello {println("In Hello"); def hi="hi"}
println(Hello.hi) // first time prints "In hello" and then hi, second time only hi


// // great paragraph about object methods: The standard class method is
// one that reads from or writes to the fields of its instance, providing
// complementary access points and business logic for the data. Likewise,
// the kinds of methods best suited for objects are pure functions and
// the functions that work with external I/O (Input/Output). Pure
// functions are ones that return results calculated exclusively from
// their inputs, have no side effects, and are referentially transparent
// (indistinguishable if replaced by the result of the function). I/O
// functions are those that work with external data, such as with files,
// databases, and external services. Neither of these function types are
// well suited to being class methods because they have little to do with
// a class’s fields.


object HtmlUtils {
  def removeMarkup(input: String) = {
    input.replaceAll("""</?\w[^>]*>""","").replaceAll("<.*>","")
  }
}
val htmlString = """<?xml version="1.0" encoding="UTF-8"?><feed xmlns="http://www.w3.org/2005/Atom" xmlns:media="http://search.yahoo.com/mrss/" xml:lang="en-US"><id>tag:github.com,2008:/andres-fr/bachelor-thesis/commits/master</id><link type="text/html" rel="alternate" href="https://github.com/andres-fr/bachelor-thesis/commits/master"/><link type="application/atom+xml" rel="self" href="https://github.com/andres-fr/bachelor-thesis/commits/master.atom"/><title>Recent Commits to bachelor-thesis:master</title><updated>2016-10-08T06:59:21+02:00</updated><entry><id>tag:github.com,2008:Grit::Commit/6bdf7e32439c88783f6b6a61daa17199218221a4</id><link type="text/html" rel="alternate" href="https://github.com/andres-fr/bachelor-thesis/commit/6bdf7e32439c88783f6b6a61daa17199218221a4"/><title>minor changes</title><updated>2016-10-08T06:59:21+02:00</updated><media:thumbnail height="30" width="30" url="https://avatars0.githubusercontent.com/u/4235319?v=3&amp;s=30"/><author><name>andres-fr</name><uri>https://github.com/andres-fr</uri></author><content type="html">&lt;pre style=&#39;white-space:pre-wrap;width:81ex&#39;&gt;minor changes&lt;/pre&gt;</content></entry><entry><id>tag:github.com,2008:Grit::Commit/084f8462ebd193dadc3757ba28d4422ae27568d8</id><link type="text/html" rel="alternate" href="https://github.com/andres-fr/bachelor-thesis/commit/084f8462ebd193dadc3757ba28d4422ae27568d8"/><title>working on kernel trick</title><updated>2016-07-10T04:01:04+02:00</updated><media:thumbnail height="30" width="30" url="https://2.gravatar.com/avatar/7e1bb8c46f22b73b0a64900a1696bebc?d=https%3A%2F%2Fassets-cdn.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png&amp;r=x&amp;s=30"/><author><name></name><email>andres-fr@users.noreply.github.com</email></author><content type="html">&lt;pre style=&#39;white-space:pre-wrap;width:81ex&#39;&gt;working on kernel trick&lt;/pre&gt;</content></entry></feed>"""
HtmlUtils.removeMarkup(htmlString)


// APPLY IN OBJECTS: FACTORY PATTERN
// this is how List(1,2,3) works in Scala: the apply method of the List obj
// returns a collection. This is a popular way to instantiate in Scala. The
// class and its COMPANION OBJECT share the name and access to all fields,
// which is good for encapsulation (see DB example):

// both of this MUST BE IN THE SAME FILE
class Multiplier(val x: Int) { def product(y: Int) = x * y }
object Multiplier { def apply(x: Int) = new Multiplier(x) } // apply is factory method
Multiplier(3) // THIS RETURNS AN INSTANCE WITHOUT CALLING NEW EXPLICITLY.


// This is good because the obj privates are in a singleton, this means,
// GLOBAL thorough the whole app, but ONLY accessible by the corresponding class
object DBConnection {
  private val db_url = "jdbc://localhost"
  private val db_user = "franken"
  private val db_pass = "berry"
  def apply() = new DBConnection
}
class DBConnection {
  private val props = Map(
      "url" -> DBConnection.db_url,
      "user" -> DBConnection.db_user,
      "pass" -> DBConnection.db_pass
    )
  println(s"Created new connection for " + props("url"))
}


val x = new DBConnection()


// COMMAND LINE APPLICATIONS WITH OBJECTS: see file app8.scala


// CASE CLASSES:
// A case class is an instantiable class that includes several
// automatically generated meth‐ ods. It also includes an automatically
// generated companion object with its own auto‐ matically generated
// methods. All of these methods in the class and in the companion object
// are based on the class’s parameter list, with the parameters being
// used to formulate methods like an equals implemention that iteratively
// compares every field and a to String method that cleanly prints out
// the class name and all of its field values

// Case classes work great for data transfer objects, the kind of classes
// that are mainly used for storing data, given the data-based methods
// that are generated. They don’t work well in hierarchical class
// structures, however, because inherited fields aren’t used to build its
// utility methods. And extending a case class with a regular class could
// lead to invalid results from the generated methods, which can’t take
// into account fields added by subclasses. However, if you want a class
// with a definitive set of fields, and these automatically generated
// methods are useful, then a case class may be right for you.

// syntax: the "val" keyword isnt necessary, it's default UNLIKE "var"
case class Character(name:String, isFemale:Boolean)
val c1 = new Character("sarah", true)// print is nice already
val c2 = c1.copy(name="adrian") // adrian, true
c2==c1 // false (checks all fields)

c1 match{
  case Character(x, true) => s"$x is a female"
  case Character(x, false) => s"$x is a male"
}

// IMPORTANT: when extending a case class with another case class,
// but the super's fields weren't explicitly added at the construction
// parameters, the automatically generated methods won't be able to use them.

///////////////////////////
// TRAITS Provide convenience for reusing functions in the same way
//that case classes provide convenience for managing your data.
// TRAITS are not instantiable: all class types (object, class, trait...)
// can extend only one class, but multiple traits! This works because the
// scala compiler converts a multiple inheritance [A, B, C ...] into a
// train of single inheritances a extends b extends c... a process called
// LINEARIZATION, and happens right to left. Example:

// trait Base { override def toString = "Base" }
// class A extends Base { override def toString = "A->" + super.toString }
// trait B extends Base { override def toString = "B->" + super.toString }
// trait C extends Base { override def toString = "C->" + super.toString }
// class D extends A with B with C { override def toString = "D->" + super.toString }
// new D() // D = D->C->B->A->Base

// A CLASS IMPORTING TWO TRAITS WITH THE SAME FIELD NAME WILL FAIL TO COMPILE,
// UNLESS MARKED WITH THE OVERRIDE KW (see page 179 for example).

trait HtmlUtils {
  def removeMarkup(input: String) = {
    input.replaceAll("""</?\w[^>]*>""","").replaceAll("<.*>","")
  }
}
class Page(val s:String) extends HtmlUtils {
  def asPlainText = removeMarkup(s)
}// the object is able to implement all the trait's functionality
new Page("<html><body><h1>Introduction</h1></body></html>").asPlainText 
// A STATIC CALL TO removeMarkup(...) FROM Page or HtmlUtils WONT WORK

// MULTIPLE INHERITING: first extend class (in case there is one), then
// traits using the "with" token:

trait SafeStringUtils {
  // Returns a trimmed version of the string wrapped in an Option,
  // or None if the trimmed string is empty.
  def trimToNone(s: String): Option[String] = {
    Option(s) map(_.trim) filterNot(_.isEmpty)
  }
}
class Page(val s: String) extends SafeStringUtils with HtmlUtils {
  def asPlainText: String = {
    trimToNone(s) map removeMarkup getOrElse "n/a"
  }
}
// now our Page instance is also safer:
new Page(null).asPlainText // n/a instead of exception



// SELF TYPES: addthe requirement that the trait can only ever
// be mixed into a subtype of the specified type, the A class.
class A { def hi = "hi" }
trait B { self: A => // self ist just a convention, could be any free var
  override def toString = "B: " + hi
}
class C extends A with B // this wouldn't work: class C extends B


// but that toy example didn't show the real utility:

class TestSuite(suiteName: String) {// the name is an identifier
  def start() {} //to be overriden
}
trait RandomSeeded { self: TestSuite =>
  def randomStart() {
    util.Random.setSeed(System.currentTimeMillis)
    self.start() // adds random functionality WITHOUT HARDCODING START 
  }
}
class IdSpec extends TestSuite("ID Tests") with RandomSeeded {
  def testId() { println(util.Random.nextInt != 1) }
  override def start() { testId() } // THIS IS THE START FUNCTIONALITY: print an ID
  println("Starting...")
  randomStart() // AND NOW IS CALLED THE TRAIT:
}
new IdSpec

// in other words: Our trait needs to invoke TestSuite.start() but
// cannot extend TestSuite because it would require hardcoding the input
// parameter. By using a self type, the trait can expect to be a
// subtype of TestSuite without explicitly being declared as one With
// self types, a trait can take advantage of extending a class without
// specifying its input parameters. It is also a safe way to add
// restrictions and/or requirements to your traits, ensuring they are
// only used in a specific context.


// INSTANTIATION WITH TRAITS: A class defined without a dependency on,
// or  even knowledge of a trait can use its functionality. The only
// catch is that traits added at a class’s instantiation extend the
// class, not the other way around. The left-to-right order of trait
// linearization includes the instantiated class in its ordering, so all
// of the traits extend the class and not the other way around.
// it is not actually extending the traits but instead being extended by them.

// ADDING THIS FUNCTIONALITY TO EXISTING CLASSES IS KNOWN AS "DEPENDENCY INJECTION"
class A
trait B {self: A=>}
val a = new A with B // a.toString is now  A with B = $anon$1@5b12012e
                     // anon is for "anonymous", with an unique code

// EXAMPLE OF DEPENDENCY INJECTION of popular class "User":
class User(val name: String) {
  def suffix = ""
  override def toString = s"$name$suffix"
}
// val u = new User("andy") // can't change suffix.
trait Attorney { self: User => override def suffix = ", esq." }
trait Wizard { self: User => override def suffix = ", Wizard" }
trait Reverser { override def toString = super.toString.reverse }
val h = new User("Harry P.") with Wizard // User with Wizard = Harry P., Wizard
val g = new User("Ginny W") with Attorney // User with Attorney = Ginny W, esq.
val l = new User("Luna L") with Wizard with Reverser // User with Wizard with Reverser = draziW ,L anuL


// IMPORTING INSTANCE MEMBERS: The import keyword can also be used to
// import members of classes and objects into the current namespace. This
// makes it possible to access them directly without specifying their
// enclosing instance (for classes) or name (for objects).
// Importing fields and methods does not override privacy controls, so
// only those that would be normally accessible can be imported.
// do not overdo it! can easily cause conflicts with typical var names like "x"

case class Receipt(id: Int, amount: Double, who: String, title: String)
val latteReceipt = Receipt(123, 4.12, "fred", "Medium Latte")
import latteReceipt._ // if this line is ommited, REPL throws in the following line:
println(s"Sold a $title for $amount to $who") //  error: not found: value title

// another useful example with random, providing a single global instance
// handy when you don’t need to set a new seed for random number generation:
import util.Random._
alphanumeric.take(20).toList.mkString
shuffle(1 to 20)

////////////////////////////////////////////////////////////////////////////////
//////     EXERCISES     ///////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// 1) 
