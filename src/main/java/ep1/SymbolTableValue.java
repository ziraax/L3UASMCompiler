package ep1;

// Represents a value in the symbol table
public abstract class SymbolTableValue {
	
	// Enumeration for types of symbols
	public enum ETypes {
		entier, 
		vide
	}
	
	// Enumeration for categories of symbols
	public enum ECat {
	    Efunction,
	    Eglobal,
	    Elocal,
	    Eparam
	}
	
	// Name of the symbol
	String name;
	// Type of the symbol
	ETypes type;
	// Category of the symbol
	ECat cat;
	
	// Constructor
	public SymbolTableValue(String name, ETypes type, ECat cat) {
		this.name = name;
		this.type = type;
		this.cat = cat;
	}
	
	// Abstract method to represent the value as a string
	public abstract String toString();
	
	// Method to get the key associated with the value
	public SymbolTableKey getKey() {
		return new SymbolTableKey(name);
	}
	
	// Method to get the offset of the value
	public int getOffset() {
		return 0;
	}
	
	// Method to get the name of the value
	public String getName() {
		return name;
	}
}
