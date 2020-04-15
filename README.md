# Z_CASprzak
Z_CASprzak is a CAS by The EasyKayzey Show (Erez Israeli Miller and Michael Kasprzak)

Hello, and welcome to our Computer Algebra System. This is a joint project by The EasyKayzey Show started at HackBCA V, 2020. It consists of a structure for parsing, storing, and working with mathematical expressions with a small command-line demo. 

## Functionality
- Parsing and storing expressions like `x^2-3y+sin(1/z)`
- Simplifying many types of expressions
- Taking and storing the derivatives of expressions
- Evaluating all of the above at any given point
- Finding zeroes of expressions using Newton's Method

## Building & Running
This project uses Gradle to automate builds. Run it with `gradlew.bat` on Windows or using `./gradlew` on Unix systems.

To build and test, run:
```
./gradlew build
```
On Windows, replace `./gradlew` with `gradlew.bat`.

To run the built-in command line UI, run:
```
./gradlew run
```

To run the `CASDebugger`, run:
```
./gradlew debugger
```

To create a JAR file suitable for distribution, run:
```
./gradlew jar
```

## Notes
- If you find a problem with the CAS, please make a new issue so we can fix it
- If you would like to design a better interface than the placeholder that we have now, please contact us.

## Contacts
### Group
The EasyKayzey Show: easykayzey@googlegroups.com

### Individuals
Erez Israeli Miller: erez.m.israeli@gmail.com

Michael Kasprzak: michaelkasprzak522@gmail.com
