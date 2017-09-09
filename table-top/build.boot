(set-env!
 :source-paths    #{"sass" "src/cljs"}
 :resource-paths  #{"resources"}
 :dependencies '[[org.clojure/clojure       "1.9.0-alpha20" :scope "provided"]
                 [adzerk/boot-cljs          "2.0.0"         :scope "test"]
                 [adzerk/boot-cljs-repl     "0.3.3"         :scope "test"]
                 [adzerk/boot-reload        "0.5.1"         :scope "test"]
                 [pandeiro/boot-http        "0.8.3"         :scope "test"]
                 [com.cemerick/piggieback   "0.2.1"         :scope "test"]
                 [org.clojure/tools.nrepl   "0.2.13"        :scope "test"]
                 [weasel                    "0.7.0"         :scope "test"]
                 [crisptrutski/boot-cljs-test "0.3.0"       :scope "test"]
                 [deraen/boot-sass  "0.3.0"                 :scope "test"]
                 [org.slf4j/slf4j-nop  "1.7.21"             :scope "test"]
                 [org.omcljs/om "1.0.0-beta1"]
                 [org.clojure/clojurescript "1.9.908"]
                 [proto-repl "0.3.1"]
                 [proto-repl-charts "0.3.2"]])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[pandeiro.boot-http    :refer [serve]]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]]
 '[deraen.boot-sass :refer [sass]])

(deftask build
  "This task contains all the necessary steps to produce a build
   You can use 'profile-tasks' like `production` and `development`
   to change parameters (like optimizations level of the cljs compiler)"
  []
  (comp (cljs)
        (sass)))

(deftask run
  "The `run` task wraps the building of your application in some
   useful tools for local development: an http server, a file watcher
   a ClojureScript REPL and a hot reloading mechanism"
  []
  (comp (serve)
        (watch)
        (cljs-repl)
        (reload)
        (build)))

(deftask production []
  (task-options! cljs {:optimizations :advanced}
                      sass   {:output-style :compressed})
  identity)

(deftask development []
  (task-options! cljs {:optimizations :none}
                 reload {:on-jsload 'table-top.app/init})
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (run)))


(deftask testing []
  (set-env! :source-paths #(conj % "test/cljs"))
  identity)

;;; This prevents a name collision WARNING between the test task and
;;; clojure.core/test, a function that nobody really uses or cares
;;; about.
(ns-unmap 'boot.user 'test)

(deftask test []
  (comp (testing)
        (test-cljs :js-env :phantom
                   :exit?  true)))

(deftask auto-test []
  (comp (testing)
        (watch)
        (test-cljs :js-env :phantom)))
