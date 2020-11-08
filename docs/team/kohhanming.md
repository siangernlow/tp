---
layout: page
title: Koh Han Ming's Project Portfolio Page
---

## Project: VirusTracker

VirusTracker is a desktop application to generate Covid-19 related statistics.

Given below are my contributions to the project.

* **New Feature**: Add functionality to generate locations that an infected person has been to.
  * What it does: It displays all the locations an infected person has been to within 2 weeks of the current date.
  (Pull requests [#132](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/132)
  , [#152](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/152)
  , [#278](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/278))
  * Justification: This enables users to view the locations that might require additional quarantine measures.

* **New Feature**: Add functionality to generate people that an infected person has been in contact with.
  * What it does: It displays all the people that visited a location on the same day infected person had been to that same location. An infected person's visits are only tracked for 2 weeks prior the current date.
  (Pull requests [#134](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/134), [#152](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/152), [#278](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/278))
  * Justification: This enables users to view the people who might need to be tested or issued stay home notices.

* **Feature Modification**: Updated the GUI to accommodate list of people, list of locations and list of visits.
  * What it does: It displays all 3 lists on the same screen side by side.
  (Pull requests [#111](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/111)
  , [#208](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/208)
  , [#280](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/280))
  * Justification: The original addressbook only had GUI for a list of people. However, our program required it to be
   able to handle 3 lists as we needed to keep track of people, locations and visits. Furthermore, there are several
   commands that require references to more than one list. Hence, I displayed all 3 lists on the same screen.
 
* **Code contributed**: [https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=kohhanming]()

* **Project management and Team Tasks**:
  * Contributed to `v1.1` - `v1.4` (4 releases) on GitHub. 
  * Re-designed the AddressBook GUI to better fit VirusTracker.
    * We realised that the dark theme of AddressBook was not a suitable aesthetic for VirusTracker. We decided that it
    should look more clinical to fit the medical context VirusTracker will be used in. Hence, I came up with mock-ups
    of a new GUI design using the mock-up software Figma to allow the team to figure out the direction of our aesthetic.
    Thereafter, I implemented it within VirusTracker itself. (Pull request
    [#208](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/208)) 
  * Designed the VirusTracker banner and cleaned up the logo 
    * The logo was originally of a very low resolution so I recreated the entire logo in Photoshop
    to create clean edges. Bezier curves were also used to prevent loss of resolution when scaling the image.
  

* **Documentation**:
  * User Guide:
    * Added documentation for the features `generateLocations` and `generatePeople` [\#72]()
  * Developer Guide:
    * Added implementation details of the `generateLocations` and `generatePeople` features.
    * Added use cases for the `generateLocations` and `generatePeople` features.


* **Review Contributions**:
  * 



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

