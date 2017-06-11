(ns spectodo.core
  (:require [rum.core :as rum]
            [scrum.core :as scrum]
            [spectodo.view :as view]
            [spectodo.reconciler :as reconciler]))


(def r reconciler/reconciler)

(defonce init-ctrl (scrum/broadcast-sync! r :default))

(rum/mount (view/View r)
           (. js/document (getElementById "app")))
