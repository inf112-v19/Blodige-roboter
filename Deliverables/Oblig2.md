# Task 1

* Name: Blodige roboter
* Teamleader: Kristian
* Account manager: Daniel

## Kompetanse på medlemmer

### Elg

* Knowledge about libgdx
* Experienced with maven
* Kind and helpful

### Rune

* Internet security
* Network protocols
* Knows paint(the software)

### Rikke

* Smart
* Basically good
* Girl on the team

### Kristian

* Real life experience with software development
* Knows sql and databases
* Experience with uml

### Daniel

* Maven
* Markdown
* Git

## Tools

* Github projects
* Maven
* Slack
* Intellij
* Git
* Github wiki (documentation)
* Tiled (dependency)
* Annotations from jetbrains

# Task 2

## Descrition of overall goal

* To be able to play roborally with other people over lan, with all features that are in the original game.

## List of requirements

* Player chooses cards to use from its dealt hand.
* The game follows the gameloop defined in the original game(phases and rounds)
  * Everything happens in the correct order
* Multiplayer over lan
* Small functions work as in the original game(e.g. laser, powercards, life tokens, locking of movementcards when damaged)
* The board interacts on the robot (e.g. assembly lines, holes and lasers).

## Prioritized list of things to do in the first iteration

1. Start the project, show a basic board
2. A robot on the board
3. Control the robot and/or the cards with a gui
4. 5 phaces/rundene, sthe progress of th game

# Task 3 Select and customize a process for the team

* We have decided to not follow one specific project methodology, but to pick elements we likefrom all of them. We are using a board inspired by both Scrum and Kanban to organize ourtasks. The board doesn’t have limitations to how many tasks can be in each column of theboard (Scrum), and we are also able to add tasks to the ToDo-column as they come up and notonly at the end of the iteration (Kanban).When a task is created and assigned to a group member (the developper), anothergroup member is also assigned to the task to write tests. We wanted to do it this way so thatyou don’t write tests for your own code, but your code has to pass someone elses tests. Anotherbenefit is that there are always two pairs of eyes to look for bugs in the code which will(hopefully) prevent most of the bugs from slipping through the testing phase. In addition wewant every push to master to go through a push request that has to be reviewed and acceptedby a third group member. The reviewer will read through the push request and check the coderuns without bugs on his/her computer. If the reviewer finds bugs, ugly code, lackingdocumentation, etc. (s)he won’t fix the problems, but comment in the push request what needsto be fixed and it’s the developers and testers responsibility to read the comment, fix whatneeds to be fixed and ask for a new review.We want to have two meetings per week (one meeting in addition to the group session).The organization of where and when will be done either at the previous meeting, or on Slack. Atmeetings we start with our version of the Scrum “standup”. We will also define tasks and assignthem, redefine and reassign tasks if necessary, and discuss any issues team members mighthave. The team leader (Kristian) is responsible for creating a meeting agenda, but all teammembers can add things they want to discuss to the agenda. In the project wiki we will postnotes of what has been discussed at every meeting.We are aware of the amount of bureaucracy we have prescribed, but we think this will bebeneficial to the project flow and code quality. If we at some point during the process realise thatour method isn’t efficient we will adapt, as both Kanban and Scrum prescribes!
* Testing: 1 developer that implements, someone else makes tests

## Dokumentation

* Write specification in the wiki, explain class structure etc
* Java docs: Functions in classes, how to use a function/class etc

## Organisation

### Meetings

* Tryout communication via Slack, 2 meetings per week, on on group session and another outside
* Meeting agenda is posted when the date of the next meeting is decided, it is everyones job to put things they want/need to discuss in the agenda

### Meeting number 1 ([02.02.2019](https://github.com/inf112-v19/Blodige-roboter/wiki/Meeting-notes_02-02-2019))

* We had a meeting 2. february. We talked about divison of labor and how we felt our methods where working
* Do we need to structure our meetings more? this meeting lasted 1 hour.
* All future meeting notes will be put in the wiki.

### Meeting number 2 ([05.02.2019](https://github.com/inf112-v19/Blodige-roboter/wiki/Meeting-notes_02-02-2019))

* We talket about the delivery of obligatory assigment 2. How the last week had been, and what remained for iteration 1.

### Division of labor

* Divide , redistribute and rebalance task as often as possible, especially with iterations with delivery demands. 
* Everyone need to have an overview over maven, libgdx, git, tiled
* Everyone need to know the game of RoboRally

### Follow-Up

* Issues, commits..
* look at pull requests, reviewer only gives feedback and doesn't change anythin him-/her-self.

### Storing

* Everything on git.

### Description

* Get project and structure up and running
* Everyone in the team needs to know the framework
* Everyone needs to agree about the organization of the team.
