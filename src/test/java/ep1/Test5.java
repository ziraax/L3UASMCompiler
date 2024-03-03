package ep1;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class Test5 {
    public static void main(String[] arguments) {
        SymbolTable symbolTable = new SymbolTable();
        Prog program = new Prog();
        
        SymbolTableValueInt intValue1 = new SymbolTableValueInt("i", ETypes.entier);
        symbolTable.addValue(intValue1);
        
        Idf idf_i = new Idf(intValue1);
        
        SymbolTableValueFunction mainFunctionTable = new SymbolTableValueFunction("main", ETypes.vide);
        symbolTable.addValue(mainFunctionTable);
        
        Fonction mainFunction = new Fonction(mainFunctionTable);
        program.ajouterUnFils(mainFunction);
        
        Lire lire = new Lire();
        Affectation assignment1 = new Affectation();
        assignment1.setFilsGauche(idf_i);
        assignment1.setFilsDroit(lire);
        mainFunction.ajouterUnFils(assignment1);
        
        Si si = new Si(1);
        Superieur greaterThan = new Superieur();
        
        mainFunction.ajouterUnFils(si);
        si.setCondition(greaterThan);
        greaterThan.setFilsGauche(idf_i);
        greaterThan.setFilsDroit(new Const(10));

        Ecrire write1 = new Ecrire();
        Ecrire write2 = new Ecrire();
        Bloc blockThen = new Bloc();
        blockThen.ajouterUnFils(write1);
        Bloc blockElse = new Bloc();
        blockElse.ajouterUnFils(write2);
        si.setBlocAlors(blockThen);
        si.setBlocSinon(blockElse);

        write1.setLeFils(new Const(1));
        write2.setLeFils(new Const(2));
        
        String result = new GenerateCode().generateUASM(program, symbolTable);
        System.out.println(result);
        TxtAfficheur.afficher(program);
    }
}
