(ns assembler.core-test
  (:require [clojure.test :as t]
            [assembler.core :as c]))

(t/deftest tokenize
 (t/testing "Tokenization"
   (t/is (c/preprocess "ldi r15, 20")
         [{:line 0, :col 0, :type :instruction, :val :ldi}
          {:line 0, :col 4, :type :register, :val 15}
          {:line 0, :col 4, :type :comma}
          {:line 0, :col 9, :type :number, :val 20}])))

;; (t/deftest tokenize
;;   (t/testing "Parsing"
;;     (t/is ())))

;; (t/deftest parse)

;; (t/deftest)
