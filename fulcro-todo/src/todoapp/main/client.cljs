(ns todoapp.main.client
  (:require
    [com.fulcrologic.fulcro.application :as app]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]
    [com.fulcrologic.fulcro.dom.events :as events]
    [com.fulcrologic.fulcro.algorithms.tempid :as tmp]
    [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
    [com.fulcrologic.fulcro.algorithms.merge :as merge]
    [com.fulcrologic.fulcro.algorithms.normalized-state :as norm]
    [com.fulcrologic.fulcro.mutations :as mut :refer [defmutation]]))

(defonce app (app/fulcro-app))

(defn add-item-to-list*
  "Add an item's ident onto the end of the given list."
  [state item-id list-id]
  (update-in state [:list/id list-id :list/items] (fnil conj []) [:item/id item-id]))

(defn clear-new-item-text*
  "Clear input for new todo"
  [state list-id]
  (assoc-in state [:list/id list-id :ui/new-item-text] ""))

(defn create-item*
  "Create a new todo item and insert it into the todo item table."
  [state id text]
  (assoc-in state [:item/id id] {:item/id         id
                                 :item/label      text
                                 :item/completed? false}))

(defmutation new-item
  "Add new item to list"
  [{:keys [id text]}]
  (action [{:keys [state]}]
          (let [tempid (tmp/tempid)]
            (swap! state #(-> %
                              (create-item* tempid text)
                              (add-item-to-list* tempid id)
                              (clear-new-item-text* id))))))

(defmutation toggle-item
  "Toggle item to completed status"
  [{:keys [id]}]
  (action [{:keys [state]}]
          (swap! state update-in [:item/id id :item/completed?] not)))

(defmutation set-filter
  "Toggle item to completed status"
  [{:keys [list-id filter]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:list/id list-id :list/filter] filter)))


(defmutation delete-item
  "Remove item from list"
  [{:keys [list-id item-id]}]
  (action [{:keys [state]}]
          (swap! state (fn [s] (-> s
                                   (norm/remove-ident [:item/id item-id] [:list/id list-id :list/items])
                                   (update :item/id dissoc item-id))))))

(defmutation clear-completed
  "Clear all completed"
  [{:keys [id]}]
  (action [{:keys [state]}]
          (let [is-complete? (fn [item-ident] (get-in @state (conj item-ident :item/complete)))]
            (swap! state update-in [:list/id id :list/items]
                   (fn [todos] (vec (remove (fn [ident] (is-complete? ident)) todos)))))))

(defn trim-text [text]
  "Returns text without surrounding whitespace if not empty, otherwise nil"
  (let [trimmed-text (clojure.string/trim text)]
    (when-not (empty? trimmed-text)
      trimmed-text)))

(defn header [component]
  (let [{:list/keys [id]
         :ui/keys   [new-item-text]} (comp/props component)]
    (dom/header :.header
                (dom/h1 "todo")
                (dom/input {:value       (or new-item-text "")
                            :placeholder "What needs to be done?"
                            :className   "new-todo"
                            :onChange    (fn [evt] (mut/set-string! component :ui/new-item-text :event evt))
                            :onKeyDown   (fn [event]
                                           (when (events/enter? event)
                                             (when-let [trimmed-text (trim-text (events/target-value event))]
                                               (comp/transact! component [(new-item {:id   id
                                                                                     :text trimmed-text})]))))
                            :autoFocus   true}))))

(defn footer [component]
  (let [{:list/keys [id]} (comp/props component)]
    (dom/footer :.footer
                (dom/span :.todo-count)
                (dom/ul :.filters
                        (dom/li (dom/a {:href    "#/"
                                        :onClick #(comp/transact! component [(set-filter {:list-id id
                                                                                          :filter  :list.filter/all})])} "All"))
                        (dom/li (dom/a {:href    "#/active"
                                        :onClick #(comp/transact! component [(set-filter {:list-id id
                                                                                          :filter  :list.filter/active})])} "Active"))
                        (dom/li (dom/a {:href    "#/completed"
                                        :onClick #(comp/transact! component [(set-filter {:list-id id
                                                                                          :filter  :list.filter/completed})])} "Completed")))
                (dom/button {:className "clear-completed"
                             :onClick   #(comp/transact! component [(clear-completed {:id id})])} "Clear completed"))))

(defsc TodoItem [this {:item/keys [id label completed?]} {:keys [delete-item]}]
  {:query [:item/id :item/label :item/completed?]
   :ident :item/id}
  (dom/li {:classes [(when completed? (str "completed"))]}
          (dom/div :.view {}
                   (dom/input {:type      "checkbox"
                               :className "toggle"
                               :onChange  (fn [_] (comp/transact! this [(toggle-item {:id id})]))
                               :checked   completed?})
                   (dom/label label)
                   (dom/button :.destroy {:onClick #(delete-item id)}))))

(def ui-todoitem (comp/computed-factory TodoItem {:keyfn :item/id}))

(defsc TodoList [this {:list/keys [id items filter]}]
  {:query         [:list/id :list/label :list/new-item :list/filter {:list/items (comp/get-query TodoItem)}]
   :ident         :list/id
   :initial-state (fn [_] {:list/id 1 :list/label "shopping" :list/items [] :ui/new-item-text "" :list/filter :list.filter/all})}
  (let [delete-item (fn [item-id] (comp/transact! this [(delete-item {:list-id id
                                                                      :item-id item-id})]))
        completed-items (filterv :item/completed? items)
        filtered-items (case filter
                         :list.filter/active (filterv (comp not :item/completed?) items)
                         :list.filter/completed completed-items
                         :list.filter/all items
                         items)]
    (dom/div {}
             (header this)
             (dom/section :.main
                          (dom/input {:id        :toggle-all
                                      :className "toggle-all"
                                      :type      "checkbox"})
                          (dom/label {:htmlFor "toggle-all"} "Mark all as complete")
                          (dom/ul :.todo-list {} (map #(ui-todoitem % {:delete-item delete-item}) filtered-items)))
             (footer this))))

(def ui-todolist (comp/factory TodoList))

(defn page-footer []
  (dom/footer :.info
              (dom/p "Double-click to edit a todo")
              (dom/p "Part of " (dom/a {:href "http://todomvc.com"} "TodoMVC"))))

(defsc Root [this {:keys [main]}]
  {:query         [{:main (comp/get-query TodoList)}]
   :initial-state (fn [_] {:main (comp/get-initial-state TodoList {})})}
  (dom/div {}
           (dom/section :.todoapp (ui-todolist main))
           (page-footer)))

(comment
  (app/current-state app)
  (comp/get-query Root)
  (merge/merge-component! app
                          TodoList {:list/id 1 :list/label "shopping" :list/items [{:item/id         1
                                                                                    :item/label      "buy milk"
                                                                                    :item/completed? false}]}
                          :replace [:main]))

(defn ^:export init
  "Shadow-cljs sets this up to be our entry-point function. See shadow-cljs.edn `:init-fn` in the modules of the main build."
  []
  (app/mount! app Root "app")
  (js/console.log "Loaded"))

(defn ^:export refresh
  "During development, shadow-cljs will call this on every hot reload of source. See shadow-cljs.edn"
  []
  ;; re-mounting will cause forced UI refresh, update internals, etc.
  (app/mount! app Root "app")
  (js/console.log "Hot reload"))