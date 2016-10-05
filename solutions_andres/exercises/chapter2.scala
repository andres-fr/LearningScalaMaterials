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



// type conversion:
val l: Long = 20
val i: Int = l.toInt
println(l)

/*
0x0f //hexa int
5l // long, same as 5L
5f // float same as 5F
5d // double same as 5D
 */

// strings:

val greeting = "Hello, " + "World" val matched = (greeting == "Hello, World")
// this returns true!! == tests functional equality

/*

 A multiline String can be created using triple-quotes. Multiline
 strings are literal, and so do not recognize the use of backslashes
 as the start of special characters:

scala> val greeting = """She suggested reformatting the file | by
replacing tabs (\t) with newlines (\n); | "Why do that?", he
asked. """ greeting: String = She suggested reformatting the file by
replacing tabs (\t) with newlines (\n); "Why do that?", he asked.
 */

/*
Here is the example again using string interpolation, preceding the
string with an s scala> println(s"Pi, using 355/113, is about
$approx." )

 You will need the optional braces if you have any nonword characters
 in your reference (such as a calculation), or if your reference can’t
 be distinguished from the surrounding text:

scala> val item = "apple" item: String = apple

scala> s"How do you like them ${item}s?"
res0: String = How do you like them apples?

scala> s"Fish n chips n vinegar, ${"pepper "*3}salt"
res1: String = Fish n chips n vinegar, pepper pepper pepper salt
 */


/*
 also printf notation, preceding the string with an f

 scala> val item = "apple"
item: String = apple

scala> f"I wrote a new $item%.3s today"
res2: String = I wrote a new app today

scala> f"Enjoying this $item ${355/113.0}%.5f times today"
res3: String = Enjoying this apple 3.14159 times today

 */


// REGEX: JAVA SPECS.
// A regular expression is a string of characters and punctuation that represents a search pattern.

// regex defining, replacing, creating from string, capturing
val input = "Enjoying this apple 3.14159 times today"
val pattern = """.* apple ([\d.]+)times .*""".r
val pattern(amountText) = input //String = 3.14159
val amount = amountText.toDouble //Double = 3.14159


// type hierarchy

/*
 Any<---[anyval, anyref]
 anyval<--- value types (objs or primitive vals) numbers, chars and boolean
 anyref<--- strings collections and classes
 The Any , AnyVal , and AnyRef types are the root of Scala’s type
 At thebottom of the Scala type hierarchy are the Nothing and Null
types. Nothing is a subtype of every other type and exists to provide
a compatible return type for operations that significantly affect a
program’s flow. Nothing is only used as a type, because it cannot
be instantiated.  The other bottom type is Null , a subtype of all
AnyRef types that exists to provide a type for the keyword null
 */


// casting of char-int
val v1: Int = 'A' // returns int 65
val v2: Char = 116 // returns char 't'


// booleans: can only be true or false! no conversion allowed

/*
 The Boolean comparison operators && and || are lazy in that they will
not bother evaluating the second argument if the first argument is
sufficient. The operators & and | will always check both arguments
before returning a result.
 */


// Unit type

/*

 The Unit type is similarly used in Scala as the return type for functions or expres‐
sions that don’t return anything. For example, the common println function could be
said to return a Unit type because it returns nothing.
The Unit literal is an empty pair of parentheses, () , which if you consider it is a fine
representation of not having a value.
 */


// Type operations:

/*
 5.asInstanceOf[Long] // AVOID ASINSTANCEOF!!! TO PREVENT RUNTIME ERRORS
 (7.0 / 5).getClass
 (5.0).isInstanceOf[Float]
 "A".hashCode
 20.toByte; 47.toFloat
 (3.0 / 4.0).toString
 */


// Tuples:
// Unlike lists and arrays, there is no way to iterate through elements in a
// tuple. Its purpose is only as a container for more than one value.
// You can access an individual element from a tuple by its 1-based index
val info = (5, "Korben", true) // returns info: (Int, String, Boolean) = (5,Korben,true)
info._2 //returned res5: String = Korben

// An alternate form of creating a 2-sized tuple is with the relation operator
// ( -> ). This is a popular shortcut for representing key-value pairs in tuples
val red = "red" -> "0xff0000"
val reversed = red._2 -> red._1 // (String, String) = (0xff0000,red)
