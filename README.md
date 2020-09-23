# CASprzak
CASprzak is a Computer Algebra System (CAS) written by The EasyKayzey Show (Erez Israeli Miller and Michael Kasprzak)

Hello, and welcome to our CAS. This is a joint project by The EasyKayzey Show started at HackBCA V, 2020. CASprzak is a computer-algebra framework mainly for storing, modifying, and applying mathematical functions and functionals, and it is written completely in native Java 14 with no external libraries (except JUnit for testing purposes only).

All documentation for CASprzak can be found at: https://ezkz.show/CASprzak-documentation/.

## Functionality
### Core
- Storage of functions in an artificial data type
- Expression simplification
- Differentiation of functions 
  - Storing derivatives as new functions
- Evaluation of functions
- Various solver tools
  - Finding zeroes of functions using Newton's or Halley's Method
  - Finding the local maxima or minima of functions on a range
- Generating Taylor Series from functions
- Numerical integration via Simpson's rule
- Basic symbolic integration using only "derivative divides" substitution
### Other
- Parsing and storing infix expressions like `x^2-3y+sin(1/z)`
- Command-line interface in `CommandUI`
  - Definition and storage of functions
  - Evaluating, differentiating, and numerically integrating functions
  - Solving for zeroes and extrema of functions
  - Substituting stored functions into variables
  - Generating taylor series of functions from a point
  - Defining special constants
  - Defining and removing variables
  - Demo command demonstrating core features

## Usage
1. Download the latest release jar from the `Releases` tab
2. Ensure you are running Java 14
3. Launch the program in your favorite command line using `java -jar --enable-preview CASprzak.jar` 

## Notes
- If you find a problem with the CAS, please report the bug in `Issues` so we can fix it
- If you have any feature suggestions not present in `Projects`, make a feature request in `Issues`
- If you would like to design a better interface than the placeholder that we have now, please contact us by email
- To use `cas.properties`:
   - If you are using the jar, put the file in the same directory as `CASprzak.jar`
   - If you are using the source code, leave the file in `src/config`
- Binaries are available for all stable releases under the `Releases` tab

## Build and Test

This project uses Gradle for building and testing. To build and test CASprzak, run this command in a terminal:
```shell
./gradlew build
```

On Windows, run this in Command Prompt:
```
gradlew.bat build
```

A combined JAR for distribution will be created in `build/libs`. JARS of individual libraries will be created in `core/build/libs`, `parsing/build/libs`, and `commandui/build/libs`.

To run the command line UI directly, use `./gradlew commandui:run` or `gradlew.bat commandui:run`.

## Contacts
### Group
The EasyKayzey Show: easykayzey@ezkz.show

### Individuals
Erez Israeli Miller: erez@ezkz.show

Michael Kasprzak: michael@ezkz.show

### Site
https://ezkz.show/CASprzak/
