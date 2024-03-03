package ep1;

public class SymbolTableValueFunction extends SymbolTableValue {
	public int nbParam;
	public int nbLoc;
	public boolean isDefined;
	
	public SymbolTableValueFunction(String name, ETypes type) {
		super(name, type, ECat.Efunction);
	}
	
	public SymbolTableValueFunction(String name, ETypes type, int nbParam, int nbLoc) {
		super(name, type, ECat.Efunction);
		this.nbParam = nbParam;
		this.nbLoc = nbLoc;
	}
	
    @Override
    public String toString() {
        return "{nom: " + name + ", type: " + type + ", cat: " + cat + ", nbParam: " + nbParam + ", nbLoc: " + nbLoc + "}";
    } 
    
    public int getnbParam() {
        return nbParam;
    }

    public int getnbLoc() {
        return nbLoc;
    }

    public void incrnbParam() {
        nbParam++;
    }

    public void incrnbLoc() {
        nbLoc++;
    }
    
    
}
