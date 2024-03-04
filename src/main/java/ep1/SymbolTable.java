package ep1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Table des symboles (SymbolTable)
public class SymbolTable {
	
	// Map to store symbol table entries
	HashMap<SymbolTableKey, SymbolTableValue> map;
	
	// Counters for if and while statements
	public int ifCounter = 0;
	public int whileCounter = 0;

	// Current scope for functions
    public SymbolTableValueFunction currentScope;

	// Constructor
	public SymbolTable() {
		map = new HashMap<>();
	}
	
    //


    @Override
    public String toString() {
        String s ="";
        for (Map.Entry entry : map.entrySet()) {
            s += entry.getValue() + "\n";
        }
        return s;
    }

    // Method to add a key-value pair to the symbol table
	public void addValue(SymbolTableKey key, SymbolTableValue value) {
		map.put(key, value);
	}
	
	// Overloaded method to add a value with its key to the symbol table
    public void addValue(SymbolTableValue value) {
        map.put(value.getKey(), value);
    }
    
    // Method to get the value associated with a key in the symbol table
    public SymbolTableValue getValue(SymbolTableKey key) {
    	return map.get(key);
    }
    
    // Method to get all values in the symbol table
    public Collection<SymbolTableValue> getValues() {
        return map.values();
    }

    // Method to display all values in the symbol table on the console
    public void displayOnConsole() {
        map.forEach((key, value) -> {System.out.println(value);});
    }

    // Method to find a variable by name and scope in the symbol table
    public SymbolTableValue findVariable(String name, SymbolTableValueFunction scope) {
        // Search for global variables
        SymbolTableValue res = map.get(new SymbolTableKey(name));
        if(res != null) {
            return res;
        }
        // Search for local variables
        res = map.get(new SymbolTableKey(name, scope));
        return res;
    }

    // Method to check if a function is defined in the symbol table
    public boolean isFunctionDefined(String name) {
    	SymbolTableValueFunction SymbolTableValue = (SymbolTableValueFunction)map.get(new SymbolTableKey(name));
        return (SymbolTableValue != null && SymbolTableValue.isDefined);
    }

    // Method to check if a function is declared in the symbol table
    public boolean isFunctionDeclared(String name) {
    	SymbolTableValueFunction SymbolTableValue = (SymbolTableValueFunction)map.get(new SymbolTableKey(name));
        return (SymbolTableValue != null);
    }
}
