package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test1 {
    public static void main(String[] arguments) {
        SymbolTable symbolTable = new SymbolTable();
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
