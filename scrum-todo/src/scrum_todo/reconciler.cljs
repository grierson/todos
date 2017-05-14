(ns scrum-todo.reconciler
  (:require
   [scrum.core :as scrum]
   [scrum-todo.counter.control :as counter]))


(defonce reconciler
  (scrum/reconciler
   {:state
    (atom {})
    :controllers
    {:counter counter/control}}))
