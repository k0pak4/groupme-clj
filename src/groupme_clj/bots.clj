(ns groupme-clj.bots
  (:require [groupme-clj.utils :as util]))

(defn get-bots
  "Retrieve a list of the users bots"
  [token]
  (let [request (util/build-request "/bots" token)
        resp (util/make-request request)]
    (util/extract-content resp)))

(defn create-bot
  "Create a bot with the specifiec name in the specified group.
  Additional options are optional"
  ([token name group-id]
   (create-bot token name group-id {}))
  ([token name group-id {:keys [avatar-url callback-url dm-notification]}]
   (let [request (util/build-request "/bots" token)
         body {"bot" {"name" name, "group_id" group-id,
                      "avatar_url" avatar-url,
                      "callback_url" callback-url,
                      "dm_notification" dm-notification}}
         resp (util/make-request request "POST" body)]
    (util/extract-content resp))))

(defn bot-message
  "Have the specificied bot send a message to its group.
  Picture messages are currently unsupported."
  [token bot-id text]
  (let [request (util/build-request "/bots/post" token)
        body {"text" text, "bot_id" bot-id}
        resp (util/make-request request "POST" body)]
    (if (= (:status resp) 202)
      (print (str "Successly sent message from bot " bot-id "!")))
    (:status resp)))

(defn destroy-bot
  "Destroy the specified bot"
  [token bot-id]
  (let [request (util/build-request "/bots/destroy" token)
        body {"bot_id" bot-id}
        resp (util/make-request request "POST" body)]
    (if (= (:status resp) 200)
      (print (str "Successly deleted bot " bot-id "!")))
    (:status resp)))
