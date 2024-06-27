# Service History
A JavaFX application for keeping track of car maintenance. 
The app contains a clean and an easy to use interface which supports multiple cars.
All the data is saved locally and most of the common operations (create new car, open existing car, add service record, etc...) can be done via keyboard shortcuts.
Recorded cars and services to it can be printed to a pdf. 
However the printing process is not the most convinient since it relies on a browser to do the actual printing, also printing may not work for all operating systems.
The app only converts the data to html format and displays it on the system default browser.

- Save files can be located at `<USER_HOME>/.wwisky/sh` but are not meant to be modified manually
- **Nothing is saved automatically!!**
- **Application contains very little safety checks!!** For example there is no check to make sure if you really want to delete something so be careful. As well if you type any of these `| ¤ ; =` characters in the input fields unexpected things may happen as these characters are used in the very primitive save-load-system.
- **Printing may not work for all operating systems!!** The temporary html -file used in the printing process is stored in the tmp -directory
- Created for personal use, so the codebase is not the cleanest

Version 1.0

### Testing

Test coverage is not great at the moment but everything should still work.
JUnit 5 is used as the testing-framework.

## Dependencies
- Java 21 (Might also work with slightly older versions)

## Installation
**Make sure to have Java installed!!**
- Download the desired version from Releases
- Open either by double-clicking or run `java -jar service-history.jar` in the terminal (in the same directory as the .jar file)

## User Manual
### Opening the application
At the start of the application opens the main window which contains all the information about the current car and its services.
Each time the application is opened a new car record is created regardless of whether you have saved cars or not. 
Already saved cars can be opened from `File > Open` menu at the top of the window. 

### Adding new cars and modifying their data
A new car can be created at `File > New` menu at the top of the window.
The car data (VIN, Make, Model, etc...) are shown in the main window. 
By **right-clicking** the box the edit window is opened. Remember to save the changes!!

### Adding services and modifying their data
A new service can be added from `Maintenance > Add` at the top of the window. 
Services (Description, Mileage, Date, etc...) are listed in the main window. 
Edit window can be opened by **right-clicking** the box. Remember to save the changes!!

### Printing the history
The service history + car data can be printed to a pdf format or to a physical paper from the top of the window `File > Print`.
Clicking print will generate a html-file in the system tmp-directory and open it in the system default browser.
The actual printing relies on the print functionality of a browser.