(ns todoapp.main.model.todoitem
  (:require [com.wsscode.pathom.connect :as pc]))

(def items
  {1 {:item/id         1
      :item/label      "Milk"
      :item/completed? false}})

(pc/defresolver todoitem-resolver [env {::keys [id]}]
                {::pc/input  #{:item/id}
                 ::pc/output [:item/id :item/label :item/completed?]}
                (get items id))

(def resolvers [todoitem-resolver])