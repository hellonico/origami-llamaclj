(ns hello
  (:require [com.phronemophobic.llama :as llama]
            [com.phronemophobic.llama.raw :as raw]
            [com.phronemophobic.llama.util :as llutil]
            [opencv4.utils :as u]
            [clojure.string :as str]))

(def fi (origami.filters.Annotate.))
(u/simple-cam-window (fn [mat] (.apply fi mat)))
(.setText fi "")
(.setPoint fi "10,150")
(.setFontSize fi 1.0)
(.setThickness fi 1.0)

(def hello (atom ""))
(def model "models/llama-2-7b-chat.ggmlv3.q4_0.bin")
(def ctx (llama/create-context model {:n-gpu-layers 1}))

(defn update-atom [s]
  (if (or (= "." s))
    (swap! hello #(str % "\n"))
    (swap! hello #(str % s)))
  ; (println s)
  (.setText fi @hello))

(defn print-response
  [ctx prompt]
   (transduce
    (take-while (fn [_] (not (Thread/interrupted))))
    (completing (fn [_ s] (update-atom s) (flush)))
    nil
    (llama/generate ctx prompt nil)))

(def prompt (or (first *command-line-args*) "Who was Napoleon?"))
(println prompt)
(print-response ctx prompt)