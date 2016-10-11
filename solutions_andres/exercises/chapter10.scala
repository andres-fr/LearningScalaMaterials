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
//////     ADVANCED TYPING          ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// Difference between class and type? because a type is more than just a class. A
// class that takes a type parameter is a type, but every time it is in‐
// stantiated with a type parameter, that too is a type. You can consider
// List , List[Int] , and List[String] to all correspond to the same
// class even though they have different types.


// starting with tuples:
Tuple2[Int, String](1, "asdf") // same as simply (1, "asdf")
// this 2 are also the same:
new Function[Int, Int]{def apply(x:Int)=x*2} // same as {x:Int=>x*2} or val f1:Int=>Int = _+2


/// IMPLICIT CLASSES: provide a type-safe way to “monkey-patch” new
// methods and fields onto existing classes. Through automatic
// conversion from the original class to the new class, methods and
// fields in the implicit class can be invoked directly on the original
// class without any changes to the class’s structure

object ImplicitClasses {
  implicit class Hello(s: String) {
    def hello = s"Hello, $s"
  }
  def test = {println( "World".hello )}
}
ImplicitClasses.test // this prints "Hello, World"


////////////////////////////////////////////////////////////////////////////////
//////     EXERCISES     ///////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// 1)
