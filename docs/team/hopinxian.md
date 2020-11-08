---
layout: page
title: Ho Pin Xian's Project Portfolio Page
---

## Project: VirusTracker

**VirusTracker is a desktop app for generating statistics for Covid-19**, optimized for use via a Command Line Interface (CLI)
while still having the benefits of a Graphical User Interface (GUI). The GUI is created with JavaFX.
VirusTracker helps users manage lists of people, locations and visits relevant to the pandemic.

Given below are my contributions to this project.

* **New Feature**: Add ability for VirusTracker to manage Location data
  * What it does: Allows the user to add, delete and edit location data within VirusTracker.
  VirusTracker also stores and reads data from a json file upon startup and exiting.
  * Justification: This feature allows users to create visits from locations and to generate useful information about location in VirusTracker.
  * Highlights: This enhancement is very code intensive. It requires creation of a number of core classes such as
  Locations, UniqueLocationList, LocationBook to allow VirusTracker to manage location data correctly.
  The PR exceeding 2 thousand  lines can be seen [here](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/69).
* **New Feature**: Add ability to use either index or Ids when referring to person or location
  * What it does: Allows users to use either index or Id to uniquely identify a specific location or person when entering a command.
  Users will need to input a unique Id to VirusTracker when adding person and location data. Relevant PRs are
  [here](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/168) and [here](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/206).
  * Justification: This creates a user-friendly experience since indexes and Ids have their separate advantages.
  * Highlights: All commands that need users to identify a specific person or location, have this feature.
  I created the IndexIdPair class as an abstraction to allow easy scaling of this feature to other commands.
  Future commands can use the IndexIdPair class to easily add on this feature. 
* **New Feature**: Add dates to quarantine and infection statuses:
* **New Feature**: Add quarantine status to Person
* **Feature Modification**: Refactor addressbook classes and maintain Code Quality
  * Justification: The purpose of refactoring is to fit all existing functionalities with VirusTracker.
  This creates a more synchronised user experience and makes it easy for future developers to understand the codebase.
  A relevant PR is [here](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/139).
  * Highlights: The main highlight of this modification is that I need to thoroughly read through the entire code base
  of addressbook level 3. This is very time intensive. 
   <div style="page-break-after: always;"></div> 
* **Feature Modification**: Set minimum width and height of GUI
  * Justification: I have set the new minimum width and height such that users will always be able to see all three lists shown.
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=hopinxian&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=hopinxian&tabRepo=AY2021S1-CS2103T-T13-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other)
* **Project management and Team Tasks**:
  * Made releases on GitHub repository `v1.3, v1.3.trial, v1.4.mockExam, v1.4`
  * Add screenshots of VirusTracker to the team document during checking of the app as required by CS2103t
  * Set up github repository webpage and enable assertions for the project
  * Lead the agenda for team meetings and drove the idea creation of the team.
    Some features that I thought of but are implemented by teammates are described below.
    * Generate a list of locations visited by an infected person
    * Generate a list of people in contact with an infected person
    * Delete corresponding visits when person or location is deleted
    * Update corresponding visits when person or location is deleted
  * Refactor addressbook into VirusTracker
  * Came up with the name and idea of the application (VirusTracker) and made the application icon
* **Documentation**:
  * User Guide: 
    * Add a section on `Indexes and Ids`, documentation on `valid formats for command parameters`,
    a section describing the `user interface`, summary of `notations` used
    * Update command descriptions for add, edit, find, delete person commands
    * Add command descriptions for add, edit, delete locations
  * Developer Guide:
    * Add activity and sequence diagrams for edit person to demonstrate use of indexes and Ids
    * Add the section on managing person, location and visits using unique identifiers
    * Add use cases regarding add, edit, delete location commands
* **Review/mentoring contributions**:  
   * I made a total of **68** comments across all PRs and reviewed 66 PRs (more than half of all PRs made). 
   * Here are the links to some notable PRs: [\#133](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/133), [\#278](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/278),
     [\#127](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/127), [\#132](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/132), 
     [\#201](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/201). 
   * Took the initiative to volunteer and assisted Shulong with getting CheckStyle implemented on his IntelliJ
   * Tested for functional bugs and feature flaws in VirusTracker. I found all the bugs that are solved during v1.4 by my teammates.
