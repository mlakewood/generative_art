(defproject sketch01 "1.0"
  :description "My example sketch."
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [quil "2.7.1"]
                 [org.apache.commons/commons-math3 "3.6"]
                 [com.bhauman/rebel-readline "0.1.4"]
                 [incanter "1.9.3"]
                 ]
  :jvm-opts ["-Xms1100m" "-Xmx1100M" "-server"]
  :source-paths ["src/clj"]
  :java-source-paths ["src/java"]
  :aot [sketch.dynamic]
  )
