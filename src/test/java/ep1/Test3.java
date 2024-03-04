package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test3 {
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

        Idf idf_i = new Idf(intValue1);
        Idf idf_l = new Idf(intValue4);
        Idf idf_k = new Idf(intValue3);
        Idf idf_j = new Idf(intValue2);

        Prog program = new Prog();

        SymbolTableValueFunction mainFunctionTable = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(mainFunctionTable);
        Fonction mainFunction = new Fonction(mainFunctionTable);
        program.ajouterUnFils(mainFunction);

        Affectation assignment1 = new Affectation();
        assignment1.setFilsGauche(idf_k);
        assignment1.setFilsDroit(new Const(2));

        Affectation assignment2 = new Affectation();
        assignment2.setFilsGauche(idf_l);

        Plus plus = new Plus();
        assignment2.setFilsDroit(plus);
        plus.setFilsGauche(idf_i);

        Multiplication multiplication = new Multiplication();
        plus.setFilsDroit(multiplication);
        multiplication.setFilsGauche(new Const(3));
        multiplication.setFilsDroit(idf_j);

        mainFunction.ajouterUnFils(assignment1);
        mainFunction.ajouterUnFils(assignment2);

        String result = new GenerateCode().generateUASM(program, symbolTable);
        System.out.println(result);
        TxtAfficheur.afficher(program);
    }
}
