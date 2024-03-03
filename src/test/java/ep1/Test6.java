package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test6 {
    public static void main(String[] args) {
    	SymbolTable symbolTable = new SymbolTable();
        Prog prg = new Prog();

        SymbolTableValueInt TDSi = new SymbolTableValueInt("i", ETypes.entier);
        SymbolTableValueInt TDSn = new SymbolTableValueInt("n", ETypes.entier, 5);
        symbolTable.addValue(TDSn);
        symbolTable.addValue(TDSi);

        SymbolTableValueFunction TDSmain = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(TDSmain);
        Fonction main = new Fonction(TDSmain);
        prg.ajouterUnFils(main);

        Affectation aff1 = new Affectation();
        main.ajouterUnFils(aff1);

        Idf i1 = new Idf(TDSi);
        aff1.setFilsGauche(i1);
        aff1.setFilsDroit(new Const(0));

        TantQue tq = new TantQue(0);
        main.ajouterUnFils(tq);

        Inferieur inf = new Inferieur();
        tq.setCondition(inf);
        Idf i2 = new Idf(TDSi);
        Idf n = new Idf(TDSn);
        inf.setFilsGauche(i2);
        inf.setFilsDroit(n);

        Bloc bloc = new Bloc();
        tq.setBloc(bloc);

        Ecrire ecr = new Ecrire();
        Idf i3 = new Idf(TDSi);
        ecr.ajouterUnFils(i3);
        bloc.ajouterUnFils(ecr);

        Affectation aff2 = new Affectation();
        bloc.ajouterUnFils(aff2);
        Idf i4 = new Idf(TDSi);
        aff2.setFilsGauche(i4);
        Plus plus = new Plus();
        aff2.setFilsDroit(plus);
        plus.setFilsDroit(new Const(1));
        plus.setFilsGauche(new Idf(TDSi));

        String res = new GenerateCode().generateUASM(prg, symbolTable);
        System.out.println(res);
		TxtAfficheur.afficher(prg);

    }
}
