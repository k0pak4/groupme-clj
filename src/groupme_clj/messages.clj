(ns groupme-clj.messages
  (:require [groupme-clj.utils :as util]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;; Messages ;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-messages
  "Retrieves messages in the given group"
  ([token group-id]
   (let [request (util/build-request (str "/groups/" group-id "/messages") token)
         resp (util/make-request request)]
     (util/extract-content resp)))
  ([token group-id message-id before?]
   (get-messages group-id message-id before? 20))
  ([token group-id message-id before? limit]
   (let [time-str (if before? "before_id=" "since_id=")
         request (util/build-request (str "/groups/" group-id "/messages")
                                     token
                                     time-str message-id)
         resp (util/make-request request)]
     (util/extract-content resp))))

(defn create-message
  "create a message within the given group with the provided text"
  ([token group-id text]
   (create-message token group-id text []))
  ([token group-id text attachments]
   (let [request (util/build-request (str "/groups/" group-id "/messages") token)
         body {"message" {"text" text,
                          "source_guid" (str (System/currentTimeMillis)),
                          "attachments" attachments}}
        resp (util/make-request request "POST" body)]
    (util/extract-content resp))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;; Likes ;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn like-message
  "Like the given message in the given conversation"
  [token conversation-id message-id]
  (let [request (util/build-request (str "/messages/" conversation-id
                                         "/" message-id "/like")
                                    token)
        resp (util/make-request request "POST")]
    (if (= (:status resp) 200)
      (print (str "Successly liked message!")))
    (:status resp)))

(defn unlike-message
  "Unlike the given message in the given conversation"
  [token conversation-id message-id]
  (let [request (util/build-request (str "/messages/" conversation-id
                                         "/" message-id "/unlike")
                                    token)
        resp (util/make-request request "POST")]
    (if (= (:status resp) 200)
      (print (str "Successly unliked message!")))
    (:status resp)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;; Leaderboard Functions  ;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-most-liked-messages
  "Get a list of the most liked messages in the group over the given period"
  [token group-id period]
  (let [request (util/build-request (str "/groups/" group-id "/likes")
                                    token "period=" period)
        resp (util/make-request request)]
    (get (util/extract-content resp) "messages")))

(defn get-my-likes
  "Get a list of messags I have liked in the given group"
  [token group-id]
  (let [request (util/build-request (str "/groups/" group-id "/likes/mine")
                                    token)
        resp (util/make-request request)]
    (get (util/extract-content resp) "messages")))

(defn get-my-liked-messages
  "Get a list of my messages others have liked in the given group"
  [token group-id]
  (let [request (util/build-request (str "/groups/" group-id "/likes/for_me")
                                    token)
        resp (util/make-request request)]
    (get (util/extract-content resp) "messages")))
