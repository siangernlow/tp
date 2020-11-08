---
layout: page
title: DreamerDragon's Project Portfolio Page
---

## Project: VirusTracker

**VirusTracker** is a **desktop app** for generating statistics for Covid-19, optimized for use via a **Command Line Interface** (CLI) while still having the benefits of a **Graphical User Interface** (GUI).
 The GUI is created with JavaFX. It is written in Java, and has about 10 kLoC.

* **New Feature**: Added the visit property.
  * What it does: Enables VirusTracker to keep track of the visit entries during Covid-19.
  * Justification:  This Visit property entails the person of visit, the location of visit and the date that the person visited the place.
  * Highlights: This feature allows VirusTracker to generate useful lists such as list of contacts, if the person visits the same location with the infected on the same date,
                and this can be checked by comparing the two visits inside the visit list.
                It required an in-depth analysis of design alternatives. The implementation too was challenging as it wraps around other existing properties.

* **New Feature**: Added the ability to add visits to VirusTracker.
  * What it does: This feature allows users to add visits to the VirusTracker by making use of the index from the person and location list, since 
  visit depends on person, location and date.
  * Highlights: It required an in-depth analysis of design alternatives. 
  
* **New Feature**: Added the ability to delete visits to VirusTracker.
  * What it does: This feature allows users to delete a specific visit from the VirusTracker by making use of the index from visit list. Alternatively,
  it allows users to choose to delete visits identified using an intended date, all the visits before and on the date will be removed from the visit list.
  * Justification: It enables users to deleteVisit in two alternative ways depending on their needs.

* **New Feature**: Added the ability to update visit list when a person is edited.
  * What it does: When a person is edited by user, the person's information can also be updated inside the visit list. 
  All the relevant visit should contain the person with the most recent information. 
  
 <div style="page-break-after: always;"></div>
 
* **Code contributed**: 
[RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=DreamerDragon&tabRepo=AY2021S1-CS2103T-T13-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * Contributed to `v1.1` - `v1.4` (4 releases) on GitHub.

* **Enhancements to existing features**:
  * Updated the GUI help window message and url links(Pull requests [\#214](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/214))
  * Wrote additional tests for existing features to increase coverage (Pull requests [\#175](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/175))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `adding a visit`, `deleting a visit` and `delete visits by date` (Pull requests [\#166](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/166), [\#269](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/269))
  * Developer Guide:
    * Added implementation details of the `deleteVisits` feature. (Pull requests [\#192](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/192/files))
    * Added user stories and use cases related to person(Pull requests [\#269](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/269))
    * Added user stories and use cases related to visit(Pull requests [\#166](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/166), [\#269](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/269))
    * Adapted existing AB3 developer guide markdown file to VirusTracker by removing irrelevant component demonstrations, and adding those related to VirusTracker. (Pull requests[\#294](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/294))
