# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## v0.2.0
- Adds changelog to document changes across versions
- Increases README documentation
- Increases Contributing documentation
- Adds API support for the following **chats (direct message)** functions:
  - Retrieve direct messages between two users
  - Create a direct message to another user
- Adds API support for the following **message** functions:
  - Retrieve messages for a specific group
  - Create a message in a group
  - Like a specific message (in group or direct message)
  - Unlike a specific message (in group or direct message)
- Adds API support for the folowing **leaderboard** functions:
  - Retrieve the most liked messages in a specific group
  - Retrieve the messages you have liked in a specific group
  - Retrieve your messages which other people have liked in a specific group
  
## v0.1.0	
- Initial release
- Adds API support for the following **group** functions:
  - Retrieving groups
  - Retrieving former groups
  - Retrieving a group by its ID
  - Creating a group
  - Updating a group's information
  - Destroying a group
  - Joining a shared group
  - Rejoining a group you previously left
  - Changing the owners of a given group
- Adds API support for the following **user** functions:
  - Retrieving the current user
  - Updating a user's information
  - Enabling SMS mode
  - Disabling SMS mode
- Adds API support for the following **membership** functions:
  - Adding members to a specific group
  - Retrieving results of a previous add (Add requests are asynchronous)
  - Removing members from a specific group
  - Updating your nickname in a specific group
- Adds API support for the following **chats (direct message)** functions:
  - Retrieving a user's direct message chats
