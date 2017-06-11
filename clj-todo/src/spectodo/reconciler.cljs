(ns spectodo.reconciler
  (:require [scrum.core :as scrum]
            [spectodo.addtodo.control :as addtodo]
            [spectodo.todolist.control :as todolist]
            [spectodo.visibility.control :as visibility]))

(defonce reconciler
  (scrum/reconciler
   {:state
    (atom {})
    :controllers {:addtodo addtodo/control
                  :todolist todolist/control
                  :visibility visibility/control}}))
