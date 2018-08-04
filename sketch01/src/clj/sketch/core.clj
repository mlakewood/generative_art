(ns sketch.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [sketch.dynamic :as dynamic] :reload)
  (:gen-class))

(q/defsketch example
    :title "Sketch"
    :setup dynamic/setup
    :update dynamic/update-state
    :draw dynamic/draw-state
    :size [900 900]
    :features [:keep-on-top :resizable]
    :middleware [m/fun-mode]
)

(defn refresh []
  (use :reload `sketch.dynamic)
  (if (nil? example)
    (println "Example is nil"))

  (println "refreshing...")
  (.loop example))
