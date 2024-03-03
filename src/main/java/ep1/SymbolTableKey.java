package ep1;

// Represents a key in the symbol table
public class SymbolTableKey {
	// Name of the symbol
	String name;
	// Scope of the symbol
	SymbolTableValue scope;
	
	// Constructor for a symbol with no scope
	public SymbolTableKey(String name) {
		this.name = name;
	}
	
	// Constructor for a symbol with a scope
	public SymbolTableKey(String name, SymbolTableValue scope) {
		this.name = name;
		this.scope = scope;
	}
	
	// Method to represent the key as a string
    public String toString() {
        return "{" + name + ", " + scope + "}";
    }

    // Method to check if two keys are equal
    public boolean equals(Object o) {
        if(o != null && o instanceof SymbolTableKey) {
        	SymbolTableKey key = (SymbolTableKey) o;
            if(scope == null) {
                if(name.equals(key.name)) {
                    return true;
                }
            } else {
                if(name.equals(key.name) && scope.equals(key.scope)) {
                    return true;
                }
            }
        }
        return false;
    }	
}
