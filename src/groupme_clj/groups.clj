(ns groupme-clj.groups
  (:require [groupme-clj.utils :as util]))

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

(defn get-group-by-id
  "Retrieve a groups information based on its unique identifier"
  [token id]
  (let [request (util/build-request (str "/groups/" id) token)
        resp (util/make-request request)]
      (util/extract-content resp)))

(defn create-group
  "Create a group with the given name. Optional arguments include
  a group description"
  [token name {:keys [description image-url share]}]
  (let [request (util/build-request "/groups" token)
        body {"name" name, "description" description,
              "image_url" image-url, "share" share}
        resp (util/make-request request "POST" body)]
    (util/extract-content resp)))

(defn update-group
  ""
  [token]
  )

(defn destroy-group
  "Destroy the given group by its ID"
  [token group-id]
  (let [request (util/build-request (str "/groups/" group-id "/destroy") token)
        resp (util/make-request request "POST")]
    (if (= (:status resp) 200)
      (print (str "Successly deleted group " group-id "!")))))

(defn join-shared-group
  ""
  [token group-id share-token]
  )

(defn rejoin-group
  ""
  [token group-id]
  )

(defn change-group-owners
  ""
  [token owner-requests]
  )


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;; Membership Functions ;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn add-members
  ""
  [token group-id members]
  )

(defn get-add-results
  ""
  [token group-id results-id]
  )

(defn remove-member
  ""
  [token group-id membership-id]
  )

(defn update-nickname
  ""
  [token group-id nickname]
  )
