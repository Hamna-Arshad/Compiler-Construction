package compiler.table;

import java.util.ArrayList;
import java.util.List;

public class symbolTable {

    List<symbolTableEntry> entryList = new ArrayList<>();
    symbolTableEntry entry;

    public void createEntry(String identifier, String type, String value, String scope){
        entry.name = identifier;
        entry.type = type;
        entry.value = value;
        entry.scope = scope;
        entryList.add(entry);
    }
    public void updateEntryName(String identifier,String val){
        for (symbolTableEntry en : entryList) {
            if (en.name.equals(identifier)) {
                en.name = val;
                break;
            }
        }
    }
    public void updateEntryType(String identifier,String val){
        for (symbolTableEntry en : entryList) {
            if (en.name.equals(identifier)) {
                en.type = val;
                break;
            }
        }
    }
    public void updateEntryValue(String identifier,String val){
        for (symbolTableEntry en : entryList) {
            if (en.name.equals(identifier)) {
                en.value = val;
                break;
            }
        }    }
    public void updateEntryScope(String identifier,String val){
        for (symbolTableEntry en : entryList) {
            if (en.name.equals(identifier)) {
                en.scope = val;
                break;
            }
        }    }

    public void printSymbolTable(){
      for (symbolTableEntry en : entryList){
            System.out.println("name: "+ en.name+", type: "+ en.type);
        }
    }
}
