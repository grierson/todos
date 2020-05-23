(ns user
  (:require [org.httpkit.server :as http]
            [todoapp.main.server :refer [middleware]]
            [clojure.tools.namespace.repl :as tools-ns]))

(tools-ns/set-refresh-dirs "src")

(defonce server (atom nil))

(defn start []
  (let [result (http/run-server middleware {:port 3000})]
    (reset! server result)
    :ok))

(defn stop []
  (when @server
    (@server)))

(defn restart []
  (stop)
  (tools-ns/refresh :after 'user/start))

(comment
  (start)
  (restart))
