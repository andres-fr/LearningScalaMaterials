// see http://ensime.github.io/build_tools/sbt/

// 1) build/configure project once (cd, then sbt, then ensimeConfig)
// 2) create empty build.sbt file
// 3) make any .scala file and start the ensime server with M-x ensime
// 4) C-c C-v z to start REPL session
// 5) C-c C-v b to eval buffer, C-c C-v C-r to eval region (notice that it is C-r)

// nice definition of variable : In computer science the term variable typically refers to a unique identifier corresponding to an allocated or reserved memory space, into which values can be stored and from which values can be retrieved



// type conversion:
val l: Long = 20
val i: Int = l.toInt
println(l)

0x0f //hexa int
5l // long, same as 5L
5f // float same as 5F
5d // double same as 5D


// strings:

val greeting = "Hello, " + "World"
val matched = (greeting == "Hello, World") // this returns true!! == tests functional equality

/*

 A multiline String can be created using triple-quotes. Multiline strings are literal, and so do not recognize the use of backslashes as the start of special characters:

scala> val greeting = """She suggested reformatting the file
     | by replacing tabs (\t) with newlines (\n);
     | "Why do that?", he asked. """
greeting: String =
She suggested reformatting the file
by replacing tabs (\t) with newlines (\n);
"Why do that?", he asked.
 */

/*
Here is the example again using string interpolation, preceding the string with an s
scala> println(s"Pi, using 355/113, is about $approx." )

 You will need the optional braces if you have any nonword characters in your reference (such as a calculation), or if your reference can’t be distinguished from the surrounding text:

scala> val item = "apple"
item: String = apple

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
