(ns spectodo.todolist.control)

(def initial-state [])

(defmulti control (fn [event args state] event))

(defmethod control :default [_ _ state]
  (if (nil? state)
    {:state initial-state}
    {:state state}))

(defmethod control :add [_ [task] state]
  (let [todos (map #(dissoc % :id) state)]
    {:state
     (map-indexed
      (fn [i todo] (conj {:id i} todo))
      (conj todos {:task task :done false :editing false}))}))

(defmethod control :toggle [_ [id] state]
  {:state (map #(if (= id (% :id)) (update % :done not) %) state)})

(defmethod control :toggle-all [_ _ state]
  {:state (map #(update % :done not) state)})

(defmethod control :delete [_ [id] state]
  {:state (filter #(not= id (% :id)) state)})

(defmethod control :clear-completed [_ _ state]
  {:state (filter #(false? (:done %)) state)})

(defmethod control :editing [_ [id] state]
  {:state (map #(if (= id (% :id)) (update % :editing not) %) state)})
