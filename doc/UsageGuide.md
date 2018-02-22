# groupme-clj Usage Guide

The library is designed to mirror options presented by the [GroupMe API](https://dev.groupme.com/docs/v3).

All functions require you to pass a string representation of your auth token from the GroupMe API.
    
The following API Endpoints and functions are currently supported.

## Users
* get-current-user  -- Retrieves the authenticated user
* update-user  --  Update the authenticated user with options in a map `{:name "", :email "", :email "", :zipcode ""}`
* enable-sms-mode  --  Enable SMS mode for the user for 1-48 hours
* disable-sms-mode --  Disable SMS mode for the user
    
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
