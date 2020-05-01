(ns todoapp.workspaces.todoitem-cards
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.dom :as dom]
            [todoapp.main.client :as client]
            [nubank.workspaces.core :as ws]
            [nubank.workspaces.card-types.fulcro3 :as ct.fulcro]))

(ws/defcard todoitem-toggle-done-card
            (ct.fulcro/fulcro-card
              {::ct.fulcro/root client/TodoList
               ::ct.fulcro/initial-state
                                {:list/id    1
                                 :list/items [(comp/get-initial-state client/TodoItem {:label      "a"
                                                                                       :completed? true})
                                              (comp/get-initial-state client/TodoItem {:label      "b"
                                                                                       :completed? false})]}}))

