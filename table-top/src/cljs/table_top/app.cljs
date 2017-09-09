(ns table-top.app
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [goog.dom :as gdom]
            [table-top.ui.components.video :as video]))

(defui App
  Object
  (render [this]
    (dom/div nil
      (dom/h1 nil "GameTable !!")
      (video/video))))

(def app (om/factory App))

(defn init []
  (js/ReactDOM.render
    (app)
    (gdom/getElement "gametable")))
