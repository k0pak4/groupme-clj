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
  [token {:keys [av-url name email zipcode]}]
  (let [request (util/build-request "/users/update" token)
        body (build-body av-url name email zipcode)
        resp (util/make-request request "POST" body)]
    (util/extract-content resp)))
