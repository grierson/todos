(ns todoapp.main.model.todoitem
  (:require [com.wsscode.pathom.connect :as pc]
            [taoensso.timbre :as log]))

(def items
  (atom {1 {:item/id         1
            :item/label      "Milk"
            :item/completed? false}
         2 {:item/id         2
            :item/label      "Bread"
            :item/completed? false}
         3 {:item/id         3
            :item/label      "Noodles"
            :item/completed? false}}))

(pc/defresolver list-resolver [env params]
                {::pc/input  #{:list/id}
                 ::pc/output [:list/label {:list/items [:item/id]}]}
                {:list/id     1
                 :list/filter :list.filter/all
                 :list/label  "List"
                 :list/items  (mapv (fn [i] {:item/id i}) (keys @items))})

(pc/defresolver all-todoitem-resolver [env _]
                {::pc/output [{:all-items [:item/id :item/label :item/completed?]}]}
                {:all-items (mapv (fn [i] {:item/id i}) (keys @items))})

(pc/defresolver todoitem-resolver [env {:item/keys [id]}]
                {::pc/input  #{:item/id}
                 ::pc/output [:item/id :item/label :item/completed?]}
                (get @items id))

(pc/defmutation delete-item [env {:keys [item-id]}]
                {::pc/sym    `todoapp.main.client/delete-item
                 ::pc/params [:item/id]
                 ::pc/output []}
                (swap! items dissoc item-id)
                {})

(pc/defmutation toggle-item [env {:item/keys [id]}]
                {::pc/sym    `todoapp.main.client/toggle-item
                 ::pc/params [:item/id]
                 ::pc/output []}
                (log/info "Toggle item" id)
                (swap! items update-in [id :item/completed?] not)
                {})

(pc/defmutation clear-completed [env _]
                {::pc/sym    `todoapp.main.client/clear-completed
                 ::pc/output []}
                (swap! items (fn [db] (into {} (remove #(-> % val :item/completed?)) db)))
                {})

(def resolvers [delete-item
                toggle-item
                clear-completed
                list-resolver
                todoitem-resolver
                all-todoitem-resolver])

