---
layout: page
title: User Guide
---

VirusTracker is a **desktop app for generating statistics for Covid-19, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI).
VirusTracker can help you generate various statistics on the pandemic quickly and easily with a few simple commands.

## **Table of Contents**
* Quick Start
* Features
    1. Adding data: `add`
    2. Deleting data: `delete`
    3. Editing data: `edit`
    4. Finding data: `find`
    5. Listing data: `list`
    6. Generating people in contact with an infected person: `generatePeople`
    7. Generating locations an infected person has been to: `generateLocations`
    8. Clearing the current list: `clear`
    9. Viewing help: `help`
    10. Exiting the program: `exit`
* Command Summary
--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `virustracker.jar` from [here](https://github.com/AY2021S1-CS2103T-T13-4/tp/releases). <br>
   _Note: VirusTracker is still a work-in-progress and official releases are not available yet._
   
3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list l/people`** : Lists all people.

   * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a person named `John Doe` to the VirusTracker.

   * **`delete`**`3` : Deletes the 3rd element shown in the current list.

   * **`clear`** : Deletes all entries from VirusTracker.

   * **`exit`** : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* **Data** refers collectively to people, locations and visits unless stated otherwise.
</div>

### Adding data: `add`

To add data to VirusTracker, there are various `add` commands that could be used.

#### Adding a person

Adds a person to VirusTracker.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS q/QUARANTINE_STATUS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 q/true`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 q/false t/criminal`

#### Adding a visit

Adds a visit by the person, location of visit and date of visit

Format: `addVisit PERSON_INDEX LOCATION_INDEX d/DATE`

* Visits are used to track close contacts and to detect if infected/quarantined people visit locations they should not.
* `PERSON_INDEX` refers to the index of the person as viewed from the most recently displayed people list.
* `LOCATION_INDEX` refers to the index of the location as viewed from the most recently displayed location list.
* The indexes **must be positive integers**: 1, 2, 3, …​
* `DATE` refers to the date when the person visited the location

#### Adding a location

Adds a location to VirusTracker.

Format: `addLocation n/NAME a/ADDRESS`

* Locations have an address and a name.
* The `NAME` of the location is defined by the user.
* The `ADDRESS` of the location is the official Singaporean address of the location.
* No duplicate locations will be allowed in the VirusTracker.

Examples:
* `addLocation n/Vivocity a/John street, block 123, #01-01`
* `addLocation n/Betsy Crowe's House a/Newgate Prison`


### Deleting data: `delete`
To delete data from VirusTracker, there are various `delete` commands that could be used.

#### Deleting a person

Deletes the specified person from the people list.

Format: `delete PERSON_INDEX`

* Deletes the person at the specified `PERONS_INDEX`.
* `PERSON_INDEX` refers to the index of the person as viewed from the most recently displayed people list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list l/infected` followed by `delete 2` deletes the 2nd infected person in the displayed people list.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

#### Deleting a location 

Deletes the specified location from the location list.

Format: `deleteLocation LOCATION_INDEX`

* Deletes the location at the specified `LOCATION_INDEX`.
* `LOCATION_INDEX` refers to the index of the location as viewed from the most recently displayed location list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list l/infected` followed by `deleteLocation 2` deletes the 2nd location in the displayed location list.

#### Deleting visits by date

Deletes all visits before the date

Format: `deleteVisits d/DATE`

* `DATE` refers to a valid date within the visits list.
* A `DATE` is valid if **at least one** visit contains the specified date.
* All the visits before and including the date will be removed from the visits list.


### Editing data: `edit`
To edit data in VirusTracker, there are various `edit` commands that could be used.

#### Editing a person

Edits an existing person in VirusTracker.

Format: `edit PERSON_INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [q/QUARANTINE_STATUS] [t/TAG]…​`

* Edits the person at the specified `PERSON_INDEX`.
* `PERSON_INDEX` refers to the index of the person as viewed from the most recently displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

#### Editing a location

Edits an existing location in VirusTracker.

Format: `editLocation LOCATION_INDEX [n/NAME] [a/ADDRESS]`

* Edits the location at the specified `LOCATION_INDEX`.
* `LOCATION_INDEX` refers to the index of the location as viewed from the most recently displayed location list.
* The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `editLocation 1 n/NTU a/Bugis street` Edits the name and address of the 1st location to be `NTU` and `Bugis Street` respectively.
*  `editLocation 2 n/NUS` Edits the name of the 2nd location to be `NUS`.

### Finding persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)


### Listing data: `list`

There are a variety of `list` commands that list different types of data.

#### Listing all people 
Format: `list l/people`

* Displays the list of all people currently stored in VirusTracker.

#### Adding a visit 
Adds a visit by the personIndex, locationIndex and date of visit.

Format: `addVisit INDEX INDEX d/DATE`

* The visit is added to the visits list to track close contacts, especially for the infected people.
* First index refers to the Index shown in the people list.
* Second index refers to the Index shown in the location list.
* Index should be the positive integers within the range of its shown list, otherwise exceptions would be thrown.
* Date refers to the particular date the person has visited the location.
* Date format should follow "yyyy-MM-dd", otherwise exceptions would be thrown.

Examples:
* `addVisit 1 1 d/2020-09-12`
* `addVisit 2 3 d/2020-09-10`

#### Deleting visits using date 

Deletes all visits before and including the date.

Format: `deleteVisits d/DATE`

* Date format should follow "yyyy-MM-dd", otherwise exceptions would be thrown.
* Date corresponds to the dates that exist in the visits list, otherwise it will be invalid.
* All the visits before and including the date will be removed from the visits list.

Examples:
* `deleteVisits d/2020-09-12`
* `deleteVisits d/2020-09-10`

#### Listing all infected people 
Format: `list l/infected`

* Displays the list of all people that are currently infected.

#### Listing all quarantined people 
Format: `list l/quarantined`

* Displays the list of all people currently in quarantine.

#### Listing all locations
Format: `list l/locations`

* Displays the list of all locations currently stored in VirusTracker.

#### Listing all visits
Format: `list l/visits`

* Displays the list of all visits currently stored in VirusTracker.

* Locations have an address and a name.
* Locations are identified by their name.
* No duplicate locations are allowed in the VirusTracker.

Examples:
* `addLocation n/Vivocity a/John street, block 123, #01-01`
* `addLocation n/Betsy Crowe's House a/Newgate Prison`

### Generating all locations visited by a person: `generateLocations`

Shows a list of locations visited by an infected person in the past 2 weeks. 

Format: `listAllLocationsVisited LOCATION_INDEX`

* Locations listed were visited by the infected person of the index given.
* The result given is a filtered list of locations that the person visited in the past 2 weeks.
* This function can be used to identify locations needing to be disinfected after being visited by an infected person.

### Generating all people in contact with an infected person: `generatePeople`

Shows a list of people who were in contact with an infected person in the past 2 weeks. 

Format `listAllPersonsInContact PERSON_INDEX`

* People listed were in contact with the infected person of the index given.
* The result given is a filtered list of people who visited the same locations as that the infected person in the past 2 weeks.
* This function can be used to identify people who need to be quarantined or issued Stay Home Notices.

#### Listing high risk locations

Lists the locations with high risk of Covid infection.

Format: `list l/high-risk-locations`

* A location is considered as infected if an infected person visited that location.
* If number of infected locations are more than 60% of number of total locations, number of high risk locations equals 
to 40% of number of total locations. Else, number of high risk locations equals to number of infected locations.
* Let number of high risk locations be `n`. The first `n` number of most infected locations are shown.
* For example, number of total locations is `10`, number of infected locations is `7`, so the number of high risk 
locations is `40% * 10 = 4`. The first `4` infected locations from the list of infected locations sorted from highest to 
lowest risk are displayed.
* If there are less than ten locations that are infected, all locations will
  be shown.
  
#### Listing summary of data

Shows the general summary of the data in the form of statistics.

Format: `list l/stats`

* Data used to generate statistics are the people, locations and visits added into VirusTracker.
* Currently, the statistics generated include:
    1. Total number of people, locations and visits
    2. Total number of people infected/quarantined
    3. Percentage of people infected/quarantined
* The above provides a brief summary of the pandemic and is subject to extension.

### Generating all people in contact with an infected person: `generatePeople`

Shows a list of people who where in contact with an infected person in the past 2 weeks. 

Format: `generatePeople INDEX`

* People listed were in contact with the infected person of the index given.
* The result given is a filtered list of people who visited the same locations as that the infected person in the past 2 weeks.
* This function can be used to identify people who need to be quarantined or issued Stay Home Notices.

### Listing all locations visited by a person: `generateLocations`

Shows a list of locations visited by an infected person in the past 2 weeks. 

Format: `generateLocations INDEX`

* Locations listed were visited by the infected person of the index given.
* The result given is a filtered list of locations that the person visited in the past 2 weeks.
* This function can be used to identify locations needing to be disinfected after being visited by an infected person.

### Clearing all entries : `clear`

Clears all entries from VirusTracker.

Format: `clear`


### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Exiting the program : `exit`

Exits the program.

Format: `exit`


### Saving the data

VirusTracker data saves in the hard disk automatically after any command that changes the data. There is no need to save manually.


### Archiving data files `[coming in v2.0]`

_{explain the feature here}_

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS q/QUARANTINE_STATUS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete PERSON_INDEX`<br> e.g., `delete 3`
**Edit** | `edit PERSON_INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [q/QUARANTINE_STATUS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list l/KEYWORD` <br> e.g., `list l/people`, `list l/stats`
**Help** | `help`
