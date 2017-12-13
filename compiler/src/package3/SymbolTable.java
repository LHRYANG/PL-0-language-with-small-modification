package package3;


public class SymbolTable {
	public BaseScope globals =new BaseScope(null,"global");
	
    public BaseScope currentScope=globals;
    
    public SymbolTable() { ;}
   
    public String toString() { return globals.toString(); }
}
