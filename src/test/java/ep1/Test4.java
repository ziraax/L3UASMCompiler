package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test4 {
    public static void main(String[] arguments) {
        SymbolTable symbolTable = new SymbolTable();
        Prog program = new Prog();
        SymbolTableValueInt intValue1 = new SymbolTableValueInt("i", ETypes.entier);
        SymbolTableValueInt intValue2 = new SymbolTableValueInt("j", ETypes.entier, 20);
        symbolTable.addValue(intValue1);
        symbolTable.addValue(intValue2);

        SymbolTableValueFunction mainFunctionTable = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(mainFunctionTable);
        Fonction mainFunction = new Fonction(mainFunctionTable);
        program.ajouterUnFils(mainFunction);

        Affectation assignment = new Affectation();
        mainFunction.ajouterUnFils(assignment);

        Idf idf_i = new Idf(intValue1);
        assignment.setFilsGauche(idf_i);

        Lire lire = new Lire();
        assignment.setFilsDroit(lire);

        Ecrire ecrire = new Ecrire();
        Plus plus = new Plus();
        mainFunction.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(plus);

        Idf idf_i2 = new Idf(intValue1);
        plus.setFilsGauche(idf_i2);
        Idf idf_j = new Idf(intValue2);
        plus.setFilsDroit(idf_j);

        String result = new GenerateCode().generateUASM(program, symbolTable);
        System.out.println(result);
        TxtAfficheur.afficher(program);
    }
}


