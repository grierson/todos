(ns reframe-todo.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [reframe-todo.core-test]))

(doo-tests 'reframe-todo.core-test)
