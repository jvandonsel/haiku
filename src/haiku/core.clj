;;; Random haiku generator, using a simple sentence grammar.
;;; Vocabulary is in module vocab.clj.
;;;
;;; Programmer: J van Donsel
;;; December, 2016
;;;
(ns haiku.core
  (:require [clojure.string :as str])
  (:use [haiku.vocab])
  )

(defn pluralize-noun [w]
  (if (coll? w)
    (map pluralize-noun w)
    (if (contains? irregular-plurals w)
      (irregular-plurals  w)
      (str/join (concat w "s")))
    )
  )

(defn pluralize-verb [w]
  (if (coll? w)
    (map pluralize-verb w)
    (if (contains? irregular-plurals w)
      (irregular-plurals w) 
      (str/join (drop-last w)))
    )
  )


;; Applies function f to all values of the given map
(defn update-map [m f]
  (reduce-kv (fn [acc k v] (assoc acc k (f v))) {} m)
  )

;; Pluralization
(def verbs-p-trans-map   (update-map verbs-s-trans-map   pluralize-verb))
(def verbs-p-trans       (flatten (vals verbs-p-trans-map)))
(def verbs-p-intrans-map (update-map verbs-s-intrans-map pluralize-verb))
(def verbs-p-intrans     (flatten (vals verbs-p-intrans-map)))
(def nouns-p-map         (update-map nouns-s-map         pluralize-noun) )
(def nouns-p             (flatten (vals nouns-p-map)))


;; Chooses a random element from coll
(defn CHOOSE-1 [coll]
  (rand-nth coll))

;; chooses a random element from xs, where each element of xs contains [probability value]
(defn CHOOSE-1-WEIGHTED [xs]
  (let [
        ;; accumulate the weights
        stacked (reductions (fn [a b] [(+ (first a) (first b)) (second b)] )  xs)
        ;; pick a random value between 0.0 and 1.0
        r (rand)
        ;; find the first accumulated weight over the random number
        x (first (filter #(> (first %) r) stacked))
        ]
    (second x)
    )
  )

;; randomly either returns x or "".
;; p is probability of ""
(defn OPT [p x]
  (if (< (rand) p) x "") )


;; Grammar
(defn gram-noun-phrase-singular       []  [(CHOOSE-1 articles) (OPT 0.33 (CHOOSE-1 adjectives)) (CHOOSE-1 nouns-s)])
(defn gram-noun-phrase-singleton      []  (CHOOSE-1 singleton-nouns))
(defn gram-noun-phrase-plural         []  [(OPT 0.33 (CHOOSE-1 articles-plural)) (OPT 0.33 (CHOOSE-1 adjectives)) (CHOOSE-1 nouns-p)])
(defn gram-object-phrase              []  (CHOOSE-1-WEIGHTED [[0.40 (gram-noun-phrase-singular)] [0.40 (gram-noun-phrase-plural)] [0.2 (gram-noun-phrase-singleton)]]))

(defn gram-verb-phrase-sing-trans     []  [(CHOOSE-1 verbs-s-trans) (gram-object-phrase) (OPT 0.33 (CHOOSE-1 adverbs))])
(defn gram-verb-phrase-sing-intrans   []  [(CHOOSE-1 verbs-s-intrans) (OPT 0.33 (CHOOSE-1 adverbs))])
(defn gram-verb-phrase-sing           []  (CHOOSE-1 [(gram-verb-phrase-sing-trans) (gram-verb-phrase-sing-intrans)]))

(defn gram-verb-phrase-plural-trans   []  [(CHOOSE-1 verbs-p-trans) (gram-object-phrase)])
(defn gram-verb-phrase-plural-intrans []  (CHOOSE-1 verbs-p-intrans))
(defn gram-verb-phrase-plural         []  (CHOOSE-1 [(gram-verb-phrase-plural-trans) (gram-verb-phrase-plural-intrans)]))

(defn gram-line [] (CHOOSE-1 [
                                [(gram-noun-phrase-singular) (gram-verb-phrase-sing)]
                                [(gram-noun-phrase-plural) (gram-verb-phrase-plural)]
                                [(gram-noun-phrase-singleton) (gram-verb-phrase-sing)]
                                ]))


;; Given a scalar k and a collection,
;; generates a map of coll[0]->k, coll[1]->k...
(defn gen-map [k coll]
  ( let [
         lc (map str/lower-case coll)
         ;; generate [lc[0] k lc[1] k ...]
         kvs (concat  (interpose k lc) [k])
         ]
   (apply array-map kvs )
   ))

;; Given a map of syllables to word lists, inverts the map
;; to generate a map of word to syllable count.
(defn invert-word-map [m]
  (let []
    (reduce-kv
     (fn [acc syllable-count words] (merge acc (gen-map syllable-count words)))
     {} m))
  )

;; Create a map of all words to their syllable counts.
(def all-words ( merge
                (invert-word-map adjectives-map)
                (invert-word-map nouns-s-map)
                (invert-word-map nouns-p-map)
                (invert-word-map verbs-s-intrans-map)
                (invert-word-map verbs-s-trans-map)
                (invert-word-map verbs-p-intrans-map)
                (invert-word-map verbs-p-trans-map)
                (invert-word-map articles-map)
                (invert-word-map articles-plural-map)
                (invert-word-map singleton-nouns-map)
                (invert-word-map adverbs-map)
                ;; "an" doesn't appear in the articles list. it's applied in post-processing, but we need a syllable count for it.
                {"an" 1}
                ))


;; Returns the number of syllables for the given word
(defn get-syllables [word]
  (let [lookup (get all-words (str/lower-case word))]
    (if (nil? lookup) 0 lookup)
    )
  )

;; Count the number of syllables in a sentence (a list of words)
(defn count-sentence-syllables [words]
  (reduce + 0 (map get-syllables words)))

(defn starts-with-vowel [word]
  (contains? #{"a" "e" "i" "o" "u"} (str/lower-case (first word))))

;; Returns true if this word should use "an" instead of "a"
(defn needs-an [word]
  (or (starts-with-vowel word) (contains? use-an word)))

(defn remove-blanks [line]
  (remove empty? line))

;; Change 'a' to 'an' if the next word starts with a vowel
;; or is in our set of words forced to use "an".
(defn a-to-an [line]
  (let [n (count line)]
    (map-indexed (fn [i val]
                   (let
                       [done (>= i (dec n))
                        next-val (when-not done (nth line (inc i)))
                        ]
                     (if (= "a" val)
                       (if (needs-an next-val)
                         "an"
                         "a")
                       val)
                     )) line )))



;; Capitalizes the first letter of a word
;; (Don't use str/capitalize. It munges the case of the other letters.)
(defn capitalize-word [word]
  (let [first-letter (first word)
        ]
    (str/join (cons (str/upper-case first-letter) (rest word)))
    )
  )

;; Capitalizes the first word in a line
(defn capitalize-line [words]
  (let [
        updated-word (capitalize-word (first words))
        capitalized (cons updated-word (rest words))
        ]
    capitalized
    )
  )


;; Some post-processing after creating our haiku
(defn post-process-line [line]
  (let [
        ;; get rid of empty words
        non-empty (remove-blanks line)
        
        ;; change 'a' to 'an' if the next word starts with a vowel
        fixed-articles (a-to-an non-empty)
        ]
    ;; capitalize first letter of line
    (capitalize-line fixed-articles)
    ))

;; Creates a single line from the given grammar function, respecting the syllable target.
(defn create-line [gram syllable-target]
  (let [raw-line (flatten (gram))
        clean-line (post-process-line raw-line)
        k (count-sentence-syllables clean-line)]
    
    ;; Brute force: if we didn't hit our syllable target, try again!
    (if (= syllable-target k)
      clean-line
      (recur gram syllable-target)
      )
    ))

; Creates a haiku, 3 lines with syllable counts of 5,7,5.
(defn create-haiku []
  (let [
        line1 (create-line gram-line 5)
        line2 (create-line gram-line 7)
        line3 (create-line gram-line 5)
        ]
    (list (str/join " " line1)
          (str/join " " line2)
          (str/join " " line3))

    ))

;; Prints out each haiku line 
(defn -main []
  (doall (map println  (create-haiku)))
  nil
  )
