package ep1;

public class SymbolTableValueIntParam  extends SymbolTableValueInt{
    int rank;

    public SymbolTableValueIntParam(String name, ETypes type, SymbolTableValue scope, int rank) {
        super(name, type, scope, 0);
        this.rank = rank;
        this.cat = ECat.Eparam;
    }

    public SymbolTableValueIntParam(String nom, ETypes type, SymbolTableValue scope,int rank, int value) {
        super(nom, type, scope, value);
        this.rank = rank;
        this.cat = ECat.Eparam;
    }

    @Override
    public String toString() {
        return "{nom: " + name + ", type: " + type + ", cat: " + cat + ", val: " + value + ", rang: " + rank + ", scope: " + scope + "}";
    }    

    @Override
    public int getOffset() {
        return (-3 - rank)*4;
    }
}
