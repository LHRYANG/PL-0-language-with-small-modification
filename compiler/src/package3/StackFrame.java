package package3;

import java.util.ArrayList;

public class StackFrame {
    Symbol sym; // associated with which function?
   public int symbolCount;
   public int jingtailian;
   public int dongtailian;
   
    public int level;
    int returnAddress;  // the instruction following the call
    public StackFrame(Symbol sym, int returnAddress,int symbolCount) {
        this.sym = sym;
        this.returnAddress = returnAddress;
        this.symbolCount=symbolCount;
    }
    
   public ArrayList<Integer> list=new ArrayList<Integer>();
    
    ArrayList<Symbol> symbols=new ArrayList<Symbol>();
    
}
