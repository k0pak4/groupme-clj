(ns groupme-clj.images
  (:require [groupme-clj.utils :as util]))

(defn upload-image
  "Upload an image to GroupMe's image service"
  [token image-path content-type]
  (-> (util/make-image-request token image-path content-type)
      (get "payload")
      (get "picture_url")))
