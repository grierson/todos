(ns todoapp.main.client
  (:require
    [com.fulcrologic.fulcro.application :as app]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]))

(defonce app (app/fulcro-app))

(defsc Header [this props]
  (dom/header :.header
              (dom/h1 "todo")
              (dom/form :.todo-form
                        (dom/input :.new-todo {:placeholder "What needs to be done?"
                                               :autoFocus   true}))))

(def ui-header (comp/factory Header))

(defsc TodoItem [this {:item/keys [id label completed?]}]
  {:query [:item/id :item/label :item/completed?]
   :ident :item/id}
  (dom/li {:classes [(when completed? (str "completed"))]}
          (dom/div :.view {}
                   (dom/input {:type      "checkbox"
                               :className "toggle"
                               :checked   completed?})
                   (dom/label label)
                   (dom/button :.destroy))
          (dom/form
            (dom/input :.edit))))

(def ui-todoitem (comp/computed-factory TodoItem {:keyfn :item/id}))

(defsc TodoList [this {:list/keys [items]}]
  {:query [:list/id {:list/items (comp/get-query TodoItem)}]
   :ident :list/id}
  (dom/section :.main
               (dom/input {:id        :toggle-all
                           :className "toggle-all"
                           :type      "checkbox"})
               (dom/label {:htmlFor "toggle-all"} "Mark all as complete")
               (dom/ul :.todo-list {} (map ui-todoitem items))))

(def ui-todolist (comp/factory TodoList))

(defsc Footer [this props]
  (dom/footer :.footer
              (dom/span :.todo-count)
              (dom/ul :.filters
                      (dom/li (dom/a {:href "#/"} "All"))
                      (dom/li (dom/a {:href "#/active"} "Active"))
                      (dom/li (dom/a {:href "#/completed"} "Completed")))
              (dom/button :.clear-completed "Clear completed")))

(def ui-footer (comp/factory Footer))

(defsc PageFooter [this props]
  (dom/footer :.info
              (dom/p "Double-click to edit a todo")
              (dom/p "Part of " (dom/a {:href "http://todomvc.com"} "TodoMVC"))))

(def ui-pagefooter (comp/factory PageFooter))

(defsc Root [this {:keys [todos]}]
  {:query [{:todos (comp/get-query TodoList)}]
   :initial-state {:todos [{:item/id 1 :item/label "hello" :item/completed? true}
                           {:item/id 2 :item/label "world" :item/completed? false}]}}
  (dom/div
    (dom/section :.todoapp
                 (ui-header)
                 (ui-todolist todos)
                 (ui-footer))
    (ui-pagefooter)))

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