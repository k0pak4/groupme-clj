(ns groupme-clj.group
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [groupme-stats-clj.util :as util]))

(defn get-group-by-id
  "Retrieve a groups information based on its unique identifier"
  [token id]
  (let [request (util/build-request (str "/groups/" id) token)
        resp (util/make-request request {:as :json})]
    (if (= resp nil)
      (println "Failed to get group")
      (util/extract-content resp))))

(defn get-groups
  "Retrieve the users active groups"
  [token per-page]
  (let [request (util/build-request "/groups" token "per_page=" per-page)
        _ (println request)
        resp (util/make-request request {:as :json})]
    (if (= resp nil)
      (println "Failed to acquire groups")
      (map (fn [x] [(get x "name") (get x "id")]) (util/extract-content resp)))))

