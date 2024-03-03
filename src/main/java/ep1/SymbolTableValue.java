package ep1;

public abstract class SymbolTableValue {
	
	public enum ETypes {
		entier, 
		vide
	}
	
	public enum ECat {
	    Efunction,
	    Eglobal,
	    Elocal,
	    Eparam
	}
	
	String name;
	ETypes type;
	ECat cat;
	
	public SymbolTableValue(String name, ETypes type, ECat cat) {
		this.name = name;
		this.type = type;
		this.cat = cat;
	}
	
	public abstract String toString();
	
	public SymbolTableKey getKey() {
		return new SymbolTableKey(name);
	}
	
	public int getOffset() {
		return 0;
	}
	
	public String getName() {
		return name;
	}
	
	
	
}
