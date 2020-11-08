---
layout: page
title: Ho Pin Xian's Project Portfolio Page
---

## Project: VirusTracker

**VirusTracker is a desktop application to generate Covid-19 related statistics.**
While it has a GUI, most of the user interactions happen using a CLI (Command Line Interface).
VirusTracker helps users manage lists of people, locations and visits relevant to the pandemic.

Given below are my contributions to this project.

* **New Feature**: Add ability to add, delete and edit Location data in VirusTracker
  * What it does: Allows the user to add, delete and edit location data within VirusTracker.
  VirusTracker also stores the data into a json file and reads from a json file upon startup and exiting.
  * Justification: This feature allows users to create visits from locations and to generate useful information about location in VirusTracker.
  * Highlights: This enhancement is very code intensive. It requires creation of a number of core classes such as
  Locations, UniqueLocationList, LocationBook to allow VirusTracker to manage location data correctly.
  These classes are consistently used by other statistics related functionalities to generate new information.
  Storage-related classes also have to be created to ensure VirusTracker can read and store location data.
  The amount of code written is similar to the amount of code for person related classes.

* **New Feature**: Add ability to use either index or Ids when referring to person or location
  * What it does: Allows the user to use either the Index or Id to refer to a specific location or person when entering a command.
  * Justification: This creates a more user-friendly experience since users can choose either to refer to the index or Id
  of a person or location. Users may use indexes when the shown list of persons or locations is short, as it is shorter to type and easy to find.
  Users may instead use Ids when the shown list is long, as it is expeceted that users would know the Id of the person
  or location they want to refer to.
  * Highlights: This feature is seen across all commands that need users to input a reference to a specific person or location.
  Examples include all edit, delete commands and generate commands. I created the IndexIdPair class as an abstraction
  to allow easy scaling of this feature to other commands. Future commands that need to refer to a specific location or
  person can use the IndexIdPair class to easily add on this feature. 

* **New Feature**: Add quarantine status to Person
  * What it does: Each person has a quarantine status to indicate whether the person is being quarantined.
  * Justification: VirusTracker should store a quarantine status to keep track of who is being quarantined.
  This quarantine status is especially useful for giving user alerts when a person is making a visit despite being quarantined.
  
* **New Feature**: Add dates to quarantine and infection statuses:
  * What it does: Quarantine and infection statuses contain dates which indicate since when the person is infected or quarantined.
  * Justification: This feature supports more detailed information gathering by VirusTracker. Warnings given when adding visits
  are more accurate since they check whether a person is quarantined or infected before the visit is made.
   
 <div style="page-break-after: always;"></div>
  
* **Feature Modification**: Refactor addressbook classes to fit in with VirusTracker
  * What it does: Refactors all classes inherited from addressbook to work with VirusTracker cohesively.
  * Justification: The purpose of refactoring is to fit all existing functionalities with VirusTracker.  
  All persons related features are refactored to fit in cohesively with the other new functionalities
  of VirusTracker. This creates a more synchronised user experience. Some of the modifications include renaming person commands,
  changing error and help messages. In addition, code has been repackaged to make it easier for future developers to
  continue developing VirusTracker. Attributes such as addresses, names and phone numbers are packaged separately. Code quality
  is also maintained by packaging other classes in VirusTracker into appropriate packages.
  * Highlights: The main highlight of this modification is that I need to thoroughly read through the entire code base
  of addressbook level 3. This is a time intensive procedure to ensure that all code classes have been refactored properly.
  Some changes that were made only through intensive searching includes changing logging messages and updating 
  storage names in userPreferences.
  
* **Feature Modification**: Remove tag fields from person
  * What it does: Remove tags from Person
  * Justification: Tags do not serve any purpose inside VirusTracker. They have thus been removed to make VirusTracker
  more specialised and simpler for the intended users.
  
* **Feature Modification**: Set minimum width and height  
  * What it does: Increase the minimum width and height of the application GUI
  * Justification: VirusTracker shows three lists simultaneously. The previous minimum width and height allows users to reduce
  the size such that the lists are clipped. The new minimum width and height is set so that this cannot happen, which
  improves user experience.
  
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=hopinxian)

* **Project management and Team Tasks**:
  * Made releases on GitHub repository `v1.3, v1.3.trial, v1.4.mockExam`
  * Add screenshots of VirusTracker to the team document during checking of the app as required by CS2103t
  * Set up github repository webpages
  * Enable assertions for the project
  * Lead the agenda for team meetings and drove the idea creation of the team. Some of the features that I thought of are described below.
    These features are implemented by team members.
    * Generate a list of locations visited by an infected person
    * Generate a list of people in contact with an infected person
    * Delete visits involving a specific person or location when person or location is deleted
    * Update visits involving a specific person or location when person or location is deleted
  * Made the application icon
  * Refactor addressbook into VirusTracker
  * Came up with the name of the application (VirusTracker)

* **Documentation**:
  * User Guide: 
    * Add a descriptive section on Indexes and Ids
    * Add documentation on valid formats
    * Add notations summary
    * Update command descriptions for add, edit, find, delete person commands
    * Add command descriptions for add, edit, delete locations
  * Developer Guide:
    * Add activity and sequence diagrams for edit person to demonstrate use of indexes and Ids
    * Add the section on managing person, location and visits using unique identifiers
* **Review/mentoring contributions**:  
   * I made a total of **68** comments across all PRs. I have reviewed 66 PRs which is more than half of all PRs made. 
   * Here are the links to some notable PRs that were made: [\#133](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/133), [\#278](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/278),
     [\#127](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/127), [\#132](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/132), 
     [\#201](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/201), 
   * Took the initiative to volunteer and assisted Shulong with getting CheckStyle implemented on his IntelliJ
   * Tested for functional bugs and feature flaws in VirusTracker. I found all the bugs that are solved during v1.4 by my teammates.
