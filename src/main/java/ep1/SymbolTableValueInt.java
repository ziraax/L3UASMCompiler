package ep1;

public class SymbolTableValueInt extends SymbolTableValue {
	int value;
	SymbolTableValue scope;
	int rank;
	
    public SymbolTableValueInt(String name, ETypes type) {
        super(name, type, ECat.Eglobal);
    }

    public SymbolTableValueInt(String name, ETypes type, SymbolTableValue scope, int rank) {
        super(name, type, ECat.Elocal);
        this.scope = scope;
        this.rank = rank;
    }

    public SymbolTableValueInt(String name, ETypes type, int val) {
        super(name, type, ECat.Eglobal);
        this.value = val;
    }   

    public SymbolTableValueInt(String nom, ETypes type,SymbolTableValue scope,int rank, int val) {
        super(nom, type, ECat.Elocal);
        this.value = val;
        this.scope = scope;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "{nom: " + name + ", type: " + type + ", cat: " + cat + ", val: " + value + ", rang: " + rank + ", scope: " + scope + "}";
    } 

    @Override
    public SymbolTableKey getKey() {
        return new SymbolTableKey(name, scope);
    }

    @Override
    public int getOffset() {
        return rank*4;
    }
}
