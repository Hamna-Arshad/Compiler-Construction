package lexer;

public class SymbolTableEntry {
    private String name;
    private String type;
    private String scope;
    private String value;

    public SymbolTableEntry(String name, String type, String scope, String value) {
        this.name = name;
        this.type = type;
        this.scope = scope;
        this.value = value;
    }

    // Getters
    public String getName() { return name; }
    public String getType() { return type; }
    public String getScope() { return scope; }
    public String getValue() { return value; }

    // Setters
    public void setValue(String value) { this.value = value; }
}