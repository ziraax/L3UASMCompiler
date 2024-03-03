package ep1;

import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;


public class Test2 {
    public static void main(String[] args) {
    	SymbolTable symbolTable = new SymbolTable();
    	SymbolTableValueInt i = new SymbolTableValueInt("i", ETypes.entier, 10);
    	symbolTable.addValue(i);
    	SymbolTableValueInt j = new SymbolTableValueInt("j", ETypes.entier, 20);
        symbolTable.addValue(j);
        SymbolTableValueInt k = new SymbolTableValueInt("k", ETypes.entier);
        symbolTable.addValue(k);
        SymbolTableValueInt l = new SymbolTableValueInt("l", ETypes.entier);
        symbolTable.addValue(l);

        Prog prg = new Prog();
        SymbolTableValueFunction TDSmain = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(TDSmain);
        Fonction main = new Fonction(TDSmain);
        prg.ajouterUnFils(main);

        String res = new GenerateCode().generateUASM(prg, symbolTable);
        System.out.println(res);
		TxtAfficheur.afficher(prg);
    }
}
