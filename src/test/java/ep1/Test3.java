package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test3 {
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

        Idf i_i = new Idf(i);
        Idf i_l = new Idf(l);
        Idf i_k = new Idf(k);

        Prog prg = new Prog();

        SymbolTableValueFunction TDSmain = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(TDSmain);
        Fonction main = new Fonction(TDSmain);
        prg.ajouterUnFils(main);


        Affectation affectation1 = new Affectation();
        affectation1.setFilsGauche(i_k);
        affectation1.setFilsDroit(new Const(2));

        Affectation affectation2 = new Affectation();
        affectation2.setFilsGauche(i_l);

        Plus plus = new Plus();
        affectation2.setFilsDroit(plus);
        plus.setFilsGauche(i_i);

        Multiplication mul = new Multiplication();
        plus.setFilsDroit(mul);
        mul.setFilsGauche(new Const(3));

        main.ajouterUnFils(affectation1);
        main.ajouterUnFils(affectation2);

        String res = new GenerateCode().generateUASM(prg, symbolTable);
        System.out.println(res);
		TxtAfficheur.afficher(prg);
    }
}
