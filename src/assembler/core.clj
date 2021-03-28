(ns assembler.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defonce word-size 32)
(defonce opcode-size 5)

(defonce register {:type :register :size 4})
(defonce condition {:type :condition :size 4})
(defonce immediate {:type :immediate :size 19})

(defonce i-format
  {:used [register register immediate]
   :unused 0})

(defonce r-format
  {:used [register register register]
   :unused 15})

(defonce i-format-no-immediate
  {:used [register register]
   :unused 19})

(defonce b-format
  {:used [register condition immediate]
   :unused 0})

(defonce j-format
  {:used [register]
   :unused 23})

(defonce instructions
  {"ld"   {:opcode 2r00000 :format :i}
   "ldi"  {:opcode 2r00001 :format :i}
   "st"   {:opcode 2r00010 :format :i}
   "add"  {:opcode 2r00011 :format :r}
   "sub"  {:opcode 2r00100 :format :r}
   "shr"  {:opcode 2r00101 :format :r}
   "shl"  {:opcode 2r00110 :format :r}
   "ror"  {:opcode 2r00111 :format :r}
   "rol"  {:opcode 2r01000 :format :r}
   "and"  {:opcode 2r01001 :format :r}
   "or"   {:opcode 2r01010 :format :r}
   "addi" {:opcode 2r01011 :format :i}
   "andi" {:opcode 2r01100 :format :i}
   "ori"  {:opcode 2r01101 :format :i}
   "mul"  {:opcode 2r01110 :format :i-no-immediate}
   "div"  {:opcode 2r01111 :format :i-no-immediate}
   "neg"  {:opcode 2r10000 :format :i-no-immediate}
   "not"  {:opcode 2r10001 :format :i-no-immediate}
   "brmi" {:opcode 2r10010 :format :b}
   "brnz" {:opcode 2r10010 :format :b}
   "brpl" {:opcode 2r10010 :format :b}
   "brzr" {:opcode 2r10010 :format :b}
   "jr"   {:opcode 2r10011 :format :j}
   "jal"  {:opcode 2r10100 :format :j}})

(declare
 split-into-lines
 trim-comment-from-line)

(defn preprocess [s]
  (->>
   s
   split-into-lines
   (map trim-comment-from-line)
   (map str/trim)))

(defn split-into-lines [s]
  (str/split s #"\n"))

(defn trim-comment-from-line [s]
  (str/replace s #"\#.*$" ""))

(declare
 split-into-tokens
 parse-arg-tokens)

(defn parse-line [line]
  (->>
   line
   split-into-tokens
   (assoc {} :remaining)
   parse-arg-tokens))

(defn split-into-tokens [line]
  (reduce
   (fn [{:keys [state]}])

   {:state :start, :tokens []}))


(defn parse-arg-tokens [[mnemonic & args]]
  (let [instruction (instructions mnemonic)
        format (instruction :format)
        args-with-format-key (assoc args :format format)]
       (parse-arg-tokens args-with-format-key)))

(declare
 parse-register-token
 parse-constant-token
 parse-register-with-offset-token)

(defmulti parse-arg-tokens :format)

(defmethod parse-arg-tokens :i [tokens]
  tokens)

(defmethod parse-arg-tokens :r [tokens]
  tokens)

(defmethod parse-arg-tokens :i-no-immediate [tokens]
  tokens)

(defmethod parse-arg-tokens :b [tokens]
  tokens)

(defmethod parse-arg-tokens :j [tokens]
  tokens)

(defn parse-register-token [[t & ts]]
  ,,,)
 
(defn parse-constant-token [[t & ts]]
  ,,,)

(defn parse-register-with-offset-token [[t & ts]]
  ,,,)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (->>
   "add r1, r2, r3 # foo \nsub r1, r2, r4\n"
   preprocess
   (map parse-line)))

(-main)
