---
layout: page
title: Low Siang Ern's Project Portfolio Page
---

## Project: VirusTracker

**VirusTracker** is a **desktop app** for generating statistics for Covid-19, optimized for use via a **Command Line Interface** (CLI) while still having the benefits of a **Graphical User Interface** (GUI).
The GUI is created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to import/export data from/to CSV files.
  * What it does: allows the user to import and export the data (people, locations, visits) stored within VirusTracker from and to a CSV file. 
  * Justification: This feature allows the user to make a transition from Excel files to VirusTracker by providing a way for them to read the data within the Excel files
  into VirusTracker. Furthermore, allowing the user to do importing/exporting of data through the use of a command is more user-centric compared to letting them copy the
  json data files.
  * Highlights: This enhancement requires modifying the application to allow it to read and write to CSV files. It required an in-depth understanding of Java file reader and writer classes.
   The format required for the CSV files was also a serious design consideration as I had to balance between making the CSV files easy to be processed by VirusTracker and also making it easy to convert
   the data stored in the user's current Excel files to the required format.

* **New Feature**: Added an attribute to track a person's infection status.
  * Justification: As VirusTracker tracks the interactions between infected people and the places they visit, it was necessary to modify the existing Person class to store the infection status of a person 
  as well.
  
* **New Class**: Created an InfoHandler class to handle the information stored in the model.
  * What it does: It provides the team with a class which has the ability to manipulate read-only data from the model without being worried that the data would be modified.

* **New Class**: Created a DataGenerator class to help parse the attributes from CSV files and to convert the attributes to Strings that were CSV compatible. 
  * What it does: It serves as an facade for commands to handle CSV related commands.
  
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=siangernlow&tabRepo=AY2021S1-CS2103T-T13-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code)

 <div style="page-break-after: always;"></div>
 
* **Project management**:
  * Set up the GitHub team organisation
  * Managed releases `v1.1` - `v1.4` (4 releases) on GitHub

* **Enhancements to existing features**:
  * Updated the Model UML diagram of the developer guide [\#181](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/181)
  * Wrote additional tests for existing features to increase coverage from 78% to 84% [\#220](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/220)

* **Documentation**:
  * Added page breaks for both User Guide and Developer Guide to ensure proper formatting when converted to PDF. [\#289](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/289)
  * User Guide:
    * Added documentation for the features `list l/high-risk-locations` and `list l/stats`. [\#21](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/21)
    * Adapted existing AB3 user guide markdown file to VirusTracker by making the user guide relevant to VirusTracker. [\#86](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/86)
  * Developer Guide:
    * Added user stories and use cases for `infected`, `high-risk-locations` and `stats` [\#28](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/28)
    * Adapted existing AB3 developer guide markdown file to VirusTracker by removing irrelevant use cases and user stories, and adding those related to VirusTracker. [\#84](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/84) 
    * Added documentation for the features `addFromCsv` and `exportToCsv`. [\#181](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/181), [\#201](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/201) 
  * AboutUs:
    * Modified the template About Us file to include the names of my team members and their GitHub repos. [\#88](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/88) 
  * General Cleanup of documentation:
    * These PRs fix formatting of existing documentation
    * [\#160](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/160), [\#291](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/291)
* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#53](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/53), [\#126](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/126),
   [\#132](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/132), [\#206](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/206)
  * Fixed issues and bugs found by fellow coursemates: [\#260](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/260), [\#271](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/271),
   [\#275](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/275), [\#277](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/277), [\#281](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/281)
