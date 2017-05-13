(ns reframe-todo.spec
  (:require
   [cljs.spec :as s]))


(s/def ::id int?)
(s/def ::task string?)
(s/def ::done boolean?)
(s/def ::todo (s/keys :req-un [::id ::task ::done]))
(s/def ::todolist (s/coll-of ::todo))

(def people [{:id 1 :name "Alice"}
             {:id 2 :name "Bob"}
             {:id 3 :name "Charlie"}])

(get-in people [:id 1])
