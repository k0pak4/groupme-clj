(ns groupme-clj.chats
  (:require [groupme-clj.utils :as util]))

(defn get-chats
  "Retrieve a user's direct message chats"
  ([token]
   (get-chats token 10 1))
  ([token per-page]
   (get-chats token per-page 1))
  ([token per-page page]
   (let [request (util/build-request "/chats" token "per_page="
                                     per-page "page=" page)
         resp (util/make-request request)]
     (util/extract-content resp))))

(defn get-direct-messages
  "Retieve direct messages between yourself and the other user"
  ([token other-user]
   (let [request (util/build-request "/direct_messages" token
                                     "other_user_id=" other-user)
         resp (util/make-request request)]
     (util/extract-content resp)))
  ([token other-user message-id before?]
   (let [time-str (if before? "before_id=" "since_id=")
         request (util/build-request "/direct_messages" token
                                       "other_user_id=" other-user
                                       time-str message-id)
         resp (util/make-request request)]
     (util/extract-content resp))))

(defn- create-direct-message
  "Create a direct message to another user.
  Attachments are currently unsupported."
  ([token other-user text]
   (create-direct-message token other-user text []))
  ([token other-user text attachments]
   (let [request (util/build-request "/direct_messages" token)
         body {"direct_message" {"recipient_id" other-user
                                 "source_guid" (str (System/currentTimeMillis)),
                                 "text" text,
                                 "attachments" attachments}}
         resp (util/make-request request "POST" body)]
    (util/extract-content resp))))
