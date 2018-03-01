(ns groupme-clj.utils
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(def base-url "https://api.groupme.com/v3")

(defn build-request
  "builds a request with the given token and more parameters"
  [target token & more]
  (let [params (apply concat (interpose ["&"] (partition 2 more)))]
  (str base-url target "?token=" token "&" (apply str params))))

(defn make-image-request
  "sends an image to GroupMe image service"
  [token image content-type]
  (-> (client/post "https://image.groupme.com/pictures"
                   {:headers {"X-Access-Token" token
                              "Content-Type" content-type}
                    :body (clojure.java.io/file image)})
      :body
      json/read-str))

(defn make-request
  "Use clj-http to make the desired request."
  ([request]
   (make-request request "GET"))
  ([request http-type]
   (make-request request http-type {}))
  ([request http-type body]
   (case http-type
     "GET" (client/get request)
     "POST" (client/post request {:body (json/write-str body)
                                  :content-type :json})
     "DELETE" (client/delete request))))
  
(defn extract-content
  "Extract the content out of the response body"
  [response]
  (get (json/read-str (:body response))
       "response"))
