package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test7 {
    public static void main(String[] args) {
    	SymbolTable symbolTable = new SymbolTable();
    	SymbolTableValueFunction TDSMain = new SymbolTableValueFunction("main", ETypes.vide);
    	symbolTable.addValue(TDSMain);
    	SymbolTableValueInt TDSa = new SymbolTableValueInt("a", ETypes.entier, 10);
        symbolTable.addValue(TDSa);
        SymbolTableValueFunction TDSf = new SymbolTableValueFunction("f", ETypes.vide, 1, 2);
        symbolTable.addValue(TDSf);
        SymbolTableValueIntParam TDSi = new SymbolTableValueIntParam("i", ETypes.entier, TDSf, 0);
        symbolTable.addValue(TDSi);
        SymbolTableValueInt TDSx = new SymbolTableValueInt("x", ETypes.entier, TDSf, 0);
        symbolTable.addValue(TDSx);
        SymbolTableValueInt TDSy = new SymbolTableValueInt("y", ETypes.entier, TDSf, 1);
        symbolTable.addValue(TDSy);

        Prog prg = new Prog();
        Fonction main = new Fonction(TDSMain);
        prg.ajouterUnFils(main);
        Fonction f = new Fonction(TDSf);
        prg.ajouterUnFils(f);

        //f
        Affectation aff1 = new Affectation();
        f.ajouterUnFils(aff1);
        aff1.setFilsGauche(new Idf(TDSx));
        aff1.setFilsDroit(new Const(1));

        Affectation aff2 = new Affectation();
        f.ajouterUnFils(aff2);
        aff2.setFilsGauche(new Idf(TDSy));
        aff2.setFilsDroit(new Const(1));

        Affectation aff3 = new Affectation();
        f.ajouterUnFils(aff3);
        aff3.setFilsGauche(new Idf(TDSa));
        Plus plus1 = new Plus();
        aff3.setFilsDroit(plus1);
        plus1.setFilsGauche(new Idf(TDSi));
        Plus plus2 = new Plus();
        plus1.setFilsDroit(plus2);
        plus2.setFilsGauche(new Idf(TDSx));
        plus2.setFilsDroit(new Idf(TDSy));

        //main 
        Appel appel = new Appel(TDSf);
        appel.ajouterUnFils(new Const(3));
        main.ajouterUnFils(appel);

        Ecrire ecrire = new Ecrire();
        ecrire.ajouterUnFils(new Idf(TDSa));
        main.ajouterUnFils(ecrire);

        String res = new GenerateCode().generateUASM(prg, symbolTable);
        System.out.println(res);
		TxtAfficheur.afficher(prg);

    }
}