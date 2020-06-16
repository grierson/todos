(ns todoapp.main.server
  (:require [com.fulcrologic.fulcro.server.api-middleware :refer [not-found-handler
                                                                  wrap-api
                                                                  wrap-transit-params
                                                                  wrap-transit-response]]
            [clojure.core.async :as async]
            [todoapp.main.model.todoitem :as todoitem]
            [com.wsscode.pathom.core :as p]
            [com.wsscode.pathom.connect :as pc]
            [taoensso.timbre :as log]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [ring.middleware.resource :refer [wrap-resource]]))

(def resolvers [todoitem/resolvers])

(def parser (p/parallel-parser
              {::p/env     {::p/reader                 [p/map-reader
                                                        pc/parallel-reader
                                                        pc/open-ident-reader]
                            ::pc/mutation-join-globals [:tempids]}
               ::p/mutate  pc/mutate-async
               ::p/plugins [(pc/connect-plugin {::pc/register resolvers})
                            (p/post-process-parser-plugin p/elide-not-found)
                            p/error-handler-plugin]}))

(def middleware (-> not-found-handler
                    (wrap-api {:uri    "/api"
                               :parser (fn [query]
                                         (log/info "Process" query)
                                         (async/<!! (parser {} query)))})
                    (wrap-transit-params)
                    (wrap-transit-response)
                    (wrap-resource "public/main")
                    wrap-content-type
                    wrap-not-modified))


(comment
  (async/<!! (parser {} [{[:list/id 1] [:list/id {:list/items [:item/label]}]}])))
