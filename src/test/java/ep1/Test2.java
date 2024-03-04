package ep1;

import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;


public class Test2 {
    public static void main(String[] arguments) {
        SymbolTable symbolTable = new SymbolTable();
        SymbolTableValueInt intValue1 = new SymbolTableValueInt("i", ETypes.entier, 10);
        symbolTable.addValue(intValue1);
        SymbolTableValueInt intValue2 = new SymbolTableValueInt("j", ETypes.entier, 20);
        symbolTable.addValue(intValue2);
        SymbolTableValueInt intValue3 = new SymbolTableValueInt("k", ETypes.entier);
        symbolTable.addValue(intValue3);
        SymbolTableValueInt intValue4 = new SymbolTableValueInt("l", ETypes.entier);
        symbolTable.addValue(intValue4);

        Prog program = new Prog();
        SymbolTableValueFunction mainFunctionTable = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(mainFunctionTable);
        Fonction mainFunction = new Fonction(mainFunctionTable);
        program.ajouterUnFils(mainFunction);

        System.out.println("Table des symboles :\n" + symbolTable.toString());
        String result = new GenerateCode().generateUASM(program, symbolTable);
        System.out.println("Arbre :");
        TxtAfficheur.afficher(program);
        System.out.println("Code Assembleur :\n" + result);
    }
}
