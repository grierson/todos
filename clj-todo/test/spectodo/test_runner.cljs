(ns spectodo.test-runner
  (:require  [doo.runner :refer-macros [doo-tests]]
             [spectodo.counter-test]))

(enable-console-print!)

(doo-tests 'spectodo.counter-test)

