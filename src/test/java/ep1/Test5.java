package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test5 {
    public static void main(String[] args) {
    	SymbolTable symbolTable = new SymbolTable();
        Prog prg = new Prog();
        
        SymbolTableValueInt TDSi = new SymbolTableValueInt("i", ETypes.entier);
        symbolTable.addValue(TDSi);
        
        Idf i = new Idf(TDSi);
        
        SymbolTableValueFunction TDSmain = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(TDSmain);
        
        Fonction main = new Fonction(TDSmain);
        prg.ajouterUnFils(main);
        
        Lire lire = new Lire();
        Affectation affectation1 = new Affectation();
        affectation1.setFilsGauche(i);
        affectation1.setFilsDroit(lire);
        main.ajouterUnFils(affectation1);
        
        
        Si si = new Si(1);
        Superieur superieur = new Superieur();
        
        main.ajouterUnFils(si);
        si.setCondition(superieur);
        superieur.setFilsGauche(i);
        superieur.setFilsDroit(new Const(10));

        Ecrire ecrire = new Ecrire();
        Ecrire ecrire2 = new Ecrire();
        Bloc blocAlors = new Bloc();
        blocAlors.ajouterUnFils(ecrire);
        Bloc blocSinon = new Bloc();
        blocSinon.ajouterUnFils(ecrire2);
        si.setBlocAlors(blocAlors);
        si.setBlocSinon(blocSinon);

        ecrire.setLeFils(new Const(1));
        ecrire2.setLeFils(new Const(2));
        
        String res = new GenerateCode().generateUASM(prg, symbolTable);
        System.out.println(res);
        TxtAfficheur.afficher(prg);
    }
}
