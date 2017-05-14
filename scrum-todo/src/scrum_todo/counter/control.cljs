(ns scrum-todo.counter.control)


(def initial-state 0) ;; initial state

(defmulti control (fn [event] event))

(defmethod control :init []
  {:local-storage
   {:method :get
    :key :counter
    :on-read :init-ready}}) ;; read from local storage

(defmethod control :init-ready [_ [counter]]
  (if-not (nil? counter)
    {:state counter} ;; init with saved state
    {:state initial-state})) ;; or with predefined initial state

(defmethod control :inc [_ _ counter]
  (let [next-counter (inc counter)]
    {:state next-counter ;; update state
     :local-storage
     {:method :set
      :data next-counter
      :key :counter}})) ;; persist to local storage

(defmethod control :dec [_ _ counter]
  (let [next-counter (dec counter)]
    {:state next-counter ;; update state
     :local-storage
     {:method :set
      :data next-counter
      :key :counter}})) ;; persist to local storage
