(ns spectodo.todolist.view
  (:require [rum.core :as rum]
            [scrum.core :as scrum]))


(rum/defc Todo-Item < rum/reactive [r todo]
  (let [toggle #(scrum/dispatch! r :todolist :toggle (:id todo))
        delete #(scrum/dispatch! r :todolist :delete (:id todo))
        editing #(scrum/dispatch! r :todolist :editing (:id todo))
        task (:task todo)
        done (:done todo)]
    [:li {:class [(when (true? (:done todo)) "completed")
                  (when (true? (:editing todo)) "editing")]}
     [:div.view
      [:input.toggle
        {:type "checkbox"
         :checked (true? done)
         :on-click toggle}]
      [:label
       {:on-double-click editing}
       task]
      [:button.destroy {:on-click delete}]]
    [:input.edit
      {:name "title"
       :value task
       :id (str "todo-" (:id todo))
       :on-blur editing
       :on-key-up (fn [e] (if (= 13 (.-keyCode e)) editing))}]]))

(rum/defc View < rum/reactive [r todos]
  (let [visibility #(rum/react (scrum/subscription r [:visibility]))
        toggle-all #(scrum/dispatch! r :todolist :toggle-all)]
  [:section.main
   [:input.toggle-all
    {:type "checkbox"
     :on-click toggle-all}]]
   [:ul.todo-list
    (map #(rum/with-key (Todo-Item r %) (:id %)) todos)]))
