(ns groupme-clj.group
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [groupme-clj.util :as util]))

(defn get-group-by-id
  "Retrieve a groups information based on its unique identifier"
  [token id]
  (let [request (util/build-request (str "/groups/" id) token)
        resp (util/make-request request)]
      (util/extract-content resp)))

(defn get-groups
  "Retrieve the users active groups"
  [token & {per-page 10}]
  (let [request (util/build-request "/groups" token "per_page=" per-page)
        resp (util/make-request request)]
      (first (util/extract-content resp))))
