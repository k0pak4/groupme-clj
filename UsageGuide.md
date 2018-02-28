# groupme-clj Usage Guide

## Table of Contents
1. [Getting Started](#getting-started)
2. [Users](#users)
   - [Blocks](#blocks)
3. [Groups](#groups)
   - [Membership](#membership)
4. [Chats](#chats)
5. [Messages](#messages)
6. [Bots](#bots)
7. [Image Service](#image-service)

## Getting Started
The library is designed to mirror options presented by the [GroupMe API](https://dev.groupme.com/docs/v3).

__All functions__ require you to pass a string representation of your auth token from the GroupMe API, like so:

`(def auth-token "123456789101112131415")`
    
The following API Endpoints and functions are currently supported.

## Users
### get-current-user  
Retrieves the authenticated user   
__Parameters:__  
* token: your authentication token

__Returns:__ a map of the user's details

### update-user
Update the authenticated user   
__Parameters:__  
* token: your authentication token
* _Optional_ a map with any of the following: `{:av-url "", :name "", :email "", :zipcode ""}`  
  
__Returns:__ a map of the user's details

### enable-sms-mode
Enable SMS mode for the user for 1-48 hours  
__Parameters:__  
* token: your authentication token
* duration: a number between 1 and 48 hours specifying how long to enable sms mode for   
  
__Returns:__ The status code of the response, 201 indicates success

### disable-sms-mode
Disable SMS mode for the user  
__Parameters:__  
* token: your authentication token
* duration: a number between 1 and 48 hours specifying how long to enable sms mode for   
  
__Returns:__ The status code of the response, 200 indicates success

## Blocks
### get-blocks
Retrieves all blocks for the given user  
__Parameters:__  
* token: your authentication token
* user-id: your user id  

__Returns:__ a list of blocks you have with other users

### does-block-exist
Checks whether a block exists between the two given users  
__Parameters:__  
* token: your authentication token
* user-id: your user id
* other-user: the other user's id  

__Returns:__ a boolean `true` or `false` on whether a block is present

### create-block
Creates a block between you and the given user  
__Parameters:__  
* token: your authentication token
* user-id: your user id
* other-user: the other user's id  

__Returns:__ The status code of the response, 201 indicates success

### destroy-block
Destroys a block between you and the given user  
__Parameters:__  
* token: your authentication token
* user-id: your user id
* other-user: the other user's id  

__Returns:__ The status code of the response, 200 indicates success
    
## Groups

### get-groups
Get the authenticated user's groups  

__Parameters:__  
* token: your authentication token
* _Optional_ per-page: the amount of results to return in a single request (defaults to 10)
* _Optional_ page: the page number of results (defaults to 1)  

__Returns:__ A list of groups you are currently in

### get-former-groups
Get the authenticated user's former groups  

__Parameters:__  
* token: your authentication token  

__Returns:__ A list of groups you were formerly in

### get-group-by-id
Get a group by its id  

__Parameters:__  
* token: your authentication token
* id: the id of the group you are retrieving  

__Returns:__ The retrieved group

### create-group
Create a group with given name and optional arguments  

__Parameters:__  
* token: your authentication token
* name: the name of the group
* _Optional_ a map with any of the following: `{:description "", :image-url "", :share ""}`  

__Returns:__ The created group  

### update-group
Update a group by its id with given updates  

__Parameters:__  
* token: your authentication token
* group-id: the id of the group to update
* _Optional_ a map with any of the following: `{:description "", :name "", :image-url "", :office-mode false, :share true}`  

__Returns:__ the updated group

### destroy-group
Destroy a group based on its id  

__Parameters:__  
* token: your authentication token
* group-id: the group you wish to destroy

__Returns:__ the status code of the response, 200 indicates success

### join-shared-group 
Join a group you are permitted to join  

__Parameters:__  
* token: your authentication token
* group-id: the id of the group you are trying to join
* share-token: the share url of the group you are trying to join  

__Returns:__ the group you joined

### rejoin-group 
Rejoin a group you previously left  

__Parameters:__  
* token: your authentication token
* group-id: the id of the group you are trying to rejoin  

__Returns:__ the group you rejoined

### change-group-owners
adjust ownership of various groups  

__Parameters:__  
* token: your authentication token
* owner-requests: a list in the format `[{'group_id': 12345, 'owner_id': 67890} ...]`  

__Returns:__ A list of results for each request sent

## Membership functions

### add-members
add members to the given group  

__Parameters:__  
* token: your authentication token
* group-id: the id of the group to add members too
* members: a list of members in the form [{'nickname': "", 'user_id': "", ...} ...]  

__Returns:__ a results id for the add request

### get-add-results
see the results of your previous add request  

__Parameters:__  
* token: your authentication token
* group-id: the group for which results you are checking
* results-id: the id of the results you are checking  

__Returns:__ the results for each add request

### remove-member
remove the specified member from the given group  

__Parameters:__  
* token: your authentication token
* group-id: the id of the group you are removing the member from
* membership-id: the membership id(not user id) of the user you are removing  

__Returns:__ the result status code, 200 indicates success

### update-nickname
update your nickname in the specified group  

__Parameters:__  
* token: your authentication token
* group-id: the id of the group you are changing your nickname in
* nickname: the nickname you want to change to  

__Returns:__ the updated membership and nickname
        
## Chats

### get-chats
Retrieves the authenticated user's direct message chats.  

__Parameters:__  
* token: your authentication token
* _Optional_ per-page: the amount of results to return in a single request (defaults to 10)
* _Optional_ page: the page number of results (defaults to 1)  

__Returns:__ A list of direct message chats you are currently in

### get-direct-messages
Retrieves direct messages between two users.  

__Parameters:__  
* token: your authentication token
* other-user: the id of the user you are checking direct messages with
* _Optional_ message-id: the index of which message to start getting results at
* _Optional_ before?: Do you want messages before the id? If false, does messages since id  

__Returns:__ A list of direct messages between you and other-user

### create-direct-message
Create a direct message to another user  

__Parameters:__  
* token: your authentication token
* other-user: the id of the user you are sending a direct message to
* text: the text of the message 
* _Optional_ attachments: A list of maps, where each map is an attachment. See the [GroupMe API](https://dev.groupme.com/docs/v3#direct_messages_create) for how to format each type of attachment  

__Returns:__ The created message

## Messages
* get-messages  --  Retrieves messages in the specified group
* create-message  --   Creates a message in the specified group
* like-message  --  Like the specified message
* unlike-message  --  Unlike the specifiec message
* get-most-liked-messages  --  Retrieve most liked messages in the specified group
* get-my-likes  --  Retrieves messages you have liked in the specified group
* get-my-liked-messages  --  Retrieves messages of yours that others have liked in the specified group

## Bots
### get-bots
Retrieves the user's bots  
__Parameters:__  
* token: your authentication token  

__Returns:__ A list of bots the user has created

### create-bot
Creates a new bot with the given name in the given group with additional options  
__Parameters:__  
* token: your authentication token
* name: the name of the bot
* group-id: the id of the group the bot will be created in
* _Optional_ a map with any of the following keys `{:avatar-url "", :callback-url "", :dm-notification ""}`  

__Returns:__ The created bot in a map

### bot-message
Send a message as the specified bot with the specified text  
__Parameters:__  
* token: your authentication token
* bot-id: the id of the bot sending the message
* text: the text of the message
* _Optional_ picture-url: A GroupMe image service url that will be attached to the message  

__Returns:__ the status code of the response, 202 indicates success

### destroy-bot
Delete the specified bot  
__Parameters:__  
* token: your authentication token
* bot-id: the id of the bot to be destroyed  

__Returns:__ The status code of the response, 200 indicates success


## Image Service

### upload-image
Upload an image to the GroupMe image service, which allows the image to be sent in messages, etc.  

__Parameters:__  
* token: your authentication token
* image-path: path to the image file on the machine
* content-type: a string of the type of file, e.g. "image/png"  

__Returns:__ The image service url to be used in messages or avatars
