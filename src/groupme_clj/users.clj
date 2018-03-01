(ns groupme-clj.users
  (:require [groupme-clj.utils :as util]))

(defn get-current-user
  "Retrieve the currently authenticated user"
  [token]
  (let [request (util/build-request "/users/me" token)
        resp (util/make-request request)]
    (util/extract-content resp)))

(defn ^:private build-body
  "Build the body of the update-user request"
  [av-url name email zipcode]
  (let [body (if (nil? name) {} {"name" name})
        body (if (nil? email) body (assoc body "email" email))
        body (if (nil? zipcode) body (assoc body "zip_code" zipcode))
        body (if (nil? av-url) body (assoc body "avatar_url" av-url))]
    body))

(defn update-user
  "Updates information about the authenticated user"
  ([token]
   (update-user token {}))
  ([token {:keys [av-url name email zipcode]}]
   (let [request (util/build-request "/users/update" token)
         body (build-body av-url name email zipcode)
         resp (util/make-request request "POST" body)]
     (util/extract-content resp))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;; SMS Mode ;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn enable-sms-mode
  "Enable SMS mode for the authenticated user for the given duration in hours.
  duration must be between 0 and 48 hours"
  [token duration]
  {:pre [(and (> duration 0) (<= duration 48))]}
  (let [request (util/build-request "/users/sms_mode" token)
        body {"duration" duration}
        resp (util/make-request request "POST" body)]
    (if (= (:status resp) 201)
      (print "Successly enabled SMS Mode!"))
    (:status resp)))

(defn disable-sms-mode
  "Disable SMS mode for the authenticated user"
  [token]
  (let [request (util/build-request "/users/sms_mode/delete" token)
        resp (util/make-request request "POST")]
    (if (= (:status resp) 200)
      (print "Successly disabled SMS Mode!"))
    (:status resp)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;  Blocks  ;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-blocks
  "Get a list of contacts you have blocked"
  [token user-id]
  (let [request (util/build-request "/blocks" token "user=" user-id)
        resp (util/make-request request)]
    (get (util/extract-content resp) "blocks")))

(defn does-block-exist
  "Check if a block exists between you and another user"
  [token user-id other-user]
  (let [request (util/build-request "/blocks/between" token "user="
                                    user-id "otherUser=" other-user)
        resp (util/make-request request)]
    (get (util/extract-content resp) "between")))

(defn create-block
  "Create a block between you and another user"
  [token user-id other-user]
  (let [request (util/build-request "/blocks" token "user="
                                    user-id "otherUser=" other-user)
        resp (util/make-request request "POST")]
    (if (= (:status resp) 201)
      (print (str "Successly created block on user " other-user "!")))
    (:status resp)))

(defn delete-block
  "Destroy a block between you and another user"
  [token user-id other-user]
  (let [request (util/build-request "/blocks" token "user="
                                    user-id "otherUser=" other-user)
        resp (util/make-request request "DELETE")]
    (if (= (:status resp) 200)
      (print (str "Successly deleted block on user " other-user "!")))
    (:status resp)))
