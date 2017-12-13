package package3;



import java.io.InputStream;
import java.util.Scanner;

import javax.imageio.ImageTypeSpecifier;

import java.io.FileInputStream;

/** A simple stack-based interpreter */
public class Interpreter {
    public static final int DEFAULT_OPERAND_STACK_SIZE = 100;
    public static final int DEFAULT_CALL_STACK_SIZE = 1000;

    public Interpreter(int ip2,Instruction2[] code,int codesize,Symbol[] arraysymbol)//构造函数给出起始地址还有代码
    {
    	this.ip=ip2;
    	this.code2=code;
    	this.codeSize2=codesize;
    	this.arraysymbol2=arraysymbol;
    }
    
    int ip;             // instruction pointer register
    Instruction[] code;        // byte-addressable code memory.
    int codeSize;
    Symbol[] arraysymbol;
   
    int ip2;
    Instruction2[] code2;
    int codeSize2;
    Symbol[] arraysymbol2;
    
    
    int[] operands = new int[DEFAULT_OPERAND_STACK_SIZE];
    int sp = -1;        // stack pointer register
	
    /** Stack of stack frames, grows upwards */
    StackFrame[] calls = new StackFrame[DEFAULT_CALL_STACK_SIZE];
    int fp = -1;        // frame pointer register
    
    boolean flag=true;
    
    public void interpret()
    {
    	
       //Instruction currentIns=code[ip];
    	while(ip<codeSize2&&ip>=0) {
    		Instruction2 currentIns=code2[ip];
    		ip++;
    		switch(currentIns.f) {
    			case Instruction2.LIT:
    				operands[++sp]=currentIns.a;
    				break;
    				
    			case Instruction2.LOD:
    				int le=currentIns.l;
    				int stackp=fp;
    				while(le!=0)
    				{
    					le--;
    					stackp--;
    				}
    				operands[++sp]=calls[stackp].list.get(currentIns.a-3);	
    				break;
    				
    			case Instruction2.STO:
    				int le2=currentIns.l;
    				int stackp2=fp;
    				while(le2!=0)
    				{
    					le2--;
    					stackp2--;
    				}
    				calls[stackp2].list.set(currentIns.a-3,operands[sp--]);	
    				break;
    			case Instruction2.CAL:
    				StackFrame f = new StackFrame(null, ip,0);
    				calls[++fp]=f;
    				calls[fp].jingtailian=fp-1;
    				calls[fp].dongtailian=fp-1;
    				calls[fp].level=calls[fp-1].level+1;
    				ip=currentIns.a;
    				
    				break;
    			case Instruction2.INT:
    				if(flag) {
    					StackFrame f1 = new StackFrame(null, -1,currentIns.a);
    					f1.jingtailian=-1;
    					f1.dongtailian=-1;
    					f1.symbolCount=currentIns.a;
    					calls[++fp]=f1;
    					for(int i=0;i<currentIns.a;i++)
    						calls[fp].list.add(0);
    					flag=false;
    					f1.level=0;
    				}
    				else {
    					calls[fp].symbolCount=currentIns.a;	
    					for(int i=0;i<currentIns.a;i++)
    						calls[fp].list.add(0);
					}
    				break;
    			case Instruction2.JMP:
    				ip=currentIns.a;
    				break;
    			case Instruction2.JPC:
    				int value=operands[sp--];
    				if(value==0) {
    					ip=currentIns.a;
    				}
    				break;
    			case Instruction2.OPR:
    				int a,b;
    				switch(currentIns.a) {
    					case Instruction2.jia:
    						a=operands[sp--];
    						b=operands[sp--];
    						operands[++sp]=a+b;
    						break;
    					case Instruction2.jian:
    						a=operands[sp--];
    						b=operands[sp--];
    						operands[++sp]=b-a;
    						break;
    					case Instruction2.cheng:
    						a=operands[sp--];
    						b=operands[sp--];
    						operands[++sp]=a*b;
    						break;
    					case Instruction2.chu:
    						a=operands[sp--];
    						b=operands[sp--];
    						operands[++sp]=b/a;
    						break;
    					case Instruction2.dayu:
    						a=operands[sp--];
    						b=operands[sp--];
    						if(b-a<=0)
    							operands[++sp]=0;
    						else
    							operands[++sp]=1;
    						break;
    					case Instruction2.dayudengyu:
    						a=operands[sp--];
    						b=operands[sp--];
    						if(b-a>=0)
    							operands[++sp]=1;
    						else 
    							operands[++sp]=0;
    						break;
    					case Instruction2.xiaoyu:
    						a=operands[sp--];
    						b=operands[sp--];
    						if(b-a<0)
    							operands[++sp]=1;
    						else
    							operands[++sp]=0;
    						break;
    					case Instruction2.xiaoyudengyu:
    						a=operands[sp--];
    						b=operands[sp--];
    						if(b-a<=0)
    							operands[++sp]=1;
    						else 
    							operands[++sp]=0;
    						break;
    					case Instruction2.dengyu:
    						a=operands[sp--];
    						b=operands[sp--];
    						if(b-a==0)
    							operands[++sp]=1;
    						else 
    							operands[++sp]=0;
    						break;	
    					case Instruction2.budengyu:
    						a=operands[sp--];
    						b=operands[sp--];
    						if(b!=a)
    							operands[++sp]=1;
    						else 
    							operands[++sp]=0;
    						break;
    					case Instruction2.read:
    						System.out.println("please in put a number");
    						Scanner s = new Scanner(System.in);
    						int v=s.nextInt();
    						operands[++sp]=v;
    						//arraysymbol[currentIns.index].value=v;
    						break;
    					case Instruction2.write:
    						int k=operands[sp--];
    						//int k=arraysymbol[currentIns.index].value;
    						System.out.println(k);
    						break;	
    					case Instruction2.ret:
    						 StackFrame fr = calls[fp--];    // pop stack frame
    						 System.out.println("stackFrame");
    						 
    						 System.out.println("reture address: "+fr.returnAddress);
    						 System.out.println("static link: "+fr.jingtailian);
    						 System.out.println("dynamic link: "+fr.dongtailian);
    						 for(int i=0;i<fr.symbolCount-3;i++)
    							 System.out.print(fr.list.get(i)+" ");
    						 System.out.println();
    						 
    		                 ip = fr.returnAddress;          // branch to ret addr
    		                 break;
    					default:
    						throw new Error("Here need a normal parameter behind OPR");
    						
    				}
    				
    		}
    			
    	}
    }
    
}
