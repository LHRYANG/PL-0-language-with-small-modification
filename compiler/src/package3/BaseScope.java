package package3;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseScope implements Scope {
	Scope enclosingScope; // null if global (outermost) scope
	//Map<String, Symbol> symbols = new LinkedHashMap<String, Symbol>(); for relocation, we use Array not Map
	ArrayList<Symbol> symbols=new ArrayList<Symbol>();
	String scopename;
	
	
	public BaseScope(Scope enclosingScope,String scopename) {
		this.enclosingScope=enclosingScope;
		this.scopename=scopename;
	}
    public BaseScope(Scope enclosingScope) { this.enclosingScope = enclosingScope;	}

    public Symbol resolve(String name) {
		//Symbol s = symbols.get(name);
    	Symbol s;
    	int len=symbols.size();
    	for(int i=0;i<len;i++)
    	{
    		s=symbols.get(i);
    		if(s.name.equals(name))
    			return s;
    	}
        //if ( s!=null ) return s;
		// if not here, check any enclosing scope
		if ( enclosingScope != null ) return enclosingScope.resolve(name);
		return null; // not found
	}

	public void define(Symbol sym) {
		symbols.add(sym);
		sym.scope = this; // track the scope in each symbol
	}
	
	
	
	
	public String getScopeName() {
		return scopename;
	}
    public Scope getEnclosingScope() { return enclosingScope; }

	public String toString() {
		
		String s="";
		for(int i=0;i<symbols.size();i++)
		{
			s=s+symbols.get(i).toString();
			s=s+'\n';
			
		}
		return s;
	}
}
