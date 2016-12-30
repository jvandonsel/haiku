;;; Vocabulary for random haiku generator
;;;
;;; Programmer: J van Donsel
;;; December, 2016
;;;
(ns haiku.vocab)

;; Adjectives
(def adjectives-map {
                     ;; 1-syllable words
                     1  ["strange" "green" "hot" "cold" "small" "large" "wide" "light" "strong" "weak" "dark" "black" "white" "glass" "deep" "red" "blue"
                         "green" "quick" "wild" "strange" "fat" "twin" "sweet"]
                     ;; 2-syllable words
                     2 ["happy"   "snowy" "speedy" "noisy" "steady" "heavy" "crazy" "random" "laden" "metal" "mindless" "liquid" "yellow" "naked" "simple"
                        "baby" "mobile" "lazy" "shiny" "orange" "oblique" "conjoined" "double" "agile" "master" "insane" "managed" "sleeping" "giant"
                        "frozen" "streaming" "soggy" "cardboard" "paper"]
                     ;; 3-syllable words
                     3 [ "curious" "amazing" "electric" "enormous" "unladen" "important" "recursive" "frictionless" "nuclear" "atomic" "unstable" "parallel"
                        "linear" "disabled"]
                     ;; 4-syllable words
                     4 ["interesting" "electronic" "impossible"]
                     ;; 5-syllable words
                     5 ["battery-powered"]
                     })
(def adjectives (flatten (vals adjectives-map)))

;; Adverbs
(def adverbs-map {1 ["now"]
                  2 ["alone" "loudly" "at night" "wildly" "deeply" "daily" "in mist"]
                  3 ["silently" "patiently" "seamlessly" "quietly" "in silence"]
                  4 ["furiously"]
                  })
(def adverbs (flatten (vals adverbs-map)))

;; Singular nouns
(def nouns-s-map {
                  1 ["drive" "pod" "floor" "zone"  "thing" "bin" "tray" "cell" "path" "beer" "star" "leaf" "bird" "tree"
                     "noise" "sound" "light" "rain" "ghost" "screen" "sun" "moon" "day" "night" "crow" "man" "friend" "gift" "truck"
                     "phone"  "badge" "face" "file" "house" "rock" "box" "dog" "cat" "cart" "dove" "glove" "cloud" "sky" "cube"
                     "shelf" "bug" "team" "dev" "queue" "Mac" "hand" "aisle" "girl" "boy" "plan" "fire" "map" "sea" "sheep"
                     "cache" "fleet" "child" "job" "task" "log" "hole" "word"]
                  2 ["robot" "robot" "robot" "station" "warehouse" "order" "human" "product" "winter" "summer" "autumn" "picker" "stower" "neighbor"
                     "window" "silence" "apple" "FC"  "mirror" "insect" "water"  "service" "server" "zombie" "woman" "person" "sunset"
                     "flower" "poem" "monkey" "eagle" "surprise" "button" "event" "finger" "machine" "resource" "function" "Kindle" "program"
                     "model" "virus" "query" "echo" "image" "system" "carton" "movie" "building" "pallet" "garden" "TT" "pipeline"
                     "coffee" "sticker" "barcode" "laptop" "website" "cable" "worker" "ASIN" "matrix" "scanner" "spirit" "iPad" "tablet"
                     "charger" "robin" "ocean" "river" "password" "module" "laser" "volume" "cuboid" "browser" "region" "mission" "garden" "android"
                     "AI" "target" "shadow" "blossom" "lily" "timestamp" "alarm"]
                  3 ["battery" "computer"  "database" "customer" "conveyor" "mezzanine" "MMA" "manager" "programmer" "factory" "family" "video"
                     "obstacle" "obstruction" "terminal" "restriction" "area"  "company" "credential" "location" "butterfly"]
                  4 ["operator" "fiducial" "associate" "activity" "datacenter"]
                  })
(def nouns-s (flatten (vals nouns-s-map)))

;; Singular intransitive verbs
(def verbs-s-intrans-map {
                          1 ["picks" "stows" "waits" "hides" "stores" "rests" "jumps" "weeps" "howls" "screams" "beeps" "dies" "plays" "hums" "moves"
                             "blinks" "sings" "stops" "spins" "drifts" "sits" "glides"  "drives" "walks" "stands" "starves" "sleeps" "dreams" "runs"
                             "smiles"]
                          2 ["listens" "enters" "watches" "twinkles" "echoes" "glimmers" "reboots" "travels" "rotates" "dances" "queues up" "crashes" "charges"
                             "sneezes" "daydreams" "flickers" "replans"]
                          3 ["navigates" "recovers" "emerges" "is coming"]
                          4 ["equalizes"]
                          })
(def verbs-s-intrans (flatten (vals verbs-s-intrans-map)))

;; singular transitive verbs
(def verbs-s-trans-map {1 ["picks" "brings" "stows" "eats" "stores" "finds"  "hears" "sees" "loves" "hates" "breaks" "drinks" "counts"
                           "ships" "scans" "builds"  "lifts" "has" "maps" "tracks" "sends" "plans" "tests" "blocks" "seeks"
                           "tastes" "kills" "streams" "helps"]
                        2 ["orders" "awaits" "gathers" "reboots" "pushes" "commands" "rejects" "carries" "controls" "restricts" "constructs" "designs"
                           "programs" "accesses" "infects" "reflects" "supports" "smells of" "programs" "touches" "obstructs" "queries"
                           "digs out" "dreams of" "renders" "inspects" "senses" "surrounds" "attacks" "transcends" "cancels"]
                        3 ["notices" "thinks about" "recovers" "contemplates" "manages" "prohibits" "encounters" "searches for" "delivers"]
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
(def articles-plural-map {1  ["some" "all" "no" "few"]
                          2 ["many"]})
(def articles-plural (flatten (vals articles-plural-map)))

;; Nouns like "daylight" or "Jeff" that don't have plurals and (usually) don't use articles
;; (Is there a real name for this part of speech?)
(def singleton-nouns-map {
                          1 ["Jeff" "work" "Ops"  "snow" "air" "code" "peak" "Prod" "spring"]
                          2 ["daylight" "hardware" "software" "data" "sunshine" "sunlight" "storage" "the Web" "AR" "QA" "money"
                             "Java" "Apple" "Bezos" "Kiva" "dunnage" "FedEx" "music" "Kobra" "A*" "Nike" "Atlas" "Jira" "Joe Q."
                             "lightning" "Europe" "Coral" "the wind" "Brazil" "Python"]
                          3 ["Amazon" "amnesty" "Apollo" "Seattle" "Hercules" "December" "Cerberus" "Alexa" "IAD"]
                          4 ["inventory" "America" "the Internet" "security" ]
                          } )
(def singleton-nouns (flatten (vals singleton-nouns-map)))


;; Irregular singular->plurals for both nouns and verbs
;; FIXME: when pluralized, some of these words change their syllable count, which
;; can result in incorrect final syllable counts in a line.
(def irregular-plurals {
                        "accesses" "access"
                        "activity" "activities"
                        "battery" "batteries"
                        "box" "boxes"
                        "butterfly" "butterflies"
                        "carries" "carry"
                        "child" "children"
                        "company" "companies"
                        "crashes" "crash"
                        "digs out" "dig out"
                        "dreams of" "dream of"
                        "echo" "echoes" ; noun
                        "echoes" "echo" ; verb
                        "factory" "factories"
                        "family" "families"
                        "has" "have"
                        "is coming" "are coming"
                        "leaf" "leaves"
                        "lily" "lilies"
                        "man" "men"
                        "matrix" "matrices"
                        "person" "people"
                        "pushes" "push"
                        "queries" "query" ; verb
                        "query" "queries" ; noun
                        "queues up" "queue up"
                        "searches for" "search for"
                        "shelf" "shelves"
                        "sky" "skies"
                        "smells of" "smell of"
                        "thinks about" "think about"
                        "touches" "touch"
                        "virus" "viruses"
                        "watches" "watch"
                        "woman" "women"
                        "sheep" "sheep"
                        })

;; Words before which we should use "an" instead of "a" even though
;; they don't start with a vowel.
(def use-an #{"MMA" "FC"})
