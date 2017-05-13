(ns reframe-todo.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn todo-item [todo]
  ^{:key (:id todo)}
  [:li
   {:class (when (:done todo) "completed ")}
   [:div.view
    [:input.toggle
     {:type "checkbox"
      :checked (:done todo)
      :on-click #(re-frame/dispatch [:toggle (:id todo)])}]
    [:label (:task todo)]
    [:button.destory]]
   [:input.edit
    {:value (:task todo)
     :name "task"
     :id (:id todo)}]])

(defn todo-list []
  (let [todolist @(re-frame/subscribe [:todolist])]
    [:section.main
     [:ul.todo-list
      (map todo-item todolist)]]))

(defn main-panel []
  [:div.todomvc-wrapper
   [:section.todoapp
    [todo-list]]])
