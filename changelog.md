# Changelog
## v0.1.1
### Bugfixes
 - Improved support for carriage returns
 - Fix bug that caused CASDemo to exit on entry if it had been previously completed
## v0.1.0
### Major Additions
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
### Minor Additions
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