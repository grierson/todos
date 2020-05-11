(ns todoapp.main.client
  (:require
    [com.fulcrologic.fulcro.application :as app]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]
    [com.fulcrologic.fulcro.algorithms.tempid :as tmp]
    [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
    [com.fulcrologic.fulcro.algorithms.merge :as merge]
    [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]))

(defonce app (app/fulcro-app))

(defn is-enter? [evt] (= 13 (.-keyCode evt)))
(defn is-escape? [evt] (= 27 (.-keyCode evt)))

(defn add-item-to-list*
  "Add an item's ident onto the end of the given list."
  [state list-id item-id]
  (update-in state [:list/id list-id :list/items] (fnil conj []) [:item/id item-id]))

(defn create-item*
  "Create a new todo item and insert it into the todo item table."
  [state id text]
  (assoc-in state [:item/id id] {:item/id id :item/label text :item/completed? false}))

(defmutation todo-new-item [{:keys [list-id id text]}]
  (action [{:keys [state]}]
          (swap! state #(-> %
                            (create-item* id text)
                            (add-item-to-list* list-id id)))))

(defn trim-text [text]
  "Returns text without surrounding whitespace if not empty, otherwise nil"
  (let [trimmed-text (clojure.string/trim text)]
    (when-not (empty? trimmed-text)
      trimmed-text)))

(defn header [component]
  (let [{:list/keys [id]} (comp/props component)]
    (dom/header :.header
                (dom/h1 "todo")
                (dom/input {:placeholder "What needs to be done?"
                            :className   "new-todo"
                            :onKeyDown   (fn [evt]
                                           (when (is-enter? evt)
                                             (when-let [trimmed-text (trim-text (.. evt -target -value))]
                                               (comp/transact! component `[(todo-new-item ~{:list-id id
                                                                                            :id      (tmp/tempid)
                                                                                            :text    trimmed-text})]))))
                            :autoFocus   true}))))

(defn footer []
  (dom/footer :.footer
              (dom/span :.todo-count)
              (dom/ul :.filters
                      (dom/li (dom/a {:href "#/"} "All"))
                      (dom/li (dom/a {:href "#/active"} "Active"))
                      (dom/li (dom/a {:href "#/completed"} "Completed")))
              (dom/button :.clear-completed "Clear completed")))

(defsc TodoItem [this {:item/keys [label completed?]}]
  {:query [:item/id :item/label :item/completed?]
   :ident :item/id}
  (dom/li {:classes [(when completed? (str "completed"))]}
          (dom/div :.view {}
                   (dom/input {:type      "checkbox"
                               :className "toggle"
                               :checked   completed?})
                   (dom/label label)
                   (dom/button :.destroy))))

(def ui-todoitem (comp/computed-factory TodoItem {:keyfn :item/id}))

(defsc TodoList [this {:list/keys [id label items]}]
  {:query         [:list/id :list/label {:list/items (comp/get-query TodoItem)}]
   :ident         :list/id
   :initial-state (fn [_] {:list/id 1 :list/label "shopping" :list/items []})}
  (dom/div {}
           (header this)
           (dom/section :.main
                        (dom/input {:id        :toggle-all
                                    :className "toggle-all"
                                    :type      "checkbox"})
                        (dom/label {:htmlFor "toggle-all"} "Mark all as complete")
                        (dom/ul :.todo-list {} (map ui-todoitem items)))
           (footer)))

(def ui-todolist (comp/factory TodoList))

(defn page-footer []
  (dom/footer :.info
              (dom/p "Double-click to edit a todo")
              (dom/p "Part of " (dom/a {:href "http://todomvc.com"} "TodoMVC"))))

(defsc Root [this {:keys [main]}]
  {:query         [{:main (comp/get-query TodoList)}]
   :initial-state (fn [_] {:main (comp/get-initial-state TodoList {})})}
  (dom/div {}
           (dom/section :.todoapp (ui-todolist list))
           (page-footer)))

(comment
  (app/current-state app)
  (comp/get-query Root)
  (merge/merge-component! app
                          TodoList {:list/id 1 :list/label "shopping" :list/items [{:item/id         1
                                                                                    :item/label      "buy milk"
                                                                                    :item/completed? false}]}
                          :replace [:root/list]))

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