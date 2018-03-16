(ns groupme-clj.utils-test
  (:require [groupme-clj.utils :as util]
            [clojure.test :refer :all]
            [stub-http.core :refer [with-routes!]]))

(deftest test-build-request
  (let [token "test-api-token"]
    (is (= (util/build-request "" token)
           "https://api.groupme.com/v3?token=test-api-token")
        "Requests can be built with a blank target")
    (is (= (util/build-request "/group" token)
           "https://api.groupme.com/v3/group?token=test-api-token")
        "Requests can be built with just a target")
    (is (= (util/build-request "/group" token "?page=" 1)
           "https://api.groupme.com/v3/group?token=test-api-token&?page=1")
        "Requests can be built with one extra parameter")
    (is (= (util/build-request "/group" token "?page=" 1 "?limit=" 10)
           "https://api.groupme.com/v3/group?token=test-api-token&?page=1&?limit=10")
        "Requests can be built with multiple extra parameter")))

(deftest test-make-image-request
  (with-routes! {{:method :post :path "/image-test"} {:status 200 :content-type "text/plain" :body "posted test data"}}
    (with-redefs [util/image-url uri]
      (is (= (:body (util/make-request (str util/base-url "/image-test") "utils.clj" "image/png"))
             "posted image data")
          "Can send image request with util function"))))

(deftest test-make-request
  (with-routes! {{:method :get :path "/test"} {:status 200 :content-type "text/plain" :body "got test data"},
                 {:method :post :path "/test"} {:status 200 :content-type "text/plain" :body "posted test data"},
                 {:method :delete :path "/test"} {:status 200 :content-type "text/plain" :body "deleted test data"}}
    (with-redefs [util/base-url uri]
      (is (= (:body (util/make-request (str util/base-url "/test")))
             "got test data")
          "Can send get request with util function")
      (is (= (:body (util/make-request (str util/base-url "/test") "POST" {}))
             "posted test data")
          "Can send post request with util function")
      (is (= (:body (util/make-request (str util/base-url "/test") "DELETE"))
             "deleted test data")
          "Can send delete request with util function"))))

(deftest test-extract-content
  (is (= (util/extract-content {:status 200,
                                :body "{\"response\":\"test data\"}"})
         "test data")
      "Content should properly be extracted"))
