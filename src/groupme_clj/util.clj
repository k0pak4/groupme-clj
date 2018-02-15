(ns groupme-clj.util
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(def base-url "https://api.groupme.com/v3")

(defn build-request
  "builds a request with the given token and more parameters"
  [target token & more]
  (let [params (apply concat (interpose ["&"] (partition 2 more)))]
  (str base-url target "?token=" token "&" (apply str params))))

(defn make-request
  "Use clj-http to make the desired request."
  [request & {:keys [params type] :or {params {} type "GET"}}]
  (case type
    "GET" (client/get request params)
    "POST" (client/post request params)
    "DELETE" (client/delete request params)))
  
(defn extract-content
  "Extract the content out of the response body"
  [response]
  (get (json/read-str (:body response))
       "response"))
