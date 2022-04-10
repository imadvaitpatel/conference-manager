To run the program, pull the repo and run the main method 
in Main.java.

This program uses a text-based user interface. The user will be asked
questions in order to proceed. 

**RUNNING FOR THE FIRST TIME**

When you begin the program, you will be asked to log in, sign up, or quit.
If there are no .ser files yet (usually on the first execution of the program), then
there will only be one account that has been created: an organizer account with username 
"HeadOrganizer" and password "1234". You must either log in with this account, 
or you can choose to sign up a new account. All accounts created by signing up are 
automatically attendee accounts. 

**DIFFERENT TYPES OF ACCOUNTS**

There are organizer accounts, speaker accounts, attendee accounts, and VIP accounts.

Attendees are able to:  
- sign up for non-VIP events  
- cancel their enrollment in events  
- see events they are currently signed up for  
- send messages to other users and see messages sent to them  
- see a list of all scheduled events  
- see a neatly-formatted HTML file of their schedule

VIPs (which are basically attendees with some extra features) are able to:
- do everything an attendee can
- see a schedule of VIP-only events
- able to sign up for VIP-only events

Speakers are able to:  
- see the events they are hosting  
- automatically message every user attending an event or multiple events hosted by them  
- see a list of all the attendees attending an event hosted by them  
- send messages to other users and see messages sent to them  
- see a list of all scheduled events  

Organizers are able to:  
- send messages to other users and see messages sent to them
- create any kind of account 
- create rooms  
- create events 
- change the capacity of an event after creation
- cancel events 
- see a list of all the speaker accounts  
- see a list of all the rooms  
- see a list of all the accounts (users, speakers, and organizers)  
- see a list of all scheduled events   
- see some interesting statistics about events, rooms, or users
- change/reassign the speaker of "talk" events after creation  
- add/remove speakers to "discussion" events after creation
- automatically message all speakers
- automatically message all users (users, speakers, and organizers) 

Once an account is created, the "type" of the account cannot be changed. Organizers
are able to create accounts of any type (including other organizers). Since all accounts
created from siging up are attendee accounts, you will have to create other types of accounts 
as an organizer.

**CREATING EVENTS**

There are 3 types of events: Parties (0 speakers), Talks (exactly 1 speaker), and Discussions(multiple speakers).
All events last 1 hour. Events cannot be created without a room to hold them and a speaker to host the event (if the event
is not a party). So, you will have to create an available room and potentially a speaker before creating
an event. Organizers are able to change the speakers of Talks and Discussions later.

Don't worry if you forget, there is an option to cancel (see below) at any point in the process. While creating
the event, you will be asked several questions about features the room should have. This will not restrict your choice
of room for the event, but based on your answers you will be suggested a list of rooms.

**MESSAGING**

Every user (with any type of account) can message another individual user. Messaging starts with creating
a new thread between a pair of users. Then, each user will be able to see the message history
of the thread. Within each thread, users can do the following to a message:
- archive/unarchive
- mark as unread
- delete (users can only delete their own messages)

**VIEWING SCHEDULE**

Every user can see a HTML document of their schedule. In the main menu, select the option 
"Save Schedule", and then you will be asked to enter a file path . Specify the exact 
file location (e.g. C:\Users\CSC207)instead of using a relative filepath (sometimes INTELLIJ
does not recognize relatvie file paths). A HTML file of the user's schedule will be generated 
and saved at that file location.

**CANCELLING/LOGGING OUT**

If at any point in the program you would like to "go back", enter the string "\cancel". 
This should take you back to the main menu of the logged in account. In the main menu,
you are also able to "sign out". This will take you to the log in prompt and you will be 
able to log in with a different account. In the log in prompt, you can "quit" to end the program.
There is no need at any point to manually stop the program - instead users should be "signing out"
and "quitting".
About
No description, website, or topics provided.
Topics
Resources
 Readme
Stars
 0 stars
Watchers
 1 watching
Forks
 0 forks
Releases
No releases published
Create a new release
Packages
No packages published
Publish your first package
Languages
Java
100.0%
© 2022 GitHub, Inc.
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About
