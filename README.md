# CASprzak
CASprzak is a Computer Algebra System (CAS) written by The EasyKayzey Show (Erez Israeli Miller and Michael Kasprzak)

Hello, and welcome to our CAS. This is a joint project by The EasyKayzey Show started at HackBCA V, 2020. CASprzak is a computer-algebra framework mainly for storing, modifying, and applying mathematical functions and functionals, and it is written completely in native Java 14 with no external libraries (except JUnit for testing purposes only).

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
  - Demo - to start the demo, run `CommandUI` and input `demo`

## Notes
- If you find a problem with the CAS, please report the bug in `Issues` so we can fix it
- If you have any feature suggestions not present in `Projects`, make a feature request in `Issues`
- If you would like to design a better interface than the placeholder that we have now, please contact us by email
- As noted before, to begin the demo, run `CommandUI` and input `demo`
- All documentation can be found at: https://easykayzey.github.io/CASprzak-documentation/
- To use `cas.properties`:
   - If you are using a binary, put the file in the same directory as `CASprzak.jar`
   - If you are using the source code, leave the file in `src/config`.
- Binaries are available for all stable releases under the `Releases` tab

## Contacts
### Group
The EasyKayzey Show: easykayzey@ezkz.show

### Individuals
Erez Israeli Miller: erez@ezkz.show

Michael Kasprzak: michael@ezkz.show

### Site
https://ezkz.show/CASprzak/
