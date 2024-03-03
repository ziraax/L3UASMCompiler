package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test4 {
    public static void main(String[] args) {
    	SymbolTable symbolTable = new SymbolTable();
        Prog prg = new Prog();
        SymbolTableValueInt TDSi = new SymbolTableValueInt("i", ETypes.entier);
        SymbolTableValueInt TDSj = new SymbolTableValueInt("j", ETypes.entier,20);
        symbolTable.addValue(TDSi);
        symbolTable.addValue(TDSj);
    
        SymbolTableValueFunction TDSmain = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(TDSmain);
        Fonction main = new Fonction(TDSmain);
        prg.ajouterUnFils(main);

        Affectation aff = new Affectation();
        main.ajouterUnFils(aff);

        Idf i = new Idf(TDSi);
        aff.setFilsGauche(i);

        Lire lire = new Lire();
        aff.setFilsDroit(lire);

        Ecrire ecrire = new Ecrire();
        Plus plus = new Plus();
        main.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(plus);

        Idf i2 = new Idf(TDSi);
        plus.setFilsGauche(i2);
        Idf j = new Idf(TDSj);
        plus.setFilsDroit(j);

        String res = new GenerateCode().generateUASM(prg, symbolTable);
        System.out.println(res);
        TxtAfficheur.afficher(prg);
    }
}

