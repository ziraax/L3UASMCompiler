package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test8 {

    public static void main(String[] arguments) {

        SymbolTable symbolTable = new SymbolTable();

        SymbolTableValueFunction mainFunctionTable = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(mainFunctionTable);
        SymbolTableValueInt intValueA = new SymbolTableValueInt("a", ETypes.entier);
        symbolTable.addValue(intValueA);
        SymbolTableValueFunction functionFTable = new SymbolTableValueFunction("f", ETypes.entier, 2, 1);
        symbolTable.addValue(functionFTable);
        SymbolTableValueInt intValueX = new SymbolTableValueInt("x", ETypes.entier, functionFTable, 0);
        symbolTable.addValue(intValueX);
        SymbolTableValueIntParam intValueI = new SymbolTableValueIntParam("i", ETypes.entier, functionFTable, 0);
        symbolTable.addValue(intValueI);
        SymbolTableValueIntParam intValueJ = new SymbolTableValueIntParam("j", ETypes.entier, functionFTable, 1);
        symbolTable.addValue(intValueJ);

        Prog program = new Prog();
        Fonction mainFunction = new Fonction(mainFunctionTable);
        program.ajouterUnFils(mainFunction);
        Fonction functionF = new Fonction(functionFTable);
        program.ajouterUnFils(functionF);

        // Function F
        Affectation assignment2 = new Affectation();
        Plus plus1 = new Plus();
        plus1.setFilsGauche(new Idf(intValueI));
        plus1.setFilsDroit(new Idf(intValueJ));
        assignment2.setFilsGauche(new Idf(intValueX));
        assignment2.setFilsDroit(plus1);

        Retour retour = new Retour(functionFTable);
        Plus plus2 = new Plus();
        plus2.setFilsGauche(new Idf(intValueX));
        plus2.setFilsDroit(new Const(10));
        retour.setLeFils(plus2);

        functionF.ajouterUnFils(assignment2);
        functionF.ajouterUnFils(retour);

        // Main Function
        Affectation assignment1 = new Affectation();
        Appel functionCall = new Appel(functionFTable);
        functionCall.ajouterUnFils(new Const(1));
        functionCall.ajouterUnFils(new Const(2));
        assignment1.setFilsGauche(new Idf(intValueA));
        assignment1.setFilsDroit(functionCall);

        Ecrire write = new Ecrire();
        write.setLeFils(new Idf(intValueA));

        mainFunction.ajouterUnFils(assignment1);
        mainFunction.ajouterUnFils(write);

        System.out.println("Table des symboles :\n" + symbolTable.toString());
        String result = new GenerateCode().generateUASM(program, symbolTable);
        System.out.println("Arbre :");
        TxtAfficheur.afficher(program);
        System.out.println("Code Assembleur :\n" + result);
    }
}
