(ns scrum-todo.view
  (:require [rum.core :as rum]
            [scrum-todo.counter.view :as counter]))


(rum/defc App < rum/reactive [r]
  [:div (counter/Counter r)])
