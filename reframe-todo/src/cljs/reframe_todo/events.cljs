(ns reframe-todo.events
    (:require [re-frame.core :as re-frame]
              [reframe-todo.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(defn toggle [id todo]
  (if (= id (todo :id))
    (assoc todo :done (not (todo :done)))
    todo))

(re-frame/reg-event-db
 :toggle
 (fn  [db [_ id]]
   (assoc db :todolist (map (partial toggle id) (db :todolist)))))

