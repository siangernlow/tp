---
layout: page
title: Koh Han Ming's Project Portfolio Page
---

## Project: VirusTracker

**VirusTracker** is a **desktop app** for generating statistics for Covid-19, optimized for use via a **Command Line Interface** (CLI) while still having the benefits of a **Graphical User Interface** (GUI).
The GUI is created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added functionality to generate locations that an infected person has been to.
  * What it does: It displays all the locations an infected person has been to within 2 weeks of the current date.
  (Pull requests [#132](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/132)
  , [#152](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/152)
  , [#278](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/278))
  * Justification: This enables users to view the locations that might require additional quarantine measures.

* **New Feature**: Added functionality to generate people that an infected person has been in contact with.
  * What it does: It displays all the people that visited a location on the same day infected person had been to that same location. An infected person's visits are only tracked for 2 weeks prior the current date.
  (Pull requests [#134](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/134), [#152](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/152), [#278](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/278))
  * Justification: This enables users to view the people who might need to be tested or issued stay home notices.

* **Feature Modification**: Updated the GUI to accommodate list of people, list of locations and list of visits.
  * What it does: It displays all 3 lists on the same screen side by side.
  (Pull requests [#111](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/111)
  , [#208](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/208)
  , [#280](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/280))
  * Justification: The original AddressBook only had GUI for a list of people. However, our program required it to be
   able to handle 3 lists as we needed to keep track of people, locations and visits. Furthermore, there are several
   commands that require references to more than one list. Hence, I displayed all 3 lists on the same screen.
 
   <div style="page-break-after: always;"></div>
 
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
    * Added documentation for the features `generateLocations` and `generatePeople`.
    (Pull requests [#165](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/165)
    , [#292](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/292))
  * Developer Guide:
    * Added implementation details of the `generateLocations` and `generatePeople` features.
    (Pull request [#165](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/165))
    * Added use cases for the `generateLocations` and `generatePeople` features.
    (Pull request [#165](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/165))
    * Added design details for the updated GUI. 
    (Pull requests [#194](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/194)
    , [#292](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/292))
    * Added sections to provide a preface, explain how to use the guide and explain notations used
    within the guide. (Pull request [#309](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/309))
    * Fix broken links in the table of contents.
    (Pull request [#317](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/317))

* **Review Contributions**:
  * PRs reviewed (with non-trivial review comments): [#133](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/133)
  , [#138](https://github.com/AY2021S1-CS2103T-T13-1/tp/pull/138)
