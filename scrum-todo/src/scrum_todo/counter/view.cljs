(ns scrum-todo.counter.view
  (:require [rum.core :as rum]
            [scrum.core :as scrum]))

(rum/defc Counter < rum/reactive [r]
  [:div
   [:button {:on-click #(scrum/dispatch! r :counter :dec)} "-"]
   [:span (rum/react (scrum/subscription r [:counter]))]
   [:button {:on-click #(scrum/dispatch! r :counter :inc)} "+"]])
