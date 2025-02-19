package compiler.regex;

public class RegEx
{
    private String pattern;

    private String pattern2     =
            "[a-z][a-z0-9]|true|false|\\d+|\\d+\\.\\d{1,5}|[a-z]" +
            "|plus|minus|mul|div|mod|power|equal|num|dec|bool|letter|sentence|show";
    
    public RegEx(String pattern) {
        this.pattern = pattern;
    }
    
    public String getPattern() {
        return pattern;
    }
    
    public boolean matches(String input) {
        return input.matches(pattern);
    }
}

/*
 Identifiers: ^[a-z][a-z0-9]*$
Boolean: ^(true|false)$
Integers: ^\d+$
Decimal Numbers: ^\d+\.\d{1,5}$
Character: ^'[a-z]'$
Operators: ^(plus|minus|mul|div|mod|power|equal)$
Keywords: ^(num|dec|bool|letter|sentence|show)$
Whitespace: ^\s*|\s*$
Comments: /\*[\s\S]*?\*/
/*Exponents: ^\d+(\.\d{1,5})?[eE][+-]?\d+$

*/
