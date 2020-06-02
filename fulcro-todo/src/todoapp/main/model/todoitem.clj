(ns todoapp.main.model.todoitem
  (:require [com.wsscode.pathom.connect :as pc]
            [taoensso.timbre :as log]))

(def items
  {1 {:item/id         1
      :item/label      "Milk"
      :item/completed? false}
   2 {:item/id         2
      :item/label      "Bread"
      :item/completed? true}})

(pc/defresolver all-todoitem-resolver [env _]
                {::pc/output [{:all-items [:item/id :item/label :item/completed?]}]}
                {:all-items (mapv (fn [i] {:item/id i}) (keys items))})

(pc/defresolver todoitem-resolver [env {:item/keys [id]}]
                {::pc/input  #{:item/id}
                 ::pc/output [:item/id :item/label :item/completed?]}
                (get items id))

(def resolvers [todoitem-resolver
                all-todoitem-resolver])

