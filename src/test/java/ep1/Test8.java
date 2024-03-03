package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;
public class Test8 {

	public static void main(String[] args) {

    	SymbolTable symbolTable = new SymbolTable();
		
		SymbolTableValueFunction TDSmain = new SymbolTableValueFunction("main", ETypes.vide);
		symbolTable.addValue(TDSmain);
		SymbolTableValueInt TDSa = new SymbolTableValueInt("a", ETypes.entier);
		symbolTable.addValue(TDSa);
		SymbolTableValueFunction TDSf = new SymbolTableValueFunction("f", ETypes.entier, 2, 1);
		symbolTable.addValue(TDSf);
		SymbolTableValueInt TDSx = new SymbolTableValueInt("x", ETypes.entier, TDSf, 0);
		symbolTable.addValue(TDSx);
		SymbolTableValueIntParam TDSi = new SymbolTableValueIntParam("i", ETypes.entier, TDSf, 0);
		symbolTable.addValue(TDSi);
		SymbolTableValueIntParam TDSj = new SymbolTableValueIntParam("j", ETypes.entier, TDSf, 1);
		symbolTable.addValue(TDSj);
		
		Prog prg = new Prog();
		Fonction main = new Fonction(TDSmain);
		prg.ajouterUnFils(main);
		Fonction f = new Fonction(TDSf);
		prg.ajouterUnFils(f);
		
		//f
		Affectation aff2 = new Affectation();
		Plus plus1 = new Plus();
		plus1.setFilsGauche(new Idf(TDSi));
		plus1.setFilsDroit(new Idf(TDSj));
		aff2.setFilsGauche(new Idf(TDSx));
		aff2.setFilsDroit(plus1);
		
		Retour retour = new Retour(TDSf);
		Plus plus2 = new Plus();
		plus2.setFilsGauche(new Idf(TDSx));
		plus2.setFilsDroit(new Const(10));
		retour.setLeFils(plus2);
		
		f.ajouterUnFils(aff2);
		f.ajouterUnFils(retour);
		
		
		//main
		Affectation aff1 = new Affectation();
		Appel appel = new Appel(TDSf);
		appel.ajouterUnFils(new Const(1));
		appel.ajouterUnFils(new Const(2));
		aff1.setFilsGauche(new Idf(TDSa));
		aff1.setFilsDroit(appel);
		
		Ecrire ecrire = new Ecrire();
		ecrire.setLeFils(new Idf(TDSa));
		
		main.ajouterUnFils(aff1);
		main.ajouterUnFils(ecrire);
		
		
		String res = new GenerateCode().generateUASM(prg, symbolTable);
		System.out.println(res);

		TxtAfficheur.afficher(prg);
	}
}