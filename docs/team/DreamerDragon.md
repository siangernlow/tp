---
layout: page
title: DreamerDragon's Project Portfolio Page
---

## Project: VirusTracker

VirusTracker is a desktop application used for data managements related to Covid-19. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the visit property.
  * What it does: Enables VirusTracker to keep track of the visit entries during Covid-19. This Visit property entails 
  the person of visit, the location of visit and the date that the person visited the place.
  * Justification: 
  * Highlights: This feature allows VirusTracker to generate useful lists such as list of contacts, if the person visits the same location with the infected on the same date,
                and this can be checked by comparing the two visits inside the visit list.
                It required an in-depth analysis of design alternatives. The implementation too was challenging as it wraps around other existing properties.

* **New Feature**: Added the ability to add visits to VirusTracker.
  * What it does: Enables VirusTracker to keep track of the visit entries during Covid-19. This Visit property entails 
  the person of visit, the location of visit and the date that the person visited the place.
  * Justification: 
  * Highlights: This feature allows VirusTracker to generate useful lists such as list of contacts, if the person visits the same location with the infected on the same date,
                and this can be checked by comparing the two visits inside the visit list.
                It required an in-depth analysis of design alternatives. The implementation too was challenging as it wraps around other existing properties.
* **New Feature**: Added a history command that allows the user to navigate to previous commands using up/down keys.

* **Code contributed**: [RepoSense link]()

* **Project management**:
  * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
  * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
  * User Guide:
    * Added documentation for the features `delete` and `find` [\#72]()
    * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
  * Developer Guide:
    * Added implementation details of the `delete` feature.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
  * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
  * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
  * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* **Tools**:
  * Integrated a third party library (Natty) to the project ([\#42]())
  * Integrated a new Github plugin (CircleCI) to the team repo

* _{you can add/remove categories in the list above}_
