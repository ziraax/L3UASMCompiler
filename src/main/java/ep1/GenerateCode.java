package ep1;
import ep1.SymbolTableValue.ECat;
import ep1.SymbolTableValue.ETypes;
import fr.ul.miage.arbre.*;

//Class responsible for generating UASM (Universal Assembly) code
public class GenerateCode {
	
    /**
     * Generates UASM code for the entire program.
     * 
     * @param a The root node of the AST.
     * @param symbolTable The symbol table containing information about variables and functions.
     * @return The generated UASM code.
     */
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
    
    /**
     * Generates the data section of UASM code containing global variable declarations.
     * 
     * @param symbolTable The symbol table containing global variables.
     * @return The generated data section code.
     */
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
    
    /**
     * Generates UASM code for all functions in the program.
     * 
     * @param a The root node of the AST.
     * @param symbolTable The symbol table containing function information.
     * @return The generated UASM code for functions.
     */
    public String generateCode(Noeud a, SymbolTable symbolTable) {
    	String res = "";
    	
        for(Noeud fils : a.getFils()) {
            res += generateFunction((Fonction) fils, symbolTable);
        }
        return res;
    }
    
    /**
     * Generates UASM code for an individual function.
     * 
     * @param function The function node from the AST.
     * @param symbolTable The symbol table containing function information.
     * @return The generated UASM code for the function.
     */
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
    
    // Method to generate code for individual instructions
    public String generateInstruction(Noeud a, SymbolTable symbolTable) {
    	String res = "";
    	
        switch (a.getCat()) {
	        case AFF:
		        res += generateAssignement((Affectation) a, symbolTable);
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
    
    /**
     * Generates UASM code for an assignment operation.
     * 
     * @param a The assignment node from the AST.
     * @param symbolTable The symbol table containing variable information.
     * @return The generated UASM code for the assignment operation.
     */
    public String generateAssignement(Affectation a, SymbolTable symbolTable) {
    	String res = "";
        // Extract the left-hand side of the assignment
    	Idf filsGauche  = (Idf) a.getFilsGauche();
        // Retrieve information about the variable from the symbol table
    	SymbolTableValueInt symbolTableFilsGauche = (SymbolTableValueInt) filsGauche.getValeur();
    	
        // Generate UASM code for the expression on the right-hand side of the assignment
        res += generateExpression(a.getFilsDroit(), symbolTable);
        // Pop the value of the expression from the stack into register r0
        res += "\tPOP(r0)\n";
        
        // Determine the type of the left-hand side (global, local, or parameter)
        switch (symbolTableFilsGauche.cat) {
        	case Eglobal:
                // Store the value into the memory location of the global variable
                res += "\tST(r0," + symbolTableFilsGauche.name + ")\n";
                break;
        	case Elocal:
            case Eparam:
                // Calculate the offset of the local or parameter variable from the base pointer
            	int offset = symbolTableFilsGauche.getOffset();
                // Store the value into the memory location at the calculated offset from the base pointer
            	res += "\tPUTFRAME(r0," + offset +")\n";
            	break;
            default:
                break;
        }
        return res;
    }
    
    /**
     * Generates UASM code for an expression node in the Abstract Syntax Tree (AST).
     * This method handles different types of expressions such as constants, identifiers,
     * arithmetic operations, input/output operations, and function calls.
     * 
     * @param a The expression node from the AST.
     * @param symbolTable The symbol table containing variable and function information.
     * @return The generated UASM code for the expression.
     */
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
    
    /**
     * Generates UASM code for an identifier node in the Abstract Syntax Tree (AST).
     * 
     * @param a The identifier node from the AST.
     * @return The generated UASM code for the identifier.
     */
    public String generateIdf(Idf a) {
        String res = "";
        // Retrieve information about the identifier from the symbol table
        SymbolTableValueInt value = (SymbolTableValueInt) a.getValeur();

        // Determine the category of the identifier (global, local, or parameter)
        switch (value.cat) {
            case Eglobal:
                // Load the value of the global variable into register r0 and push it onto the stack
	            res += "\tLD(" + value.name + ",r0)\n\tPUSH(r0)\n";
	            break;
            case Elocal:
            case Eparam:
                // Calculate the offset of the local or parameter variable from the base pointer
	            int offset = value.getOffset();
	            // Retrieve the value of the local or parameter variable from the stack frame at the calculated offset
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
    
    /**
     * Generates UASM code for a block of statements.
     * 
     * @param a The block node from the Abstract Syntax Tree (AST).
     * @param symbolTable The symbol table containing variable and function information.
     * @return The generated UASM code for the block.
     */
    public String generateBloc(Bloc a, SymbolTable symbolTable) {
        String res = "";
        
        // Iterate through each statement in the block and generate code
        for(Noeud fils : a.getFils()) {
            res += generateInstruction(fils, symbolTable); 
        }
        
        return res;
    }
    
    /**
     * Generates UASM code for a write operation.
     * 
     * @param a The write node from the Abstract Syntax Tree (AST).
     * @param symbolTable The symbol table containing variable and function information.
     * @return The generated UASM code for the write operation.
     */
    public String generateWrite(Ecrire a, SymbolTable symbolTable) {
        String res = "";
        
        // Generate code for the expression to be written
        res += generateExpression(a.getLeFils(), symbolTable);
        res += "\tPOP(r0)\n\tWRINT()\n"; // Write the expression's value
        
        return res;
    }
    
    /**
     * Generates UASM code for a while loop.
     * 
     * @param a The while loop node from the Abstract Syntax Tree (AST).
     * @param symbolTable The symbol table containing variable and function information.
     * @return The generated UASM code for the while loop.
     */
    public String generateWhile(TantQue a, SymbolTable symbolTable) {
        String res = "";
        
        // Generate code for the loop condition and label
        res += "tantque" + a.getValeur() + ":\n";
        res += generateBool(a.getCondition(), symbolTable);
        res += "\tPOP(r0)\n\tBEQ(r0,fintantque" + a.getValeur() +")\n";
        
        // Generate code for the loop body and branching back to the loop
        res += generateBloc(a.getBloc(), symbolTable);
        res += "\tBR(tantque" + a.getValeur() +")\nfintantque" + a.getValeur() + ":\n";

        return res;
    }
    
    /**
     * Generates UASM code for a return statement.
     * 
     * @param a The return node from the Abstract Syntax Tree (AST).
     * @param symbolTable The symbol table containing variable and function information.
     * @return The generated UASM code for the return statement.
     */
    public String generateReturn(Retour a, SymbolTable symbolTable) {
        String res = "";
        SymbolTableValueFunction f = (SymbolTableValueFunction) a.getValeur();
        int offset = (-3 - f.nbParam)*4;

        // Generate code for the expression to be returned and store it in the appropriate stack frame location
        res += generateExpression(a.getLeFils(), symbolTable);
        res += "\tPOP(r0)\n";
        res += "\tPUTFRAME(r0, " + offset + ")\n";
        res += "\tBR(RET_" + f.name + ")\n";
        return res;
    }
    
    /**
     * Generates UASM code for a function call.
     * 
     * @param a The function call node from the Abstract Syntax Tree (AST).
     * @param symbolTable The symbol table containing variable and function information.
     * @return The generated UASM code for the function call.
     */
    public String generateCall(Appel a, SymbolTable symbolTable) {
        String res = "";
        int p = 0;

        SymbolTableValueFunction function = (SymbolTableValueFunction) a.getValeur();
        
        // Determine the number of parameters to allocate space for
        if(function.type == ETypes.entier){
            p = 1;
        }

        // Allocate space for parameters on the stack
        res += "\tALLOCATE(" + p + ")\n";
        
        // Generate code for each parameter expression
        for(Noeud fils : a.getFils()) {
            res += generateExpression(fils, symbolTable);
        }
        
        // Call the function and deallocate space for parameters
        res += "\tCALL(" + function.name + ")\n";
        res += "\tDEALLOCATE(" + a.getFils().size() + ")\n";
        return res;
    }
    
    
}
