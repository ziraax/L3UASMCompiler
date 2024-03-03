package ep1;

// Represents an integer value in the symbol table
public class SymbolTableValueInt extends SymbolTableValue {
	// Value of the integer
	int value;
	// Scope of the integer
	SymbolTableValue scope;
	// Rank of the integer
	int rank;
	
	// Constructor with name and type
    public SymbolTableValueInt(String name, ETypes type) {
        super(name, type, ECat.Eglobal);
    }

    // Constructor with name, type, scope, and rank
    public SymbolTableValueInt(String name, ETypes type, SymbolTableValue scope, int rank) {
        super(name, type, ECat.Elocal);
        this.scope = scope;
        this.rank = rank;
    }

    // Constructor with name, type, and value
    public SymbolTableValueInt(String name, ETypes type, int val) {
        super(name, type, ECat.Eglobal);
        this.value = val;
    }   

    // Constructor with name, type, scope, rank, and value
    public SymbolTableValueInt(String name, ETypes type, SymbolTableValue scope, int rank, int val) {
        super(name, type, ECat.Elocal);
        this.value = val;
        this.scope = scope;
        this.rank = rank;
    }

    // Method to represent the integer value as a string
    @Override
    public String toString() {
        return "{nom: " + name + ", type: " + type + ", cat: " + cat + ", val: " + value + ", rang: " + rank + ", scope: " + scope + "}";
    } 

    // Method to get the key associated with the integer value
    @Override
    public SymbolTableKey getKey() {
        return new SymbolTableKey(name, scope);
    }

    // Method to get the offset of the integer value
    @Override
    public int getOffset() {
        return rank*4;
    }
}
