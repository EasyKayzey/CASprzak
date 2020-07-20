# Changelog
# v0.2.1
### Minor Additions
 - Add 'v' to the version String from the `version` command
 - Add `v` as another way to call `version` in KeywordInterface
 - Add a period to the end of 'Reset done.'
### Bugfixes
 - Fix malformed whitespace in the `help` command
# v0.2.0
### Major Additions 
 - Integer division (with `//`), modulo, lcm, and gcd operations
 - Floor, ceiling, rounding, and random operations
 - New abstract classes for new integer operations
 - Settings to enforce functions' domain and range when simplifying using inverse functions
 - Array indexing to retrieve values from methods like `solve`

### Minor Additions
 - Make parsing errors more descriptive
 - Port parsing to use operation maps rather than operation lists
 - New test class for integer operations
 - Many new exceptions for improved error handling
 - Support for `_` in `evaluate` (used as `eval x^2 x=_` when `_` is a `Double`)
 - Better handling of `ArrayIndexOutOfBoundsExceptions` in the keyword interface
 - `err` command to improve error reporting
 - `version` command
 - `reset` command
 
 ### Changes
 - Restrict variable and function names to a regex of valid names to improve multi-character name support
 - Change `equals` to check if two functions are exactly equal and implement `equalsSimplified` to check if they are equal when simplified
 - Make `substitute`/`sub` more powerful in the keyword interface by allowing the substitution of multiple expressions simultaneously
 - Make `def` and `sub` in automatically perform a `minimalSimplify`
 - Move `SettingsParser` functionality directly to `Settings`
 - Rename `SpecialFunction` to `EndpointFunction`
 - Improve exit logic from all interfaces
 
 ### Bugfixes
 - Fix `toInteger` error message using the wrong margin from `Settings`
 - Fix bad rounding in `ParsingTools.toInteger` and `ParsingTools.isAlmostInteger`
 - Fix the evaluation of several arctrig functions 
 - Fix parsing of non-escaped expressions with spaces such as `1 + sin(x)`
 - Fix `defconstant` not LaTeX-escaping constant names
 - Add an exception when user attempts to use nested quotes in keyword interface
 - Fix division by decimals such as `1/.2` not working properly
 
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
