// see http://ensime.github.io/build_tools/sbt/

// 1) build/configure project once (cd, then sbt, then ensimeConfig)
// 2) create empty build.sbt file
// 3) make any .scala file and start the ensime server with M-x ensime
// 4) C-c C-v z to start REPL session
// 5) C-c C-v b to eval buffer, C-c C-v C-r to eval region (notice that it is C-r)

/*

 C-c C-r a       ensime-refactor-add-type-annotation
C-c C-r i       ensime-refactor-diff-inline-local
C-c C-r l       ensime-refactor-diff-extract-local
C-c C-r m       ensime-refactor-diff-extract-method
C-c C-r o       ensime-refactor-diff-organize-imports
C-c C-r r       ensime-refactor-diff-rename
C-c C-r t       ensime-import-type-at-point

C-c C-b C       ensime-sbt-do-compile-only
C-c C-b E       ensime-sbt-do-ensime-config
C-c C-b S       ensime-stacktrace-switch
C-c C-b c       ensime-sbt-do-compile
C-c C-b n       ensime-sbt-do-clean
C-c C-b o       ensime-sbt-do-test-only-dwim
C-c C-b p       ensime-sbt-do-package
C-c C-b q       ensime-sbt-do-test-quick-dwim
C-c C-b r       ensime-sbt-do-run
C-c C-b s       ensime-sbt-switch
C-c C-b t       ensime-sbt-do-test-dwim

C-c C-d a       ensime-db-clear-all-breaks
C-c C-d b       ensime-db-set-break
C-c C-d c       ensime-db-continue
C-c C-d i       ensime-db-inspect-value-at-point
C-c C-d n       ensime-db-next
C-c C-d o       ensime-db-step-out
C-c C-d q       ensime-db-quit
C-c C-d r       ensime-db-run
C-c C-d s       ensime-db-step
C-c C-d t       ensime-db-backtrace
C-c C-d u       ensime-db-clear-break

C-c C-t i       ensime-goto-impl
C-c C-t t       ensime-goto-test

C-c C-c a       ensime-typecheck-all
C-c C-c c       ensime-typecheck-current-buffer
C-c C-c e       ensime-show-all-errors-and-warnings
C-c C-c r       ensime-reload-open-files

C-c C-v C-r     ensime-inf-eval-region
C-c C-v .       ensime-expand-selection-command
C-c C-v 5       Prefix Command
C-c C-v D       ensime-project-docs
C-c C-v T       ensime-type-at-point-full-name
C-c C-v b       ensime-inf-eval-buffer
C-c C-v d       ensime-show-doc-for-symbol-at-point
C-c C-v e       ensime-print-errors-at-point
C-c C-v f       ensime-format-source
C-c C-v i       ensime-inspect-type-at-point
C-c C-v l       ensime-inf-load-file
C-c C-v o       ensime-inspect-project-package
C-c C-v p       ensime-inspect-package-at-point
C-c C-v r       ensime-show-uses-of-symbol-at-point
C-c C-v s       ensime-sbt-switch
C-c C-v t       ensime-type-at-point
C-c C-v u       ensime-undo-peek
C-c C-v v       ensime-search
C-c C-v z       ensime-inf-switch
 */

object HelloWorld {
  val x = 234
  def main(args: Array[String]): Unit = {
    println("Hello, world! "+x)
  }
}






println("dfasdfs")