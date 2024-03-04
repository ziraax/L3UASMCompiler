package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test6 {
    public static void main(String[] arguments) {
        SymbolTable symbolTable = new SymbolTable();
        Prog program = new Prog();

        SymbolTableValueInt intValue1 = new SymbolTableValueInt("i", ETypes.entier);
        SymbolTableValueInt intValue2 = new SymbolTableValueInt("n", ETypes.entier, 5);
        symbolTable.addValue(intValue2);
        symbolTable.addValue(intValue1);

        SymbolTableValueFunction mainFunctionTable = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(mainFunctionTable);
        Fonction mainFunction = new Fonction(mainFunctionTable);
        program.ajouterUnFils(mainFunction);

        Affectation assignment1 = new Affectation();
        mainFunction.ajouterUnFils(assignment1);

        Idf idf_i = new Idf(intValue1);
        assignment1.setFilsGauche(idf_i);
        assignment1.setFilsDroit(new Const(0));

        TantQue whileLoop = new TantQue(0);
        mainFunction.ajouterUnFils(whileLoop);

        Inferieur lessThan = new Inferieur();
        whileLoop.setCondition(lessThan);
        Idf idf_n = new Idf(intValue2);
        lessThan.setFilsGauche(idf_i);
        lessThan.setFilsDroit(idf_n);

        Bloc block = new Bloc();
        whileLoop.setBloc(block);

        Ecrire write = new Ecrire();
        write.ajouterUnFils(idf_i);
        block.ajouterUnFils(write);

        Affectation assignment2 = new Affectation();
        block.ajouterUnFils(assignment2);
        assignment2.setFilsGauche(idf_i);
        Plus plus = new Plus();
        assignment2.setFilsDroit(plus);
        plus.setFilsDroit(new Const(1));
        plus.setFilsGauche(idf_i);

        String result = new GenerateCode().generateUASM(program, symbolTable);
        System.out.println(result);
        TxtAfficheur.afficher(program);

    }
}
