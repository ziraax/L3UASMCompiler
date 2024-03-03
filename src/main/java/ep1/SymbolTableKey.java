package ep1;

public class SymbolTableKey {
	String name;
	SymbolTableValue scope;
	
	public SymbolTableKey(String name) {
		this.name = name;
	}
	
	public SymbolTableKey(String name, SymbolTableValue scope) {
		this.name = name;
		this.scope = scope;
	}
	
    public String toString() {
        return "{" + name + ", " + scope + "}";
    }

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
