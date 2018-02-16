(ns groupme-clj.group
  (:require [groupme-clj.util :as util]))

(defn get-group-by-id
  "Retrieve a groups information based on its unique identifier"
  [token id]
  (let [request (util/build-request (str "/groups/" id) token)
        resp (util/make-request request)]
      (util/extract-content resp)))

(defn get-groups
  "Retrieve the users active groups"
  ([token]
   (get-groups token 10))
  ([token per-page]
  (let [request (util/build-request "/groups" token "per_page=" per-page)
        resp (util/make-request request)]
    (util/extract-content resp))))

(defn get-former-groups
  "Retrieve the users former groups"
  [token]
  (let [request (util/build-request "/groups/former" token)
        resp (util/make-request request)]
    (util/extract-content resp)))

