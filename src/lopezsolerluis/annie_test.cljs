(ns ^:figwheel-hooks lopezsolerluis.annie-test
  (:require
   [goog.dom :as gdom]
   [reagent.core :as r :refer [atom]]
   [reagent.dom :as rdom]
   [cljsjs.react-vis :as rvis]))

(defn crear-etiqueta-1 [x y id position]
  [:<>
    ^{:key id}
     [:> rvis/CustomSVGSeries {:data [{:x x :y y
                                 :customComponent (fn []
                                   (let [[inc-x inc-y] position]
                                    (r/as-element [:g {:className "etiqueta"}
                                                     [:text
                                                       [:tspan {:x inc-x :y (+ inc-y 0)} "Hidrógeno"]
                                                       [:tspan {:x inc-x :y (+ inc-y 18)} "Alfa"]]])))}]}]
    ^{:key (str key "line")}
    [:> rvis/CustomSVGSeries {:data [{:x x :y y
                                      :customComponent (fn []
                                         (let [[inc-x inc-y] position]
                                           (r/as-element [:g {:className "etiqueta"}
                                                         [:polyline {:points [0 (if (< inc-y 5) -10 5) 0 inc-y inc-x inc-y]
                                                                     :stroke "black" :fill "none"}]])))}]}]
  ])

(defn crear-etiqueta-2 [x y id position]
    ^{:key id}
     [:> rvis/CustomSVGSeries {:data [{:x x :y y
                                 :customComponent (fn []
                                   (let [[inc-x inc-y] position]
                                    (r/as-element [:g {:className "etiqueta"}
                                                     [:text
                                                       [:tspan {:x inc-x :y (+ inc-y 0)} "Hidrógeno "]
                                                       [:tspan {:x inc-x :y (+ inc-y 18)} "Alfa"]]])))}]}]
    ^{:key (str key "line")}
    [:> rvis/CustomSVGSeries {:data [{:x x :y y
                                      :customComponent (fn []
                                         (let [[inc-x inc-y] position]
                                           (r/as-element [:g {:className "etiqueta"}
                                                         [:polyline {:points [0 (if (< inc-y 5) -10 5) 0 inc-y inc-x inc-y]
                                                                     :stroke "black" :fill "none"}]])))}]}]
  )

(defn crear-etiqueta-3 [x y id position]
  (vector
       [:> rvis/CustomSVGSeries {:data [{:x x :y y
                                 :customComponent (fn []
                                   (let [[inc-x inc-y] position]
                                    (r/as-element [:g {:className "etiqueta"}
                                                     [:text
                                                       [:tspan {:x inc-x :y (+ inc-y 0)} "Hidrógeno"]
                                                       [:tspan {:x inc-x :y (+ inc-y 18)} "Alfa"]]])))}]}]
       [:> rvis/CustomSVGSeries {:data [{:x x :y y
                                         :customComponent (fn []
                                            (let [[inc-x inc-y] position]
                                             (r/as-element [:g {:className "etiqueta"}
                                                           [:polyline {:points [0 (if (< inc-y 5) -10 5) 0 inc-y inc-x inc-y]
                                                                       :stroke "black" :fill "none"}]])))}]}]
    ))

(defn line-chart []
 [:div.graph
 (into
   [:> rvis/FlexibleXYPlot
      {:margin {:left 100 :right 50 :top 20} :xDomain [-10 10] :yDomain [-10 10]}
      ;(crear-etiqueta-1 0 0 "key-1" [30 40])   ; <- nothing shows up
      ;[crear-etiqueta-1 0 0 "key-1" [30 40]]   ; <- nothing shows up
      ;(crear-etiqueta-2 0 0 "key-1" [30 40])   ; <- only last CustomSVGSeries shows up
      ;[crear-etiqueta-2 0 0 "key-1" [30 40]]    ; <- nothing shows up
    ]
    ;(crear-etiqueta-3 0 0 "key-1" [30 40]) ; <- Works great!
    (mapcat (fn [x] (crear-etiqueta-3 x 0 "key-1" [30 40]))  ; -> This too! :)
            (range 5))                                       ;
    )])

(defn get-app-element []
  (gdom/getElement "app"))

(defn mount [el]
  (rdom/render [line-chart] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(mount-app-element)

(defn ^:after-load on-reload []
  (mount-app-element))
