;;; Random haiku generator, using a simple sentence grammar.
;;;
;;; This vocabulary set relates to Amazon Robotics, but it's easy to customize.
;;;
;;; Programmer: J van Donsel
;;; December, 2016
;;;
(ns haiku
  (:require [clojure.string :as str]))

;; Adjectives
(def adjectives-map {
                     ;; 1-syllable words
                     1  ["orange" "strange" "green" "hot" "cold" "small" "large" "wide" "light" "strong" "weak" "dark" "black" "white" "glass" "deep" "red" "blue"
                         "green" "fast" "wild" "strange"]
                     ;; 2-syllable words
                     2 ["happy"   "snowy" "speedy" "noisy" "steady" "heavy" "crazy" "random" "laden" "metal" "mindless" "seamless"
                        "liquid" "yellow" "naked" "simple" "baby" "mobile"]
                     ;; 3-syllable words
                     3 [ "curious" "amazing" "electric" "enormous" "unladen" "important" "recursive"]
                     ;; 4-syllable words
                     4 ["interesting"]
                     ;; 5-syllable words
                     5 ["battery-powered"]
                     })
(def adjectives (flatten (vals adjectives-map)))

;; Adverbs
(def adverbs-map {
                  1 ["fast"]
                  2 ["alone" "loudly" "at night" "wildly" "deeply"]
                  3 ["silently" "patiently" "seamlessly" "quietly" ]
                  4 ["furiously"]
                  })
(def adverbs (flatten (vals adverbs-map)))

;; Singular nouns
(def nouns-s-map {
                  1 ["drive" "pod" "floor" "zone" "spring" "thing" "bin" "tray" "cell" "path" "beer" "star" "leaf" "bird" "tree"
                    "noise" "sound" "light" "rain" "ghost" "screen" "sun" "moon" "day" "night" "crow" "man" "friend" "gift" "truck"
                     "phone" "wire" "iPad" "tablet" "badge" "face" "file" "house" "rock" "box" "dog" "cat" "time" "cart" "dove" "glove"
                     "shelf" "bug" "team" "dev" "queue" "Mac" "hand" "aisle" "girl" "boy" "plan"]
                  2 ["robot" "robot" "station" "warehouse" "order" "human" "product" "winter" "summer" "autumn" "picker" "stower" "neighbor"
                     "window" "silence" "apple" "FC" "child" "mirror" "insect" "water"  "service" "server" "zombie" "woman" "person" "sunset"
                     "flower" "poem" "monkey" "eagle" "surprise" "button" "event" "finger" "machine" "resource" "function" "Kindle" "program"
                     "model" "virus" "query" "echo" "image" "system" "carton" "movie" "building" "pallet" "garden" "TT" "pipeline" "present"
                     "coffee" "sticker" "barcode" "laptop" "website" "cable"]
                  3 ["battery" "computer" "activity" "database" "customer" "conveyor" "mezzanine" "MMA" "manager" "programmer" "factory" "family" "video"
                    "obstacle" "obstruction" "terminal" "restriction" "area" "datacenter" "company"]
                  4 ["operator" "fiducial" "associate"]
                  })
(def nouns-s (flatten (vals nouns-s-map)))

;; Singular intransitive verbs
(def verbs-s-intrans-map {
                          1 ["picks" "stows" "waits" "hides" "stores" "rests" "jumps" "weeps" "howls" "screams" "beeps" "dies" "plays" "hums" "moves" "charges"
                           "blinks" "sings" "stops" "spins" "drifts" "sits" "glides"  "drives" "walks" "stands" "starves"]
                          2 ["listens" "enters" "watches" "twinkles" "echoes" "glimmers" "reboots" "travels" "rotates" "dances" "queues up" "crashes"]
                          3 ["navigates" "recovers" "emerges" ]
                          4 ["equalizes"]
                          })
(def verbs-s-intrans (flatten (vals verbs-s-intrans-map)))

;; Singular transitive verbs
(def verbs-s-trans-map {1 ["picks" "brings" "stows" "eats" "runs" "stores" "finds" "searches for" "hears" "sees" "loves" "hates" "breaks" "drinks" "counts"
                         "inspects" "takes" "ships" "scans" "builds" "senses" "lifts" "has" "maps" "tracks" "sends" "plans" "tests" "blocks"]
                        2 ["orders" "awaits" "gathers"  "reboots" "pushes" "commands" "rejects" "carries" "controls" "restricts" "constructs" "designs"
                           "programs" "accesses" "infects" "reflects" "supports" "smells like" "programs" "touches" "obstructs" "queries"
                           "digs out"]
                        3 ["notices" "surprises" "thinks about" "recovers" "contemplates" "manages" "prohibits" ]
                        4 ["consolidates"]
                        })
(def verbs-s-trans (flatten (vals verbs-s-trans-map)))

;; Articles
;; (the repetition is a cheap way of doing weighting)
(def articles-map {1  ["a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a" "a"
                       "the" "the" "the" "the" "the" "the" "the" "the" "the" "the" "the" "the" "the" "the" "the" "the" "the" "the"
                       "their" "my" "Jeff's"]})
(def articles (flatten (vals articles-map)))

;; Plural articles
(def articles-plural-map {1  ["some" "all" "no"]
                          2 ["many"]})
(def articles-plural (flatten (vals articles-plural-map)))

;; Nouns like "daylight" or "Jeff" that don't have plurals and (usually) don't use articles
;; (Is there a real name for this part of speech?)
(def singleton-nouns-map {1 ["Jeff" "work" "Ops" "the wind" "snow" "air" "code"]
                          2 ["daylight" "hardware" "software" "data" "sunshine" "sunlight" "storage" "the Web" "AR" "QA" "money"
                             "Java" "Apple" "Bezos" "Kiva" "dunnage" "FedEx" "music" "Kobra"]
                          3 ["Amazon" "amnesty" "Apollo" "Brazil" "Seattle"]
                          4 ["inventory" "America" "the Internet" "security" ]
                          } )
(def singleton-nouns (flatten (vals singleton-nouns-map)))


;; Irregular singlar->plurals for both nouns and verbs
;; TODO: when pluralized, some of these words change their syllable count.
(def irregular-plurals {"battery" "batteries"
                        "watches" "watch"
                        "leaf" "leaves"
                        "searches for" "search for"
                        "pushes" "push"
                        "child" "children"
                        "man" "men"
                        "woman" "women"
                        "person" "people"
                        "activity" "activities"
                        "carries" "carry"
                        "accesses" "access"
                        "box" "boxes"
                        "echoes" "echo" ; verb
                        "echo" "echoes" ; noun
                        "family" "families"
                        "thinks about" "think about"
                        "factory" "factories"
                        "virus" "viruses"
                        "smells like" "smell like"
                        "has" "have"
                        "shelf" "shelves"
                        "touches" "touch"
                        "queries" "query" ; verb
                        "query" "queries" ; noun
                        "queues up" "queue up"
                        "company" "companies"
                        "digs out" "dig out"
                        "crashes" "crash"
                        })


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


;;; Grammar
(def noun-phrase-singular  [[:choose-1 articles] [:opt [:choose-1 adjectives]] [:choose-1 nouns-s]])
(def noun-phrase-singleton [:choose-1 singleton-nouns])
(def noun-phrase-plural    [[:opt [:choose-1 articles-plural]] [:opt [:choose-1 adjectives]] [:choose-1 nouns-p]])
(def noun-phrase           [:choose-1-weighted [[0.45 noun-phrase-singular] [0.45 noun-phrase-plural] [0.1 noun-phrase-singleton]]])

(def verb-phrase-sing-trans   [[:choose-1 verbs-s-trans] noun-phrase [:opt [:choose-1 adverbs]]])
(def verb-phrase-sing-intrans [:choose-1 verbs-s-intrans])
(def verb-phrase-sing         [:choose-1 [verb-phrase-sing-trans verb-phrase-sing-intrans]])

(def verb-phrase-plural-trans    [[:choose-1 verbs-p-trans] noun-phrase])
(def verb-phrase-plural-intrans  [:choose-1 verbs-p-intrans])
(def verb-phrase-plural          [:choose-1 [verb-phrase-plural-trans verb-phrase-plural-intrans]])

(def line-recipe [:choose-1 [[noun-phrase-singular verb-phrase-sing]
                             [noun-phrase-plural verb-phrase-plural]]])


;; given:
;;   vs=[1 2 3 4]
;;   k=9
;; returns:
;;   [1 9 2 9 3 9 4 9]
(defn alternate [vs k]
  (concat  (interpose k vs) [k]) 
  )

;; Given a map of syllables to word lists, inverts the map
;; to generate a map of word to syllable count.
;; TODO: rewrite this fugly map inversion.
(defn invert-word-map [m]
  (let []
    (reduce-kv
     (fn [acc syllable-count words]
       (apply assoc acc (alternate (map str/lower-case words) syllable-count) ) )
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
                ;; "an" doesn't appear in the articles list. it's applied in post-processing.
                {"an" 1}
                ))


;; Returns true with a probability of p
(defn randy [p]
  (< (rand) p ))

;; Chooses a random element from coll
(defn choose-1 [coll]
  (rand-nth coll))

;; chooses a random element from xs, where each element of xs contains [probability value]
(defn choose-1-weighted [xs]
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

;; randomly either returns the given argument or ""
(defn choose-opt [x]
  (if (randy 0.33) x "") )

;; Returns the number of syllables for the given word
(defn get-syllables [word]
  (let [lookup (get all-words (str/lower-case word))]
    (if (nil? lookup) 0 lookup)
    )
  )

;; Count the number of syllables in a sentence (a list of words)
(defn count-sentence-syllables [words]
  (reduce + 0 (map get-syllables words)))

;; applies a grammar recipe
(defn assemble [recipe]
  (let []
    (cond
      (not (coll? recipe))                  recipe
      (= (first recipe) :choose-1)          (recur (apply choose-1 (rest recipe)) )
      (= (first recipe) :choose-1-weighted) (recur (apply choose-1-weighted (rest recipe)) )
      (= (first recipe) :opt)               (recur (choose-opt (rest recipe)) )
      :default                              (map assemble  recipe)
      )
    )
  )

(defn starts-with-vowel [word]
  (contains? #{"a" "e" "i" "o" "u"} (str/lower-case (first word))))

(defn remove-blanks [line]
  (filter #(not (empty? %)) line))

;; Change 'a' to 'an' if the next word starts with a vowel
(defn a-to-an [line]
  (let [
        n (count line)
        ]
    (map-indexed (fn [i val]
                   (let
                       [
                        done (>= i (dec n))
                        next-val (if done
                                   nil
                                   (nth line (inc i)))
                        ]
                     (if (= "a" val)
                       (if (starts-with-vowel next-val)
                         "an"
                         "a")
                       val)
                     )) line )
    ))

;; Capitalizes a word
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

        ;; capitalize first letter of line
        capitalized (capitalize-line fixed-articles)
        ]
    capitalized
    ))

;; Creates a single line from the given recipe, respecting the syllable target.
(defn create-line [recipe syllable-target]
  (let [raw-line (flatten (assemble recipe))
        clean-line (post-process-line raw-line)
        k (count-sentence-syllables clean-line)]
    ;; brute force: if we didn't hit our syllable target, try again.
    (if (= syllable-target k)
      clean-line
      (recur recipe syllable-target)
      )
    ))

;; Creates a haiku, 3 lines with syllable counts of 5,7,5.
(defn create-haiku []
  (let [
        line1 (create-line line-recipe 5)
        line2 (create-line line-recipe 7)
        line3 (create-line line-recipe 5)
        ]
    (apply println line1)
    (apply println line2)
    (apply println line3)
    ))
