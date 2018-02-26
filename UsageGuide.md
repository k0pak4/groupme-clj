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

## Getting Started
The library is designed to mirror options presented by the [GroupMe API](https://dev.groupme.com/docs/v3).

__All functions__ require you to pass a string representation of your auth token from the GroupMe API, like so:

`(def auth-token "123456789101112131415")`
    
The following API Endpoints and functions are currently supported.

## Users
#### get-current-user  
Retrieves the authenticated user   
__Parameters:__  
* token: your authentication token

__Returns:__ a map of the user's details

#### update-user
Update the authenticated user   
__Parameters:__  
* token: your authentication token
* _Optional_ a map with any of the following: `{:av-url "", :name "", :email "", :zipcode ""}`  
  
__Returns:__ a map of the user's details

#### enable-sms-mode
Enable SMS mode for the user for 1-48 hours
__Parameters:__  
* token: your authentication token
* duration: a number between 1 and 48 hours specifying how long to enable sms mode for   
  
__Returns:__ The status code of the response, 201 indicates success

#### disable-sms-mode
Disable SMS mode for the user
__Parameters:__  
* token: your authentication token
* duration: a number between 1 and 48 hours specifying how long to enable sms mode for   
  
__Returns:__ The status code of the response, 200 indicates success

### Blocks
#### get-blocks
Retrieves all blocks for the given user  
__Parameters:__  
* token: your authentication token
* user-id: your user id  

__Returns:__ a list of blocks you have with other users

#### does-block-exist
Checks whether a block exists between the two given users
__Parameters:__  
* token: your authentication token
* user-id: your user id
* other-user: the other user's id  

__Returns:__ a boolean `true` or `false` on whether a block is present

#### create-block
Creates a block between you and the given user
__Parameters:__  
* token: your authentication token
* user-id: your user id
* other-user: the other user's id  

__Returns:__ The status code of the response, 201 indicates success

#### destroy-block
Destroys a block between you and the given user
__Parameters:__  
* token: your authentication token
* user-id: your user id
* other-user: the other user's id  

__Returns:__ The status code of the response, 200 indicates success
    
## Groups

* get-groups  --  Get the authenticated user's groups
* get-former-groups  --  Get the authenticated user's former groups
* get-group-by-id  --  Get a group by its id
* create-group  --  Create a group with given name and optional arguments
* update-group  --  Update a group by its id with given updates
* destroy-group  --  Destroy a group based on its id
* join-shared-group  --  Join a group you are permitted to join
* rejoin-group   --  Rejoin a group you previously left
* change-group-owners  --  adjust ownership of various groups

### Membership functions

* add-members  --  add members to the given group
* get-add-results  --  see the results of your previous add request
* remove-member  --  remove the specified member from the given group
* update-nickname  --  update your nickname in the specified group

        
## Chats

* get-chats  -- Retrieves the authenticated user's direct message chats.
* get-direct-messages  --  Retrieves direct messages between two users.
* create-direct-message  --  Create a direct message to another user

## Messages
* get-messages  --  Retrieves messages in the specified group
* create-message  --   Creates a message in the specified group
* like-message  --  Like the specified message
* unlike-message  --  Unlike the specifiec message
* get-most-liked-messages  --  Retrieve most liked messages in the specified group
* get-my-likes  --  Retrieves messages you have liked in the specified group
* get-my-liked-messages  --  Retrieves messages of yours that others have liked in the specified group

## Bots
* get-bots  --  Retrieves the user's bots
* create-bot  --  Creates a new bot with the given name in the given group with additional options
* bot-message  --  Send a message as the specified bot with the specified text
* destroy-bot  --  Delete the specified bot
