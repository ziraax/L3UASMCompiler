package ep1;
import ep1.SymbolTableValue.ECat;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

public class GenerateCode {
    public String generateUASM(Noeud a, SymbolTable symbolTable) {
        String res = "";
        res += ".include beta.uasm\n.include intio.uasm\n.options tty\n";
        res += "\tCMOVE(pile, SP)\n\tBR(debut)\ndata:\n";
        res += generateData(symbolTable);
        res += "debut:\n\tCALL(main)\n";
        res += generateCode(a, symbolTable);
        res += "\tHALT()\npile:\n";
        return res;
    }
    
    public String generateData(SymbolTable symbolTable) {
        String res = "";

        for(SymbolTableValue entry : symbolTable.getValues()) {
            if(entry.cat == ECat.Eglobal) {
                SymbolTableValueInt var = (SymbolTableValueInt) entry;
                res += "\t" + var.name + " : LONG(" + var.value + ")\n";
            }
        }
        return res;
    }
    
    public String generateCode(Noeud a, SymbolTable symbolTable) {
    	String res = "";
    	
        for(Noeud fils : a.getFils()) {
            res += generateFunction((Fonction) fils, symbolTable);
        }
        return res;
    }
    
    public String generateFunction(Fonction function, SymbolTable symbolTable) {
    	String res = "";
    	SymbolTableValueFunction f = (SymbolTableValueFunction) function.getValeur();
    	
    	res += f.name + ":\n";
    	int nbLoc = f.nbLoc;
    	
        res += "\tPUSH(LP)\n\tPUSH(BP)\n";
        res += "\tMOVE(SP,BP)\n";
        res += "\tALLOCATE(" + nbLoc + ")\n";
        
    	for(Noeud fils : function.getFils()) {
            res += generateInstruction(fils, symbolTable); 
        }
    	
    	String label = "RET_" + f.name;
    	res += label + ":\n\tDEALLOCATE(" + nbLoc + ")\n\tPOP(BP)\n\tPOP(LP)\n";
        if(f.name.equals("main")) {
            res += "\tHALT()\n";
        } else {
            res += "\tRTN()\n";            
        };
        
        return res;
    }
    
    public String generateInstruction(Noeud a, SymbolTable symbolTable) {
    	String res = "";
    	
        switch (a.getCat()) {
	        case AFF:
		        res += generateAffectation((Affectation) a, symbolTable);
		        break;
	    
	        case SI:
		        res += generateIf((Si) a, symbolTable);
		        break;
	
	        case ECR:
		        res += generateWrite((Ecrire) a, symbolTable);
		        break;
	
	        case TQ:
		        res += generateWhile((TantQue) a, symbolTable);
		        break;
	
	        case RET:
		        res += generateReturn((Retour) a, symbolTable);
		        break;
	
	        case APPEL: 
		        res += generateCall((Appel) a, symbolTable);
		        break;

	        default:
	            return "";
	    }
	    return res;
    }
    
    public String generateAffectation(Affectation a, SymbolTable symbolTable) {
    	String res = "";
    	Idf filsGauche  = (Idf) a.getFilsGauche();
    	SymbolTableValueInt symbolTableFilsGauche = (SymbolTableValueInt) filsGauche.getValeur();
    	
        res += generateExpression(a.getFilsDroit(), symbolTable);
        res += "\tPOP(r0)\n";
        
        switch (symbolTableFilsGauche.cat) {
        	case Eglobal:
                res += "\tST(r0," + symbolTableFilsGauche.name + ")\n";
                break;
        	case Elocal:
            case Eparam:
            	int offset = symbolTableFilsGauche.getOffset();
            	res += "\tPUTFRAME(r0," + offset +")\n";
            	break;
            default:
                break;
        }
        return res;
    }
    
    public String generateExpression(Noeud a, SymbolTable symbolTable) {
    	if(a == null) return "";
    	
    	String res = "";
    	switch(a.getCat())  {
	        case CONST:
	            res += generateConst((Const) a);
	            break;
	        case IDF:
	            res += generateIdf((Idf) a);
	            break;
	        case LIRE:
	            res += generateRead((Lire) a);
	            break;
	        case APPEL:
	            res += generateCall((Appel) a, symbolTable);
	            break;
	        case PLUS:
	            res += generateAdd((Plus) a, symbolTable);
	            break;
	        case DIV:
	            res += generateDiv((Division) a, symbolTable);
	            break;
	        case MUL:
	            res += generateMul((Multiplication) a, symbolTable);
	            break;
	        case MOINS:
	            res += generateSub((Moins) a, symbolTable);
	            break;
	        default :
	            return "";
    	}
    	return res;
    }
    
    public String generateConst(Const a) {
    	String res = "";
        res += "\tCMOVE(" +  a.getValeur() + ",r0)\n\tPUSH(r0)\n";
        return res;
    }
    
    public String generateIdf(Idf a) {
        String res = "";
        SymbolTableValueInt value = (SymbolTableValueInt) a.getValeur();

        switch (value.cat) {
            case Eglobal:
	            res += "\tLD(" + value.name + ",r0)\n\tPUSH(r0)\n";
	            break;
            case Elocal:
            case Eparam:
	            int offset = value.getOffset();
	            res += "\tGETFRAME(" + offset +",r0)\n\tPUSH(r0)\n";
	            break;
            default:
                return "";
        }
        return res;
    }
    
    public String generateRead(Lire a) {
    	String res = "";
        res += "\tRDINT()\n\tPUSH(r0)\n";
        return res;
    }

    public String generateAdd(Plus a, SymbolTable symbolTable) {
        String res = "";
        res += generateExpression(a.getFilsGauche(), symbolTable);
        res += generateExpression(a.getFilsDroit(), symbolTable);
        res += "\tPOP(r2)\n\tPOP(r1)\n\t" + "ADD" + "(r1,r2,r3)\n\tPUSH(r3)\n";
        return res;
    }
    
    public String generateDiv(Division a, SymbolTable symbolTable) {
        String res = "";
        res += generateExpression(a.getFilsGauche(), symbolTable);
        res += generateExpression(a.getFilsDroit(), symbolTable);
        res += "\tPOP(r2)\n\tPOP(r1)\n\t" + "DIV" + "(r1,r2,r3)\n\tPUSH(r3)\n";
        return res;
    }
    
    public String generateMul(Multiplication a, SymbolTable symbolTable) {
        String res = "";
        res += generateExpression(a.getFilsGauche(), symbolTable);
        res += generateExpression(a.getFilsDroit(), symbolTable);
        res += "\tPOP(r2)\n\tPOP(r1)\n\t" + "MUL" + "(r1,r2,r3)\n\tPUSH(r3)\n";
        return res;
    }
    
    public String generateSub(Moins a, SymbolTable symbolTable) {
        String res = "";
        res += generateExpression(a.getFilsGauche(), symbolTable);
        res += generateExpression(a.getFilsDroit(), symbolTable);
        res += "\tPOP(r2)\n\tPOP(r1)\n\t" + "SUB" + "(r1,r2,r3)\n\tPUSH(r3)\n";
        return res;
    }
    
    public String generateIf(Si a, SymbolTable symbolTable) {
    	String res = "";
    	
    	res += generateBool(a.getCondition(), symbolTable);
    	res += "si" + a.getValeur() + ":\n";
    	res += "\tPOP(r0)\n";
    	res += "\tBF(r0,sinon" + a.getValeur() + ")\n";
    	res += "alors" + a.getValeur() + ":\n";
    	res += generateBloc(a.getBlocAlors(), symbolTable);
    	res += "\tBR(finsi" + a.getValeur() + ")\n";
    	res += "sinon" + a.getValeur() + ":\n";
    	res += generateBloc(a.getBlocSinon(), symbolTable);
    	res += "finsi" + a.getValeur() + ":\n";
    	
    	return res;
    }
    
    public String generateBool(Noeud a, SymbolTable symbolTable) {
    	String res = "";
    	
    	switch(a.getCat()) {
    		case INF: res += generateLessThan((Inferieur) a, symbolTable);
    			break;
    		case SUP: res += generateSup((Superieur)a, symbolTable);
    			break;
    		case INFE: res += generateLessEqualThan((InferieurEgal) a, symbolTable);
    			break;
    		case SUPE: res += generateSupEqual((SuperieurEgal) a, symbolTable);
    			break;
    		case EG: res += generateEqual((Egal) a, symbolTable);
    			break;
    		case DIF: res += generateDiff((Different) a, symbolTable);
    			break;
    		default :
    			return "";
    	}
    	
    	return res;
    }
    
    public String generateLessThan(Inferieur a, SymbolTable symbolTable) {
    	String res = "";
    	
    	res += generateExpression(a.getFilsGauche(), symbolTable);
    	res += generateExpression(a.getFilsDroit(), symbolTable);
    	res += "\tPOP(r2)\n\tPOP(r1)\n\tCMPLT(r1,r2,r3)\n\tPUSH(r3)\n";
    	
    	return res;
    }
    
    public String generateLessEqualThan(InferieurEgal a, SymbolTable symbolTable) {
    	String res = "";
    	
    	res += generateExpression(a.getFilsGauche(), symbolTable);
    	res += generateExpression(a.getFilsDroit(), symbolTable);
    	res += "\tPOP(r2)\n\tPOP(r1)\n\tCMPLE(r1,r2,r3)\n\tPUSH(r3)\n";
    	
    	return res;
    }
    
    public String generateSup(Superieur a, SymbolTable symbolTable) {
    	String res = "";
    	
    	res += generateExpression(a.getFilsGauche(), symbolTable);
    	res += generateExpression(a.getFilsDroit(), symbolTable);
    	res += "\tPOP(r2)\n\tPOP(r1)\n\tCMPLT(r2,r1,r3)\n\tPUSH(r3)\n";
    	
    	return res;
    }
    
    public String generateSupEqual(SuperieurEgal a, SymbolTable symbolTable) {
    	String res = "";
    	
    	res += generateExpression(a.getFilsGauche(), symbolTable);
    	res += generateExpression(a.getFilsDroit(), symbolTable);
    	res += "\tPOP(r2)\n\tPOP(r1)\n\tCMPLE(r2,r1,r3)\n\tPUSH(r3)\n";
    	
    	return res;
    }
    
    public String generateEqual(Egal a, SymbolTable symbolTable) {
    	String res = "";
    	
    	res += generateExpression(a.getFilsGauche(), symbolTable);
    	res += generateExpression(a.getFilsDroit(), symbolTable);
    	res += "\tPOP(r2)\n\tPOP(r1)\n\tCMPEQ(r1,r2,r3)\n\tPUSH(r3)\n";
    	
    	return res;
    }
    
    public String generateDiff(Different a, SymbolTable symbolTable) {
    	String res = "";
    	
    	res += generateExpression(a.getFilsGauche(), symbolTable);
    	res += generateExpression(a.getFilsDroit(), symbolTable);
    	res += "\tPOP(r2)\n\tPOP(r1)\n\tCMPEQ(r1,r2,r3)\n\tXORC(r3,1,r4)\n\tPUSH(r4)\n";
        //CMPEQ(r1,r2,r3)nXORC(r3,1,r4) := not equal
    	
    	return res;
    }
    
    public String generateBloc(Bloc a, SymbolTable symbolTable) {
    	String res = "";
    	
    	for(Noeud fils : a.getFils()) {
            res += generateInstruction(fils, symbolTable); 
        }
    	
    	return res;
    }
    
    public String generateWrite(Ecrire a, SymbolTable symbolTable) {

    	String res = "";
    	res += generateExpression(a.getLeFils(), symbolTable);
    	res += "\tPOP(r0)\n\tWRINT()\n";
    	
    	return res;
    }
    
    public String generateWhile(TantQue a, SymbolTable symbolTable) {
        String res = "";
        res += "tantque" + a.getValeur() + ":\n";
        res += generateBool(a.getCondition(), symbolTable);
        res += "\tPOP(r0)\n\tBEQ(r0,fintantque" + a.getValeur() +")\n";
        res += generateBloc(a.getBloc(), symbolTable);
        res += "\tBR(tantque" + a.getValeur() +")\nfintantque" + a.getValeur() + ":\n";

        return res;
    }
    
    public String generateReturn(Retour a, SymbolTable symbolTable) {
        String res = "";
        SymbolTableValueFunction f = (SymbolTableValueFunction) a.getValeur();
        int offset = (-3 - f.nbParam)*4;

        res += generateExpression(a.getLeFils(), symbolTable);
        res += "\tPOP(r0)\n";
        res += "\tPUTFRAME(r0, " + offset + ")\n";
        res += "\tBR(RET_" + f.name + ")\n";
        return res;
    }
    
    public String generateCall(Appel a, SymbolTable symbolTable) {
    	String res = "";
    	int p = 0;

        SymbolTableValueFunction function = (SymbolTableValueFunction) a.getValeur();
    	
    	if(function.type == ETypes.entier){
    		p = 1;
    	}

        res += "\tALLOCATE(" + p + ")\n";
    	
    	for(Noeud fils : a.getFils()) {
    		res += generateExpression(fils, symbolTable);
    	}
    	
    	res += "\tCALL(" + function.name + ")\n";
        res += "\tDEALLOCATE(" + a.getFils().size() + ")\n";
    	return res;
    }
    
    
}
