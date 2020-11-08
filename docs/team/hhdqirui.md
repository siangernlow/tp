---
layout: page
title: Wu Qirui's Project Portfolio Page
---

## Project: VirusTracker

**VirusTracker** is a **desktop app** for generating statistics for Covid-19, optimized for use via a **Command Line Interface** (CLI) while still having the benefits of a **Graphical User Interface** (GUI).
The GUI is created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to list infected people in VirusTracker.
  * What it does: It displayed all persons whose infection status is true on the GUI.
  * Justification: This feature allows users to list and view all the infected people on the GUI.
  
* **New Feature**: Added the ability to list quarantined people in VirusTracker.
  * What it does: It displayed all persons whose quarantine status is true on the GUI.
  * Justification: This feature allows users to list and view all the quarantined people on the GUI.
  
* **New Feature**: Added the ability to list high risk locations in VirusTracker.
  * What it does: It displayed a number of top most infected locations on the GUI. The number can be specified by the
  user. If the number is not specified, VirusTracker will determine the number using a pre-defined rule.
  * Justification: This feature allows users to list and view high risk locations (i.e. the top few most infected 
  locations) in VirusTracker.

* **New Feature**: Added the ability to automatically delete all visits associated to the location when this location
is deleted by the execution a `deleteLocation` command. This is also known as cascade delete for location. 
  * Justification: This feature saves the trouble for users to delete visits associated with the deleted location 
  manually.

* **New Feature**: Added the ability to automatically delete all visits associated to the person when this person
is deleted by the execution a `deletePerson` command. This is also known as cascade delete for person. 
  * Justification: This feature saves the trouble for users to delete visits associated with the deleted person 
  manually.

* **New Feature**: Added the ability to automatically update all visits associated to the location when this location
is edited by the execution a `editLocation` command.
  * What it does: It iterates through the list of visits and replace the not yet edited location with edited location
  into those visits associated to this location.
  
  <div style="page-break-after: always;"></div>
  
  * Justification: This feature ensures that all visits stored in the visit book are consistent with locations in the
  location book.

* **Enhancements to existing features**: Added a unique identifier field for Person class.
  * Justification: This can allow VirusTracker and users to uniquely identify a person. The unique identification of
  a person is first planned to be used for `getPerson` method in Visit class, which was not carried out. It was then
  used in `addPerson`, `deletePerson` and `editPerson` command.
  
* **Enhancements to existing features**: Added exception handling for date parsing for `addVisit` command
  * Justification: This ensures that the system will not crash when user type in the wrong date in wrong format in
  `addVisit` command.
  
* **Code contributed**: 
[RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=hhdqirui&tabRepo=AY2021S1-CS2103T-T13-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code)
  
* **Project management**:
  * Contributed to `v1.1` - `v1.4` (4 releases) on GitHub.  

* **Documentation**:
  * User Guide:
    * Added documentation for the features `list l/infected` and `list l/quarantined`. [\#157](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/157)
    * Fixed content mistakes. [\#218](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/218)
    * Fixed ordering of UG table of content. [\#204](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/204)
    * Added missing points under `deleteLocation` and `deletePerson`. [\#204](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/204)
  * Developer Guide:
    * Added use cases for infection status and quarantine status [\#76](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/76)
    * Added use cases for view all infected people and quarantined people. [\#157](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/157) 
    * Updated documentation for `list l/high-risk-locations`. [\#185](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/185)
    * Added documentation for `editLocation` and `deleteLocation`. [\#290](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/290)
    * Updated documentation for `editLocation` and `deleteLocation`. [\#301](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/301)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#160](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/160), [\#166](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/166),
  [\#212](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/212)
