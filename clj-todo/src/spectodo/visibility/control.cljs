(ns spectodo.visibility.control)


(def initial-state :all)

(defmulti control (fn [event args state] event))

(defmethod control :default [_ _ state]
  (if (nil? state)
    {:state initial-state}
    {:state state}))

(defmethod control :change [_ [visibility] state]
  {:state visibility})
