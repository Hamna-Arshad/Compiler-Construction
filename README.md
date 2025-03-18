# Lexical Analyzer - User Manual

## Overview
This document serves as a user manual for the lexical analyzer, which processes a custom programming language with specific keywords, rules, and syntax. It is designed to recognize and tokenize different data types, arithmetic operations, and comments while ensuring proper handling of variable scopes and formatting.

## Keywords and Rules

### Data Types
- **Flag** → Represents boolean values (`true` / `false`).
- **Num** → Represents whole numbers (Integers).
- **Dec** → Represents decimal numbers (Decimals, accurate up to five decimal places, following standard rounding rules).
- **Char** → Represents single-character values (Characters).

### Variable Naming Rules
- Only lowercase letters (`a` to `z`) are allowed as valid variable names or identifiers.

### Arithmetic Operations
The system supports the following arithmetic operations:
- **plus** → Addition (`+`)
- **sub** → Subtraction (`-`)
- **mul** → Multiplication (`*`)
- **div** → Division (`/`)
- **mod** → Modulus (`%` for remainder calculation)
- **Exponentiation** → Raising numbers to a power for both whole numbers and decimals.

### Comments
- **Single-line comment:** `**` (everything after `**` on the same line is ignored by the lexer)
- **Multi-line comment:** `*** ... ***` (everything between `***` and `***` is ignored, even in tricky multi-line cases)

### Syntax Rules
- **Assignment:** Variables are assigned values using the `equal` keyword or `=`.
- **Ignoring Extra Spaces:** The system handles extra spaces between elements and ignores them.
- **Global and Local Variables:** Supports both scopes.
- **Transition State Table:** Users can view the transition state table for debugging or understanding the lexer's tokenization process.

## Example Code
```plaintext
num x equal 10
dec y equal 3.14159
numm a equal 8
dec d = 3.1

***
dec r equal 3.1
***

flag isvalid equal true
**flag w equal true
char var equal k

x equal a plus b
show x plus y
```

## Expected Tokenization
| Token Type  | Lexeme        |
|------------|--------------|
| Keyword    | num          |
| Identifier | x            |
| Operator   | equal        |
| Integer    | 10           |
| Keyword    | dec          |
| Identifier | y            |
| Operator   | equal        |
| Decimal    | 3.14159      |
| Identifier | a            |
| Operator   | equal        |
| Integer    | 8            |
| Keyword    | dec          |
| Identifier | d            |
| Operator   | =            |
| Decimal    | 3.1          |
| Comment    | *** ... ***  |
| Keyword    | flag         |
| Identifier | isvalid      |
| Operator   | equal        |
| Boolean    | true         |
| Comment    | **flag w...  |
| Keyword    | char         |
| Identifier | var          |
| Operator   | equal        |
| Character  | k            |
| Identifier | x            |
| Operator   | equal        |
| Identifier | a            |
| Operator   | plus         |
| Identifier | b            |
| Keyword    | show         |
| Identifier | x            |
| Operator   | plus         |
| Identifier | y            |

## Transition State Table
The lexer processes input using a transition state table, which maps states based on the current character/token. This table helps in debugging and understanding how the input is being parsed. A detailed transition state table is available within the program.

## Conclusion
This lexical analyzer ensures robust tokenization and processing of a custom programming language with support for comments, arithmetic operations, and different data types. The system provides structured tokenization, handles errors gracefully, and ensures correct recognition of identifiers and expressions.
