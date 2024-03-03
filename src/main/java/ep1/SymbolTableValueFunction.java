package ep1;

// Represents a function value in the symbol table
public class SymbolTableValueFunction extends SymbolTableValue {
	// Number of parameters
	public int nbParam;
	// Number of local variables
	public int nbLoc;
	// Flag indicating if the function is defined
	public boolean isDefined;
	
	// Constructor with name and type
	public SymbolTableValueFunction(String name, ETypes type) {
		super(name, type, ECat.Efunction);
	}
	
	// Constructor with name, type, number of parameters, and number of local variables
	public SymbolTableValueFunction(String name, ETypes type, int nbParam, int nbLoc) {
		super(name, type, ECat.Efunction);
		this.nbParam = nbParam;
		this.nbLoc = nbLoc;
	}
	
    // Method to represent the function value as a string
    @Override
    public String toString() {
        return "{nom: " + name + ", type: " + type + ", cat: " + cat + ", nbParam: " + nbParam + ", nbLoc: " + nbLoc + "}";
    } 
    
    // Method to get the number of parameters
    public int getnbParam() {
        return nbParam;
    }

    // Method to get the number of local variables
    public int getnbLoc() {
        return nbLoc;
    }

    // Method to increment the number of parameters
    public void incrnbParam() {
        nbParam++;
    }

    // Method to increment the number of local variables
    public void incrnbLoc() {
        nbLoc++;
    }
}
