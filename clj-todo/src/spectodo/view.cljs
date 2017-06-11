(ns spectodo.view
  (:require [rum.core :as rum]
            [scrum.core :as scrum]
            [spectodo.todolist.view :as todolist]
            [spectodo.addtodo.view :as addtodo]
            [spectodo.visibility.view :as visibility]))

(rum/defc View < rum/reactive [r]
  (let [todolist (rum/react (scrum/subscription r [:todolist]))
        visibility (rum/react (scrum/subscription r [:visibility]))
        active (count (filter #(false? (:done %)) todolist))
        completed (count (filter #(true? (:done %)) todolist))
        todos (cond
                (= visibility :complete) (filter #(true? (:done %)) todolist)
                (= visibility :active) (filter #(false? (:done %)) todolist)
                :else todolist)]
    [:div
     [:div
      [:section.todoapp
       (addtodo/View r)
       (todolist/View r todos)
       (visibility/View r active completed visibility)]]]))
