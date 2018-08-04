(ns sketch.dynamic
  (:require [quil.core :as q])
  (:use [incanter.core :only [$=]])
  (:use [clojure.math.combinatorics :only [combinations cartesian-product]])
  (:use [clojure.pprint])
  (:use [clojure.set :only [union]])
  (:use [clojure.contrib.map-utils :only [deep-merge-with]])
  (:import [org.apache.commons.math3.distribution ParetoDistribution])
  (:import [processing.core PShape PGraphics])
  )

;; (defn h
;;   ([] (h 1.0))
;;   ([value] (* (height) value)))

;; (defn w
;;   ([] (w 1.0))
;;   ([value] (* (width) value)))

(defn setup []
  )




;; (defn draw []
;;   (no-loop)

;;   (color-mode :hsb 360 100 100 1.0)

;;   (background 220 0 66)
;;   (rect (w 0.1) (h 0.1) (w 0.5) (h 0.5)) ()
;;   (save "sketch.tif"))


;; ## Create blue parallel lines
(defn- draw-line [s-x s-y x-padding y-padding max-size]
  (loop [start-x s-x start-y s-y]
    (let [center-x (/ (q/width) 2)
          center-y (/ (q/height) 2)
          size (rand-int max-size)
          x1 start-x
          y1 start-y
          x2 start-x ;;(+ center-x 1000)
          y2 (+ start-y size)
          radius 2000
          params [(- (rand-int 255) (/ radius 2))
                  50
                  (mod 200 radius)
                  (mod 200 radius)
                  ]
          params [(/ y2 5) y2 100 200]
          next-x (if (> y2 (q/height))
                   (+ x1 x-padding)
                   start-x
                   )
          next-y (if (not= next-x start-x)
                   0
                   (+ y2 y-padding))
          ]
      (apply q/stroke params)
      (q/line x1 y1 x2 y2)
      (when (< start-x (+ (q/width) 100) )
        (recur next-x next-y)))
    )
  )

(defn draw-state [state]
  (q/frame-rate 1)
;;  (q/no-loop)
  (q/stroke-weight 10)
  (q/background 20)
  (q/color-mode :hsb)
  (draw-line 0 0 20 20 64)
;;  (dorun (repeatedly 300 #(draw-line 0 0 20 20)))
  )

;; ## Create a spiral of arcs



;; (defn update-state [state]
;;   state)

;; (defn- draw-spiral []
;;   (let [num-segments 50
;;         max-angle (* 80 2 Math/PI)
;;         diff-angle (/ Math/PI num-segments 0.7)
;;         diff-radius 0.01
;;         noise-amplitude 30
;;         center-x (/ (q/width) 2)
;;         center-y (/ (q/height) 2)]
;;     (loop [angle 0 radius 0 noise (rand 100)]
;;       (let [new-angle (+ diff-angle angle)
;;             new-radius (+ (+ diff-radius radius)
;;                           (- (* (q/noise noise) noise-amplitude)
;;                              (/ noise-amplitude 2)))
;;             x1 (+ center-x (* radius (Math/cos angle)))
;;             y1 (+ center-y (* radius (Math/sin angle)))
;;             x2 (+ center-x (* new-radius (Math/cos new-angle)))
;;             y2 (+ center-y (* new-radius (Math/sin new-angle)))]
;;         ;;(println radius)
;;         (q/stroke (- (rand-int 255) (/ radius 2))
;;                   50
;;                   (mod 200 (inc radius))
;;                   (mod 200 (inc radius)))
;;         (q/line x1 y1 x2 y2)
;;         (when (< new-angle max-angle)
;;           (recur new-angle new-radius (+ noise 0.05)))))))

;; (defn draw-state [state]
;;   (q/frame-rate 1)
;;   (q/stroke-weight 0.1)
;;   (q/background 20)
;;   (q/color-mode :hsb)
;;   (dorun (repeatedly 300 draw-spiral))
;;   )


;; ## animated squigly line

;; (defn setup []
;;   (q/smooth)
;;   (q/frame-rate 30)
;;   {:noise-seed (q/random 10)
;;    :background 100})

;; (defn update-state [state]
;;   (let [noise-seed (+ (:noise-seed state) 0.08)
;;         background (+ (:background state) (- (q/noise noise-seed) 0.5))
;;         x-range 600
;;         y-range 100
;;         xs (range 0 x-range 2)
;;         ys (map-indexed
;;             (fn [n _] (* (q/noise (+ noise-seed (* n 0.03))) y-range))
;;             xs)]
;;     {:x-range x-range
;;      :y-range y-range
;;      :noise-seed noise-seed
;;      :background background
;;      :line-segments (partition 2 (interleave xs ys))}))

;; (defn draw-state [{:keys [x-range y-range line-segments background]}]
;;   (q/background background)
;;   (q/translate (/ (- (q/width) x-range) 2)
;;                (/ (- (q/height) y-range) 2))
;;   (loop [[head & tail] line-segments]
;;     (when tail
;;       (q/stroke (- background (* (/ (first head) x-range) background)))
;;       (q/stroke-weight (/ (second head) 3))
;;       (q/line head (first tail))
;;       (recur tail))))


;; ## Tree stuff
;; (defn rand-between [start end]
;;   (+ start (rand (inc (- end start)))))

;; (defn grow-tree [depth size]
;;   (let [tree {:size size
;;               :angle (rand-between -1 4)
;;               :branches ()}]
;;     (if (> (dec depth) 0)
;;       (assoc tree :branches (repeatedly size #(grow-tree (dec depth) size)))
;;       tree)))

;; (defn setup []
;;   (q/frame-rate 1)
;;   {:trees ()})

;; (defn update-state [state]
;;   (let [mod-state (if (not (contains? state :counter))
;;                     (assoc state :counter 3)
;;                     state
;;                     )
;;         mod-state (update-in mod-state [:counter] dec)
;;         mod-state (if (> (:counter mod-state) 0)
;;                             (update-in mod-state [:trees] conj {:pos [(rand-between -300 300) (rand-between -300 300)]
;;                                                                 :tree (grow-tree 5 4)})
;;                             mod-state)
;;         ]
;;     mod-state))

;; (defn draw-tree [start {:keys [size angle branches]}]
;;   (when (> size 0)
;;     (let [length (* size 30)
;;           end [(+ (first start) (* length (Math/sin angle)))
;;                (- (second start) (* length (Math/cos angle)))]
;;           color [160 (* 250 (/ angle (* 2 Math/PI))) 100]
;;           ]
;;       (apply q/stroke color)
;;       (q/line start end)
;;       (doseq [branch branches]
;;         (draw-tree end branch)))))

;; (defn draw-state [state]
;;   (q/background 0)
;;   (q/stroke-weight 1)
;;   (q/stroke 200)
;;   (q/with-translation [(/ (q/width) 2)
;;                        (/ (q/height) 2)]
;;     (doseq [{:keys [pos tree]} (:trees state)]
;;       (draw-tree pos tree))))

;; worms

;; (defn setup []
;;   (q/frame-rate 120)
;;   (q/color-mode :hsb)
;;   ;; setup function returns initial state. It contains
;;   ;; circle color and position.
;;   {:color 0
;;    :angle 0
;;    :offset 0
;;    :move 0
;;    })

;; (defn sin-scale [value max]
;;   (->  (/ (mod value max) max)
;;        (* Math/PI 2)
;;        Math/sin
;;        (* (/ max 2))
;;        (+ (/ max 2))))

;; (defn update-state [state]
;;   ;; Update sketch state by changing circle color and position.
;;   {:color (+ (:color state) (rand 1))
;;    :angle (+ (:angle state) (rand 0.005))
;;    :offset (+ (rand 1) (:offset state))
;;    :move (+ (:move state) (- (rand 2) 1))
;;    })

;; (defn draw-state [state]
;;   (let [offset (sin-scale (:offset state) 50)]
;;     ;; Set circle color.
;;     (q/fill (sin-scale (:color state) 255)
;;             (sin-scale (:color state) 50)
;;             (+ 20 (sin-scale (:color state) 100))
;;             100)
;;     (q/no-stroke)
;;     ;; Calculate x and y coordinates of the circle.
;;     (let [angle (:angle state)
;;           radius (sin-scale (:offset state) 300)
;;           x (+ (+ (* radius (q/cos angle)) offset) (:move state))
;;           y (+ (+ (* radius (q/sin angle)) offset) (:move state))
;;           width (+ 10 (sin-scale (:offset state) 100))
;;           ]
;;       ;; Move origin point to the center of the sketch.
;;       (q/with-translation [(/ (q/width) 2)
;;                            (/ (q/height) 2)]
;;         ;; Draw the circle.
;;         (q/ellipse x y width width)))))

;; ## fuzzicles

;; (defn point-on-circle
;;   ([angle]
;;    (point-on-circle angle 1))
;;   ([angle radius]
;;    [(* radius (q/cos angle))
;;     (* radius (q/sin angle))]))

;; (defn points-on-circle [start diff]
;;   (lazy-seq (cons (point-on-circle start)
;;                   (points-on-circle (+ start diff) diff))))

;; (defn setup []
;;   (q/smooth)
;;   (q/frame-rate 0.5)
;;   {:noise-seed (q/random 10)
;;    :radius 10})

;; (defn update-state [state]
;;   (-> state
;;       (assoc :radius 300
;;              :noise-seed (q/random 100))))

;; (defn- draw-fuzzicle [position radius noise-seed]
;;   (doseq [angle (range 0 Math/PI 0.002)]
;;     (let [rand-angle (+ angle
;;                         (q/noise (+ (* 2 angle) noise-seed)))
;;           rand-position (mapv #(+ % (* 100 (q/noise (+ (* 2 angle) noise-seed))) -50)
;;                               position)
;;           rand-radius (* (q/noise (+ (* 2 angle) noise-seed))
;;                          radius)
;;           color [160 (* 250 (/ angle (* 2 Math/PI))) 100]]
;;       (apply q/stroke color)
;;       (q/with-translation rand-position
;;         (q/line (point-on-circle rand-angle rand-radius)
;;                 (point-on-circle (+ rand-angle Math/PI) rand-radius))))))

;; (defn draw-state [{:keys [radius noise-seed]}]
;;   (q/background 0)
;;   (q/stroke-weight 0.1)
;;   (q/stroke 200)
;;   (draw-fuzzicle [(/ (q/width) 2) (/ (q/height) 2)] 400 noise-seed))


;; ## evo
;; (def frame-rate 20)

;; (defn create-cell [position]
;;   {:generation 0
;;    :position position
;;    :energy (rand 1)
;;    :size 60})

;; (def ^:dynamic *grid-size* 30)

;; (defn max-generation [cells]
;;   (apply max (map :generation cells)))

;; (defn- latest-generation
;;   [cells]
;;   (filter #(= (:generation %) (max-generation cells)) cells))

;; (def cells-per-generation 20)
;; (def cells-max-age 20)

;; (defn kill-oldest-generation [cells]
;;   (if (> (max-generation cells) cells-max-age)
;;     (drop cells-per-generation cells)
;;     cells))

;; (defn- update-energy
;;   [noise-seed {:keys [energy position] :as cell}]
;;   (assoc cell :energy (let [new-energy (-> (q/noise (+ energy (* noise-seed (inc position))))
;;                                            (- 0.5)
;;                                            (+ energy))]
;;                         (cond
;;                           (< new-energy 0) 0
;;                           (> new-energy 1) 1
;;                           :default new-energy))))

;; (defn evolve-cells [noise-seed cells]
;;   (->> cells
;;        latest-generation
;;        (map #(update-in % [:generation] inc))
;;        (map (partial update-energy noise-seed))
;;        (concat cells)))

;; (defn translate-to-center []
;;   (q/translate (/ (q/width) 2)
;;                (/ (q/height) 1.1)))

;; (defn center-last-generation [cells]
;;   (translate-to-center)
;;   (q/translate (- (/ (* *grid-size* cells-per-generation) 2))
;;                (- (* *grid-size* (max-generation cells)))))

;; (defn reset-state []
;;   {:noise-seed (q/random 100)
;;    :cells (map create-cell (range cells-per-generation))})

;; (defn setup []
;;   (q/color-mode :hsb 1.0)
;;   (q/frame-rate frame-rate)
;;   (q/no-smooth)
;;   (reset-state))

;; (defn update-state [{:keys [noise-seed] :as state}]
;;   (-> state
;;       (update-in [:noise-seed] #(+ % 0.1))
;;       (update-in [:cells] (partial evolve-cells noise-seed))
;;       (update-in [:cells] kill-oldest-generation)))

;; (defn draw-cell [{:keys [size generation position energy]}]
;;   (let [size (* *grid-size* 2)]
;;     (q/fill energy
;;             1
;;             energy
;;             0.7)
;;     (q/ellipse (* *grid-size* position)
;;                (* *grid-size* generation)
;;                (* size energy)
;;                (* size energy))))

;; (defn draw-state [{:keys [cells noise-seed]}]
;;   (q/background 0 0 0.03)
;;   (binding [*grid-size* (/ (q/height) cells-max-age 1.2)]
;;     (center-last-generation cells)
;;     (doall (map draw-cell cells))))

;; ## circles
;; (defn point-on-circle [angle]
;;   [(q/cos angle)
;;    (q/sin angle)])

;; (defn points-on-circle [start diff]
;;   (lazy-seq (cons (point-on-circle start)
;;                   (points-on-circle (+ start diff) diff))))

;; (defn setup []
;;   (q/smooth)
;;   (q/frame-rate 30)
;;   {:noise-seed (q/random 10)
;;    :radius 10})

;; (defn update-state [state]
;;   {:radius (if (:grow state)
;;              (+ (:radius state) 0.3)
;;              (- (:radius state) 0.3))
;;    :grow (cond
;;            (> (:radius state) 100) false
;;            (< (:radius state) 20) true
;;            :default (:grow state))})

;; (defn- draw-circle [pos state size]
;;   (q/translate pos)
;;   (let [num-points 10]
;;     (doseq [[x y] (take num-points
;;                         (points-on-circle (* (q/millis) 0.0009)
;;                                           (/ Math/PI num-points 0.5)))]
;;       (let [radius (+ (* 20 (q/noise (rand-int 2))) (:radius state))
;;             xr (* x radius)
;;             yr (* y radius)]
;;         (q/fill (* 200 (q/noise (/ (q/millis) 1000)))
;;                 90 30)
;;         (q/ellipse xr yr size size)
;;         (q/point xr yr)))))

;; (defn draw-state [state]
;;   (q/background 20)
;;   (q/stroke 0)
;;   (draw-circle [200 110] state 20)
;;   (draw-circle [120 150] state 30))
