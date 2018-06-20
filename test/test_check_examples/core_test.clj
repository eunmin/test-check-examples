(ns test-check-examples.core-test
  (:require [clojure.test :refer :all]
            [test-check-examples.core :refer :all]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.spec.test.alpha :as stest]))

(defspec sort-idempotent-prop
  (prop/for-all [v (gen/vector gen/int)]
                (= (sort v) (sort (sort v)))))

(defspec prop-sorted-first-less-than-last
  (prop/for-all [v (gen/not-empty (gen/vector gen/int))]
                (let [s (sort v)]
                  (<= (first s) (last s)))))

(defspec first-element-is-min-after-sorting 100
  (prop/for-all [v (gen/not-empty (gen/vector gen/int))]
                (= (apply min v)
                   (first (sort v)))))

(deftest ranged-rand-test
  (is (true? (-> (stest/check `ranged-rand)
                 first
                 :clojure.spec.test.check/ret
                 :result))))
