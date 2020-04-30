(ns todoapp.workspaces.todoitem-cards
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.dom :as dom]
            [nubank.workspaces.core :as ws]
            [nubank.workspaces.card-types.fulcro3 :as ct.fulcro]))

(defsc FulcroDemo
          [this {:keys [counter]}]
          {:initial-state (fn [_] {:counter 0})
           :ident         (fn [] [::id "singleton"])
           :query         [:counter]}
          (dom/div
            (str "Fulcro counter demo [" counter "]")))

(ws/defcard fulcro-demo-card
            (ct.fulcro/fulcro-card
              {::ct.fulcro/root FulcroDemo}))