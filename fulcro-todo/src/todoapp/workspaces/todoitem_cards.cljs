(ns todoapp.workspaces.todoitem-cards
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [todoapp.main.client :as client]
            [nubank.workspaces.core :as ws :refer [defcard]]
            [nubank.workspaces.card-types.fulcro3 :as ct.fulcro]))

(defcard todolist-card
         (ct.fulcro/fulcro-card
           {::ct.fulcro/root          client/TodoList
            ::ct.fulcro/initial-state {:list/id         1
                                       :list/items      [{:item/id 1
                                                          :item/label "a"
                                                          :item/completed? false}
                                                         {:item/id 2
                                                          :item/label "b"
                                                          :item/completed? true}]}}))
