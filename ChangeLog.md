# Changelog
# v0.2.0
### Major Additions 
 - Integer division (with `//`), modulo, lcm, and gcd operations
 - Floor, ceiling, rounding, and random operations
 - Add new abstract classes for new integer operations
 - Add settings to enforce functions' domain and range when simplifying using inverse functions
 - Add array indexing to retrieve values from methods like `solve`

### Minor Additions
 - Make parsing errors more descriptive
 - Port parsing to use operation maps rather than operation lists
 - Add new test class for integer operations
 - Change `equals` to check if two functions are exactly equal and implement `equalsSimplified` to check if they are equal when simplified
 - Add `DerivativeDoesNotExistException` for operations with no derivative
 - Make `substitute` more powerful in the UI by allowing the substitution of multiple expressions simultaneously
 - Make `def` and `sub` in `CommandUI` now automatically perform a `minimalSimplify`
 - Add support for `_` in `evaluate` (used as `eval x^2 x=_` when `_` is a  `Double`)
 - Add `version` command to keyword interface
 - Add `reset` command to keyword interface
 
 ### Bugfixes
 - Fix `toInteger` error message using the wrong margin from `Settings`
 - Fix bad rounding in `ParsingTools.toInteger` and `ParsingTools.isAlmostInteger`
 - Fix the evaluation of several arctrig functions 
 - Fix parsing of non-escaped expressions with spaces such as `1 + sin(x)`
 
## v0.1.2
### Bugfixes
 - Fix properties not being read on launch for binaries

## v0.1.1
### Bugfixes
 - Improves support for carriage returns
 - Fix bug that caused CASDemo to exit on entry if it had been previously completed
 - Make TaylorSeries use SingleVariableDefault and not "x"
  
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