(ns groupme-clj.groups
  (:require [groupme-clj.utils :as util]))

(defn get-groups
  "Retrieve the users active groups"
  ([token]
   (get-groups token 10))
  ([token per-page]
   (get-groups token per-page 1))
  ([token per-page page]
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


(defn ^:private build-update-body
  "Build the body of the update-group request"
  [name description image-url office-mode share]
  (let [body (if (nil? name) {} {"name" name})
        body (if (nil? description) body (assoc body "description" description))
        body (if (nil? image-url) body (assoc body "image_url" image-url))
        body (if (nil? office-mode) body (assoc body "office_mode" office-mode))
        body (if (nil? share) body (assoc body "share" share))]
    body))

(defn update-group
  "Update fields of the given group"
  [token group-id {:keys [name description image-url office-mode share]}]
  (let [request (util/build-request (str "/groups/" group-id "/update") token)
        body (build-update-body name description image-url office-mode share)
        resp (util/make-request request "POST" body)]
    (util/extract-content resp)))

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
    (get (util/extract-content resp) "results")))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;; Membership Functions ;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn add-members
  "Add the supplied members to the given group.
  member objects must be of the form [{'nickname': '', ...} ...]"
  [token group-id members]
  (let [request (util/build-request (str "/groups/" group-id "/members/add")
                                    token)
        body {"members" members}
        resp (util/make-request request "POST" body)]
    (util/extract-content resp)))

(defn get-add-results
  "Retrieve the results of the previous add operation by result-id"
  [token group-id results-id]
  (let [request (util/build-request
                 (str "/groups/" group-id "/members/results/" results-id)
                 token)
        resp (util/make-request request "GET")]
    (util/extract-content resp)))

(defn remove-member
  "Remove a member from the given group. The membership id is not the user id, but specific to the group"
  [token group-id membership-id]
  (let [request (util/build-request
                 (str "/groups/" group-id "/members/" membership-id "/remove")
                 token)
        resp (util/make-request request "POST")]
    (if (= (:status resp) 200)
      (print (str "Successly removed member from group " group-id "!")))
    (:status resp)))

(defn update-nickname
  "Change your nickname in the given group. Must be between 1-50 characters"
  [token group-id nickname]
  (let [request (util/build-request (str "/groups/" group-id "/memberships/update")
                                    token)
        body {"membership" {"nickname" nickname}}
        resp (util/make-request request "POST" body)]
    (util/extract-content resp)))
