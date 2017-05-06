(ns clojure-todo.db
  (:require [cljs.reader]
            [cljs.spec :as s]
            [re-frame.core :as re-frame]))

(s/def ::uid int?)
(s/def ::name string?)
(s/def ::completed boolean?)
(s/def ::todo (s/keys :req-un [::uid ::name ::completed]))

(s/def ::todolist (s/and
                   (s/map-of ::todo)
                   #(instance? PersistentTreeMap %)))

(s/def ::db (s/keys :req-un [::todolist]))

(def default-db
  {:todolist "Alice"})
