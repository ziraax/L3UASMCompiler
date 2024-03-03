package ep1;

// Represents an integer parameter value in the symbol table
public class SymbolTableValueIntParam extends SymbolTableValueInt {
    // Rank of the parameter
    int rank;

    // Constructor with name, type, scope, and rank
    public SymbolTableValueIntParam(String name, ETypes type, SymbolTableValue scope, int rank) {
        super(name, type, scope, 0);
        this.rank = rank;
        this.cat = ECat.Eparam;
    }

    // Constructor with name, type, scope, rank, and value
    public SymbolTableValueIntParam(String name, ETypes type, SymbolTableValue scope, int rank, int value) {
        super(name, type, scope, value);
        this.rank = rank;
        this.cat = ECat.Eparam;
    }

    // Method to represent the integer parameter value as a string
    @Override
    public String toString() {
        return "{nom: " + name + ", type: " + type + ", cat: " + cat + ", val: " + value + ", rang: " + rank + ", scope: " + scope + "}";
    }    

    // Method to get the offset of the integer parameter value
    @Override
    public int getOffset() {
        return (-3 - rank) * 4;
    }
}
