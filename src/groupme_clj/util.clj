(ns groupme-clj.util
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(def base-url "https://api.groupme.com/v3")

(defn build-request
  "builds a request with the given token and more parameters"
  [target token & more]
  (let [params (apply concat (interpose ["&"] (partition 2 more)))]
  (str base-url target "?token=" token "&" (apply str params))))

(defn handle-error
  "Handle exceptions and print out useful information"
  [exception]
  (print (str "Caught Exception: " exception)))

(defn make-request
  "Use clj-http to make the desired request. If the status code is not 200, catch and serve exception"
  [request params]
  (try (client/get request params)
       (catch Exception e (do (handle-error e)
                              nil))))

(defn extract-content
  "Extract the content out of the response body"
  [response]
  (get (json/read-str (:body response))
       "response"))
