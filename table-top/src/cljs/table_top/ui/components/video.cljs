(ns table-top.ui.components.video
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]))

(def getUserMedia (or js/navigator.mozGetUserMedia
                      js/navigator.webkitGetUserMedia))

(defn get-user-media [config success error]
  (.call getUserMedia js/navigator (clj->js config) success error))

(defn renderStream [stream]
  (let [videoTracks ()]))

(defui Video
  Object
  (componentDidMount [this]
    (get-user-media {:audio false :video true}
                    (fn [stream]
                      (let [vc (dom/node this)]
                        (set! (.-autoplay vc) true)
                        (set! (.-srcObject vc) stream)))
                    (fn [error]
                      (.log js/console error))))
  (render [this]
    (dom/video nil)))

(def video (om/factory Video))
