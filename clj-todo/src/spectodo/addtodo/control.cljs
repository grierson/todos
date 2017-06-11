(ns spectodo.addtodo.control)

(def initial-state "")

(defmulti control (fn [event _ _] event))

(defmethod control :default [event value state]
  (if (nil? state)
    {:state initial-state}
    {:state state}))

(defmethod control :update [event [value] state]
  {:state value})

(defmethod control :clear [_ _ _]
  {:state ""})
