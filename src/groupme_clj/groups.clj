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
      (print (str "Successly deleted group " group-id "!")))
    (:status resp)))

(defn join-shared-group
  "Join a shared group with the requested id and required share token"
  [token group-id share-token]
  (let [request (util/build-request (str "/groups/" group-id
                                         "/join/" share-token)
                                    token)
        resp (util/make-request request "POST")]
    (util/extract-content resp)))

(defn rejoin-group
  "Rejoin a group that you previously removed yourself from"
  [token group-id]
    (let [request (util/build-request "/groups/join" token)
        body {"group_id" group-id}
        resp (util/make-request request "POST" body)]
    (util/extract-content resp)))

(defn change-group-owners
  "Submit requests to change group owners of groups.
  Requests must be in format: [{'group_id': 12345, 'owner_id': 67890} ...]"
  [token owner-requests]
  (let [request (util/build-request "/groups/change_owners" token)
        body {"requests" owner-requests}
        resp (util/make-request request "POST" body)]
    (util/extract-content resp)))

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
