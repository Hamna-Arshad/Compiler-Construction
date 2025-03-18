# Lexical Analyzer - User Manual


## Keywords and Rules

### Data Types
- *Flag* → Represents boolean values (true / false).
- *Num* → Represents whole numbers (Integers).
- *Dec* → Represents decimal numbers (Decimals, accurate up to five decimal places, following standard rounding rules).
- *Char* → Represents single-character values (Characters).

### Variable Naming Rules
- Only lowercase letters (a to z) are allowed as valid variable names or identifiers.

### Arithmetic Operations
The system supports the following arithmetic operations:
- *plus* → Addition (+)
- *sub* → Subtraction (-)
- *mul* → Multiplication (*)
- *div* → Division (/)
- *mod* → Modulus (% for remainder calculation)
- *exp* → Raising numbers to a power for both whole numbers and decimals.

### Comments
- *Single-line comment:* ** (everything after ** on the same line is ignored by the lexer)
- *Multi-line comment:* *** ... *** (everything between *** and *** is ignored)

### Syntax Rules
- *Assignment:* Variables are assigned values using the equal keyword or =.
- *Ignoring Extra Spaces:* The system handles extra spaces between elements and ignores them.
- *Global and Local Variables:* Supports both scopes.
- *Transition State Table:* Users can view the transition state table for debugging or understanding the lexer's tokenization process.


## Transition State Table
The lexer processes input using a transition state table, which maps states based on the current character/token. This table helps in debugging and understanding how the input is being parsed. A detailed transition state table is available within the program.

## Conclusion
This lexical analyzer ensures robust tokenization and processing of a custom programming language with support for comments, arithmetic operations, and different data types. The system provides structured tokenization, handles errors gracefully, and ensures correct recognition of identifiers and expressions.
