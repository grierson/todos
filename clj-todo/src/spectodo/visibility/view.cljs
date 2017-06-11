(ns spectodo.visibility.view
  (:require [rum.core :as rum]
            [scrum.core :as scrum]))

(rum/defc TodosCount [active]
  (let [plural (if (= 1 active) "item" "items")]
    [:span.todo-count
     [:strong (str active " " plural)]]))

(rum/defc VisibilityItem < rum/reactive [r current vis-type]
  (let [vis-text (str vis-type)]
    [:li {:on-click #(scrum/dispatch! r :visibility :change vis-type)}
      [:a {:class (when (= current vis-type) "selected")}
        [:text vis-text]]]))

(rum/defc Visibility < rum/reactive [r current]
  [:ul.filters (map #(rum/with-key (VisibilityItem r current %) %) [:all :complete :active])])

(rum/defc ClearCompleted < rum/reactive [r completed]
  [:button.clear-completed
   {:on-click #(scrum/dispatch! r :todolist :clear-completed)
    :hidden (zero? completed)}
   [:text (str "Clear completed (" completed ")")]])

(rum/defc View < rum/reactive [r active completed visibility]
  [:footer.footer {:hidden (zero? active)}
   (TodosCount active)
   (Visibility r visibility)
   (ClearCompleted r completed)])
