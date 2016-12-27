;;; Vocabulary for random haiku generator
;;;
;;; This vocabulary set relates to Amazon Robotics, but it's easy to customize.
;;;
;;; Programmer: J van Donsel
;;; December, 2016
;;;
(ns haiku.vocab)

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
                     "phone" "wire"  "badge" "face" "file" "house" "rock" "box" "dog" "cat" "time" "cart" "dove" "glove"
                     "shelf" "bug" "team" "dev" "queue" "Mac" "hand" "aisle" "girl" "boy" "plan" "fire"]
                  2 ["robot" "robot" "station" "warehouse" "order" "human" "product" "winter" "summer" "autumn" "picker" "stower" "neighbor"
                     "window" "silence" "apple" "FC" "child" "mirror" "insect" "water"  "service" "server" "zombie" "woman" "person" "sunset"
                     "flower" "poem" "monkey" "eagle" "surprise" "button" "event" "finger" "machine" "resource" "function" "Kindle" "program"
                     "model" "virus" "query" "echo" "image" "system" "carton" "movie" "building" "pallet" "garden" "TT" "pipeline" "present"
                     "coffee" "sticker" "barcode" "laptop" "website" "cable" "worker" "ASIN" "matrix" "scanner" "spirit" "iPad" "tablet"]
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
                          3 ["navigates" "recovers" "emerges" "is coming" ]
                          4 ["equalizes"]
                          })
(def verbs-s-intrans (flatten (vals verbs-s-intrans-map)))

;; singular transitive verbs
(def verbs-s-trans-map {1 ["picks" "brings" "stows" "eats" "runs" "stores" "finds" "searches for" "hears" "sees" "loves" "hates" "breaks" "drinks" "counts"
                           "inspects" "takes" "ships" "scans" "builds" "senses" "lifts" "has" "maps" "tracks" "sends" "plans" "tests" "blocks" "seeks"]
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
(def singleton-nouns-map {1 ["Jeff" "work" "Ops" "the wind" "snow" "air" "code" "peak"]
                          2 ["daylight" "hardware" "software" "data" "sunshine" "sunlight" "storage" "the Web" "AR" "QA" "money"
                             "Java" "Apple" "Bezos" "Kiva" "dunnage" "FedEx" "music" "Kobra" "A-*" "Nike" "Atlas"]
                          3 ["Amazon" "amnesty" "Apollo" "Brazil" "Seattle" "Hercules" "December"]
                          4 ["inventory" "America" "the Internet" "security" ]
                          } )
(def singleton-nouns (flatten (vals singleton-nouns-map)))


;; Irregular singlar->plurals for both nouns and verbs
;; TODO: when pluralized, some of these words change their syllable count.
(def irregular-plurals {
                        "accesses" "access"
                        "activity" "activities"
                        "battery" "batteries"
                        "box" "boxes"
                        "carries" "carry"
                        "child" "children"
                        "company" "companies"
                        "crashes" "crash"
                        "digs out" "dig out"
                        "echo" "echoes" ; noun
                        "echoes" "echo" ; verb
                        "factory" "factories"
                        "family" "families"
                        "has" "have"
                        "is coming" "are coming"
                        "leaf" "leaves"
                        "man" "men"
                        "matrix" "matrices"
                        "person" "people"
                        "pushes" "push"
                        "queries" "query" ; verb
                        "query" "queries" ; noun
                        "queues up" "queue up"
                        "searches for" "search for"
                        "shelf" "shelves"
                        "smells like" "smell like"
                        "thinks about" "think about"
                        "touches" "touch"
                        "virus" "viruses"
                        "watches" "watch"
                        "woman" "women"
                        })

