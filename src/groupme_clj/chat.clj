(ns groupme-clj.chat
  (:require [groupme-clj.util :as util]))

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
