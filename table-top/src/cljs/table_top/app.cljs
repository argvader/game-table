(ns table-top.app
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn widget [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/h1 nil (:text data)))))

(defn init []
  (om/root widget {:text "Gametable!!"}
           {:target (. js/document (getElementById "gametable"))}))
