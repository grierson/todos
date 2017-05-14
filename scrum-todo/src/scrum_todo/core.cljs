(ns scrum-todo.core
  (:require [rum.core :as rum]
            [scrum.core :as scrum]
            [scrum-todo.view :as view]
            [scrum-todo.reconciler :as reconciler]))


(def r reconciler/reconciler)
;; initialize controllers
(defonce init-ctrl (scrum/broadcast-sync! r :init))
;;(def init-ctrl (scrum/broadcast-sync! reconciler :init))

;; render
(rum/mount (view/App r)
           (. js/document (getElementById "app")))
