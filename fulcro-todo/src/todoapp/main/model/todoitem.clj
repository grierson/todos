(ns todoapp.main.model.todoitem
  (:require [com.wsscode.pathom.connect :as pc]))

(def items
  {1 {:item/id         1
      :item/label      "Milk"
      :item/completed? false}
   2 {:item/id         2
      :item/label      "Bread"
      :item/completed? true}})

(pc/defresolver list-resolver [env params]
                {::pc/input  #{:list/id}
                 ::pc/output [:list/label {:list/items [:item/id]}]}
                {:list/id    1
                 :list/label "List"
                 :list/items [{:item/id 1} {:item/id 2}]})

(pc/defresolver all-todoitem-resolver [env _]
                {::pc/output [{:all-items [:item/id :item/label :item/completed?]}]}
                {:all-items (mapv (fn [i] {:item/id i}) (keys items))})

(pc/defresolver todoitem-resolver [env {:item/keys [id]}]
                {::pc/input  #{:item/id}
                 ::pc/output [:item/id :item/label :item/completed?]}
                (get items id))

(def resolvers [list-resolver
                todoitem-resolver
                all-todoitem-resolver])

