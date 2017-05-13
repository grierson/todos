(ns reframe-todo.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))


(re-frame/reg-sub
 :todolist
 (fn [db]
   (:todolist db)))
