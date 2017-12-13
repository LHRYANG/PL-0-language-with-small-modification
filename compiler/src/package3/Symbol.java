package package3;


public class Symbol { // A generic programming language symbol
   public String name;      // All symbols at least have a name
   public String type;      // type can be procedure, variable,constant
   public Scope scope;      // All symbols know what scope contains them.
   public int value;
    
   public int index;     //this is used to record the index of Symbols in Data stack
   public int address;   // if this symbol is function,it is used to point to the entry.
   
   public int level;
   public int addr;
   
   public int index2;
   
   
   
    public Symbol(String name) { this.name = name; }
    public Symbol(String name, String type) { this(name); this.type = type; }
    public Symbol(String name,String type,int index) {this.name=name;this.type=type;this.index=index;}
    
    public Symbol(String name,String type,int value,int index) {this.name=name;this.type=type;this.value=value;this.index=index;}
    public String getName() { return name; }
    
    public Symbol(String name,String type, int index,int address,int value)
    {
    	this.name=name;
    	this.type=type;
    	this.index=index;
    	this.address=address;
    	this.value=value;
    }
    public String toString() {
    	
       return "name: "+this.name+" type: "+this.type+" value: "+this.value+" index: "+this.index+" address: "+this.address;
    				
     }
}
