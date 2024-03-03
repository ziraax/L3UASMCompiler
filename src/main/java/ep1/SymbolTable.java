package ep1;

import java.util.Collection;
import java.util.HashMap;

//Table des symboles (SymbolTable)
public class SymbolTable {
	
	HashMap<SymbolTableKey, SymbolTableValue> map;
	
	public int ifCounter = 0;
	public int whileCounter = 0;
    public SymbolTableValueFunction currentScope;

	
	public SymbolTable() {
		map = new HashMap<>();
	}
	
	public void addValue(SymbolTableKey key, SymbolTableValue value) {
		map.put(key, value);
	}
	
    public void addValue(SymbolTableValue value) {
        map.put(value.getKey(), value);
    }
    
    public SymbolTableValue getValue(SymbolTableKey key) {
    	return map.get(key);
    }
    
    public Collection<SymbolTableValue> getValues() {
        return map.values();
    }

    public void displayOnConsole() {
        map.forEach((key, value) -> {System.out.println(value);});
    }

    public SymbolTableValue findVariable(String name, SymbolTableValueFunction scope) {
        //global vars
        SymbolTableValue res = map.get(new SymbolTableKey(name));
        if(res != null) {
            return res;
        }
        //local vars
        res = map.get(new SymbolTableKey(name, scope));
        return res;
    }

    public boolean isFunctionDefined(String name) {
    	SymbolTableValueFunction SymbolTableValue = (SymbolTableValueFunction)map.get(new SymbolTableKey(name));
        return (SymbolTableValue != null && SymbolTableValue.isDefined);
    }

    public boolean isFunctionDeclared(String name) {
    	SymbolTableValueFunction SymbolTableValue = (SymbolTableValueFunction)map.get(new SymbolTableKey(name));
        return (SymbolTableValue != null);
    }
	
	
	
}
