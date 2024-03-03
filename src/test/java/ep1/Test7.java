package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test7 {
    public static void main(String[] arguments) {
        SymbolTable symbolTable = new SymbolTable();
        SymbolTableValueFunction mainFunctionTable = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(mainFunctionTable);
        SymbolTableValueInt intValueA = new SymbolTableValueInt("a", ETypes.entier, 10);
        symbolTable.addValue(intValueA);
        SymbolTableValueFunction functionFTable = new SymbolTableValueFunction("f", ETypes.vide, 1, 2);
        symbolTable.addValue(functionFTable);
        SymbolTableValueIntParam intValueI = new SymbolTableValueIntParam("i", ETypes.entier, functionFTable, 0);
        symbolTable.addValue(intValueI);
        SymbolTableValueInt intValueX = new SymbolTableValueInt("x", ETypes.entier, functionFTable, 0);
        symbolTable.addValue(intValueX);
        SymbolTableValueInt intValueY = new SymbolTableValueInt("y", ETypes.entier, functionFTable, 1);
        symbolTable.addValue(intValueY);

        Prog program = new Prog();
        Fonction mainFunction = new Fonction(mainFunctionTable);
        program.ajouterUnFils(mainFunction);
        Fonction functionF = new Fonction(functionFTable);
        program.ajouterUnFils(functionF);

        // Function F
        Affectation assignment1 = new Affectation();
        functionF.ajouterUnFils(assignment1);
        assignment1.setFilsGauche(new Idf(intValueX));
        assignment1.setFilsDroit(new Const(1));

        Affectation assignment2 = new Affectation();
        functionF.ajouterUnFils(assignment2);
        assignment2.setFilsGauche(new Idf(intValueY));
        assignment2.setFilsDroit(new Const(1));

        Affectation assignment3 = new Affectation();
        functionF.ajouterUnFils(assignment3);
        assignment3.setFilsGauche(new Idf(intValueA));
        Plus plus1 = new Plus();
        assignment3.setFilsDroit(plus1);
        plus1.setFilsGauche(new Idf(intValueI));
        Plus plus2 = new Plus();
        plus1.setFilsDroit(plus2);
        plus2.setFilsGauche(new Idf(intValueX));
        plus2.setFilsDroit(new Idf(intValueY));

        // Main Function
        Appel appel = new Appel(functionFTable);
        appel.ajouterUnFils(new Const(3));
        mainFunction.ajouterUnFils(appel);

        Ecrire ecrire = new Ecrire();
        ecrire.ajouterUnFils(new Idf(intValueA));
        mainFunction.ajouterUnFils(ecrire);

        String result = new GenerateCode().generateUASM(program, symbolTable);
        System.out.println(result);
        TxtAfficheur.afficher(program);

    }
}
