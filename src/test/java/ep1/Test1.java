package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test1 {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();
        Prog prg = new Prog();
        SymbolTableValueFunction TDSmain = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(TDSmain);
        Fonction main = new Fonction(TDSmain);
        prg.ajouterUnFils(main);

        String res = new GenerateCode().generateUASM(prg, symbolTable);
        System.out.println(res);
		TxtAfficheur.afficher(prg);
    }
}
