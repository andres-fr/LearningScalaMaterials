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



////////////////////////////////////////////////////////////////////////////////
//////     EXERCISES     ///////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

// 1) 
