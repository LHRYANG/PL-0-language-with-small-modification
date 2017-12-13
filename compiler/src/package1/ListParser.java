package package1;

import java.awt.List;
import java.awt.print.Printable;
import java.util.ArrayList;

import javax.management.timer.TimerMBean;
import javax.swing.LookAndFeel;
import javax.xml.parsers.FactoryConfigurationError;

import org.omg.CosNaming.IstringHelper;

import package3.BaseScope;
import package3.Instruction;
import package3.Instruction2;
import package3.Symbol;
import package3.Symbol2;
import package3.SymbolTable;

//在这个过程中已经完成了解析树的构建


public class ListParser extends Parser {
    public ListParser(Lexer input) { super(input); }
   // public String proname=" ";
    /*
     * This is about how to built table!!
     * */
    
    SymbolTable table=new SymbolTable();
    
    String[] proname=new String[20];
    int pronamep=-1;
    
    SymbolTable table2=new SymbolTable();
    /*
     * This is about how to build parse tree.
     * 
     * 
     * */
    
    ParseTree root;
    ParseTree currentNode;
    
    int ip=-1;
    int ip2=-1;
    public ArrayList<Symbol> arraysymbol=new ArrayList<Symbol>(); // 这个表格是一个统一的表格，用于记录各种符号所在的位置
    int index=0;                               //这个是符号表当前填入的位置
    
    boolean flag=false;
    /*
     * 下面是加上的代码
     */
    public ArrayList<Symbol2> arrarsymbol2=new ArrayList<Symbol2>(); 
    int index2=0;
    int level=-1;
    int relative=0;
    
    
    public ArrayList<Instruction2> code2=new ArrayList<Instruction2>();
    int codeindex2=0;
    /*
     * 上面是加上的代码
     * */
    
    
    // 这个变量是为了记录每个每个过程所拥有的符号的个数
    int symbolCount=0;
    public ArrayList<Integer> symbolCountArray=new ArrayList<Integer>();
    
    
    public ArrayList<Instruction> code=new ArrayList<Instruction>();
    int codeindex=0; //这个是代码的地址
    
    
    
    public void match(int x){
    	currentNode.addChild(lookahead);
    	super.match(x);
    }
    
    
    
    public void program() {
    	RuleNode r=new RuleNode("program");
    	if(root==null)
    		root=r;
    	else
    		currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	subprogram();
    	match(Lexer.EOF_TYPE);
    	
    	currentNode=_save;
    }
    
    
    public void subprogram() {
    	
    	/*
    	 * add
    	 * */
    	level++;
    	relative=3;
    	/*
    	 * add
    	 * */
    	
    	RuleNode r=new RuleNode("subprogram");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	if(lookahead.type==ListLexer.CONST)
    		constDel();//常量说明
    	if(lookahead.type==ListLexer.VAR) 
    		variaDel();  //变量说明
    	
    	
    	//ip=codeindex;
    	if(lookahead.type==ListLexer.PROCEDURE)
    	{
    		flag=true;
    		symbolCount++;
    		
    		//Instruction2 instruction2=new Instruction2(Instruction2.INT,0,symbolCount+3);
        	//code2.add(instruction2);
        	//codeindex2++;
    		
        	symbolCountArray.add(symbolCount);
    		symbolCount=0;
    		proceDel();  //过程说明
    		
    		
    	
    	}
    	
    	if(pronamep>=0) {
    	Symbol symbol=new Symbol(proname[pronamep],"procedure",index++,codeindex,-1);
    	symbol.index2=codeindex2;
    	//System.out.println("codeindex2"+codeindex2);
    	symbol.level=level;
    	symbol.addr=relative;
    	arraysymbol.add(symbol);
    	
    	 /*
    	 * add
    	 * */
    	Symbol2 symbol2=new Symbol2(proname[pronamep], "procedure", level,relative++);
    	arrarsymbol2.add(symbol2);
    	pronamep--;
    	
    	 /*
    	 * add
    	 * */
    	
    	
    	table.currentScope.getEnclosingScope().define(symbol);
    	}
    	Instruction2 instruction3=new Instruction2(Instruction2.INT,0,symbolCount+3);
		code2.add(instruction3);
		codeindex2++;
		
		//System.out.println("helloworld");
		//System.out.println(symbolCountArray.size());
		if(symbolCountArray.size()>0) {
			symbolCount=symbolCountArray.get(symbolCountArray.size()-1);
			symbolCountArray.remove(symbolCountArray.size()-1);
		}
		/*
    	if(flag) {
    		Instruction2 instruction3=new Instruction2(Instruction2.INT,0,symbolCount+3);
    		code2.add(instruction3);
    		codeindex2++;
    		flag=false;
    	}
    	*/
    	//symbolCountArray.remove(symbolCountArray.size()-1);
    	//symbolCount=symbolCountArray.get(symbolCountArray.size()-1);
    	//ip=codeindex;
    	//System.out.println(ip);
    	
    	ip=codeindex;
    	
    	
    	
    	
        sentence(); //语句
        
      
       
        
        /*
    	 * add
    	 * */
    	Instruction2 instruction2=new Instruction2(Instruction2.OPR,0,12);
    	code2.add(instruction2);
    	codeindex2++;
    	
    	/*
    	 * add
    	 * */
        /*
    	 * add
    	 * */
        level--;
        relative=3;
        
        /*
    	 * add
    	 * */
        
        //symbolCount=symbolCountArray.get(symbolCountArray.size()-1);
      	//symbolCountArray.remove(symbolCountArray.size()-1);
        
      	currentNode=_save;
    }
    
    
    
    
    public void constDel() {
    	RuleNode r=new RuleNode("constDel");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	
    	
    	match(ListLexer.CONST); 
    	constDef(); 
    	while(lookahead.type==ListLexer.COMMA)
    	{
    		match(ListLexer.COMMA);
    		constDef();
    	}
    	match(ListLexer.COLON);
    	
    	currentNode=_save;
    }
    public void constDef() {
    	RuleNode r=new RuleNode("constDef");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	//here, we have known that it is an identifier for const
    	String constName;
    	int value;
    	// we record the identifier name before we skip it.
    	constName=lookahead.text;
    	
    	
    	
    	Identifer();
    	match(ListLexer.EQUAL); 
    	//we record the value of const identifier before we skip it.
    	value=Integer.parseInt(lookahead.text);
    	Unsigned();
    	Symbol symbol=new Symbol(constName,"const",value,index++);
    	
    	 /*
    	 * add
    	 * */
    	Symbol2 symbol2=new Symbol2(constName,"const", value);
    	arrarsymbol2.add(symbol2);
    	 /*
    	 * add
    	 * */
    	
    	
    	arraysymbol.add(symbol);
    	
    	table.currentScope.define(symbol);
    	
    	
    	
    	currentNode=_save;
    }
    public void Identifer() {
    	
    	RuleNode r=new RuleNode("Identifier");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	match(ListLexer.NAME);
    	
    	
    	
    	currentNode=_save;
    }
    public void Unsigned() {
    	RuleNode r=new RuleNode("Unsigned");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	match(ListLexer.NUMBER);
    	
    	currentNode=_save;
    }
    
    
    
    
    public void variaDel() {
    	RuleNode r=new RuleNode("variaDel");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	match(ListLexer.VAR); 
    	String varName;
    	varName=lookahead.text;
    	Identifer();
    	
    	//这里是记录变量的个数
    	symbolCount++;
    	
    	Symbol symbol=new Symbol(varName,"variable",-65536,index++);
    	symbol.level=level;
    	symbol.addr=relative;
    	 /*
    	 * add
    	 * */
    	Symbol2 symbol2=new Symbol2(varName, "variable", level,relative++);
    	arrarsymbol2.add(symbol2);
    	
    	 /*
    	 * add
    	 * */
    	arraysymbol.add(symbol);
    	table.currentScope.define(symbol);
    	while(lookahead.type==ListLexer.COMMA)
    	{
    		match(ListLexer.COMMA);
    		varName=lookahead.text;
    		Identifer();
    		//这里是记录变量的个数
    		symbolCount++;
    		
    		symbol=new Symbol(varName,"variable",-65536,index++);
    		symbol.level=level;
    		symbol.addr=relative;
    		 /*
        	 * add
        	 * */
        	symbol2=new Symbol2(varName, "variable", level,relative++);
        	arrarsymbol2.add(symbol2);
        	
        	 /*
        	 * add
        	 * */
    		arraysymbol.add(symbol);
        	table.currentScope.define(symbol);
    	}
    	
    	match(ListLexer.COLON);
    	currentNode=_save;
    }
    
    
    
    
    public String proceDel() {
    	RuleNode r=new RuleNode("proceDel");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	//这里是记录变量的个数
    	//symbolCount++;
    	
    	
    	String string=proHead();
    	
    	subprogram();
    	Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.ret);
    	code.add(instruction);
    	codeindex++;
    	
    	match(ListLexer.COLON);
    	if(lookahead.type==ListLexer.PROCEDURE)
    		proceDel();
    	currentNode=_save;
    	//在这表明的程序的当前局部表已经完成了，下一步应该是进行退出当前的表，进去上一层的表
    	table.currentScope=(BaseScope) table.currentScope.getEnclosingScope();
    	return string;
    }
    public String proHead() {
    	RuleNode r=new RuleNode("proHead");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	match(ListLexer.PROCEDURE); 
    	
    	String proName=lookahead.text;
    	
    	proname[++pronamep]=proName;
    	Identifer();
    	
    	
    	/*
    	Symbol symbol=new Symbol(proName,"procedure",index++,codeindex,-1);
    	symbol.index2=codeindex2;
    	symbol.level=level;
    	symbol.addr=relative;
    	arraysymbol.add(symbol);
    	
    	 /*
    	 * add
    	 * */
    	//Symbol2 symbol2=new Symbol2(proName, "procedure", level,relative++);
    	//arrarsymbol2.add(symbol2);
    	
    	 /*
    	 * add
    	 * */
    	
    	
    	//table.currentScope.define(symbol);
    	
    	
    	BaseScope localScope=new BaseScope(table.currentScope,proName);
    	table.currentScope=localScope;
    	
    	match(ListLexer.COLON);
    	currentNode=_save;
    	
    	return proName;
    }
    
    
    //这个句子应该进行的是解析，符号表在上面已经完全构建完毕。，下一步就是确定这些符号的名称所在的位置。，这个工作应该结合解释程序完成，这儿就先不写了。
    public void sentence() {
    	
    	//ip=codeindex;
    	//symbol.index2=codeindex2;
    	ip2=codeindex2;
    	RuleNode r=new RuleNode("sentence");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	if(lookahead.type==ListLexer.NAME)
    		 setValue();
    	else if(lookahead.type==ListLexer.IF)
    		 conditionsen();
    	else if(lookahead.type==ListLexer.WHILE)
    		 cycle();
    	else if(lookahead.type==ListLexer.CALL)
    		 callPro();
    	else if(lookahead.type==ListLexer.READ)
    		 Readsen();
    	else if(lookahead.type==ListLexer.WRITE)
    		 Writesen();
    	else if(lookahead.type==ListLexer.BEGIN)
    		 Intesen();
    	else
    		nothing();
    	currentNode=_save;
    }
    public void setValue() {
    	RuleNode r=new RuleNode("setValue");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	String varden=lookahead.text;
    	Symbol s=table.currentScope.resolve(varden); //这个地方应该加一个解析出来的符号是不是正常的符合规范的语句  
    	if(s.type.equals("const"))
    		throw new Error("You can not change the value of a const!");
    	else if(s.type.equals("procedure"))
    		throw new Error("You can not assign a value to a procedure!");
    	//System.out.println(s.toString());
    	
    	Identifer();
    	match(ListLexer.SET);
    	Expre();
    	
    	Instruction instruction=new Instruction(Instruction.STO,s.index);
    	code.add(instruction);
    	codeindex++;
    	/*
    	 * add
    	 * */
    	Instruction2 instruction2=new Instruction2(Instruction2.STO,level-s.level,s.addr);
    	code2.add(instruction2);
    	codeindex2++;
    	
    	/*
    	 * add
    	 * */
    	
    	currentNode=_save;
    }
    public void Expre() {
    	RuleNode r=new RuleNode("Expre");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	boolean flag=false;
    	if(lookahead.type==ListLexer.ADD)
    		match(ListLexer.ADD);
    	else if(lookahead.type==ListLexer.MINUS)
    	{
    		match(ListLexer.MINUS);
    		flag=true;
    	}
    	term();
    	if(flag)
    	{
    		Instruction instruction=new Instruction(Instruction.LIT,-1,-1,-1);
    		Instruction instruction2=new Instruction(Instruction.OPR,-1,Instruction.cheng);
    		code.add(instruction);
    		code.add(instruction2);
    		codeindex+=2;
    		/*
        	 * add
        	 * */
        	Instruction2 instruction3=new Instruction2(Instruction2.LIT,-1,-1);
        	Instruction2 instruction4=new Instruction2(Instruction.OPR,-1,Instruction2.cheng);
        	code2.add(instruction3);
        	code2.add(instruction4);
        	codeindex2+=2;
        	
        	/*
        	 * add
        	 * */
    		
    	}
    	while(lookahead.type==ListLexer.ADD||lookahead.type==ListLexer.MINUS)
    	{
    		if(lookahead.type==ListLexer.ADD)
    		{
    			match(ListLexer.ADD);
    			term();
    			Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.jia);
    			code.add(instruction);
    			codeindex++;
    			
    			/*
    	    	 * add
    	    	 * */
    	    	Instruction2 instruction2=new Instruction2(Instruction2.OPR,-1,Instruction2.jia);
    	    	code2.add(instruction2);
    	    	codeindex2++;
    	    	
    	    	/*
    	    	 * add
    	    	 * */
    			
    		}
    		else if(lookahead.type==ListLexer.MINUS)
    		{
    			match(ListLexer.MINUS);
    			term();
    			Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.jian);
    			code.add(instruction);
    			codeindex++;
    			/*
    	    	 * add
    	    	 * */
    	    	Instruction2 instruction2=new Instruction2(Instruction2.OPR,-1,Instruction2.jian);
    	    	code2.add(instruction2);
    	    	codeindex2++;
    	    	
    	    	/*
    	    	 * add
    	    	 * */
    		}
    
    	    //term();
    		
    	}
    	currentNode=_save;
    	
    }
    public void term(){
    	RuleNode r=new RuleNode("term");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	
    	factor();
    	while(lookahead.type==ListLexer.MULTIPLY||lookahead.type==ListLexer.DIVIDE){
    		if(lookahead.type==ListLexer.MULTIPLY)
    		{	
    			match(ListLexer.MULTIPLY);
    			factor();
    			Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.cheng);
    			code.add(instruction);
    			codeindex++;
    			/*
    	    	 * add
    	    	 * */
    	    	Instruction2 instruction2=new Instruction2(Instruction2.OPR,-1,Instruction2.cheng);
    	    	code2.add(instruction2);
    	    	codeindex2++;
    	    	
    	    	/*
    	    	 * add
    	    	 * */
    		}
    		else if(lookahead.type==ListLexer.DIVIDE)
    		{
    			match(ListLexer.DIVIDE);
    			factor();
    			Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.chu);
    			code.add(instruction);
    			codeindex++;
    			/*
    	    	 * add
    	    	 * */
    	    	Instruction2 instruction2=new Instruction2(Instruction2.OPR,-1,Instruction2.chu);
    	    	code2.add(instruction2);
    	    	codeindex2++;
    	    	
    	    	/*
    	    	 * add
    	    	 * */
    		}
    		//factor();
    	}
    	
    	currentNode=_save;
    }
    public void factor() {
    	RuleNode r=new RuleNode("factor");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	
    	if(lookahead.type==ListLexer.NAME)
    	{
    		String na=lookahead.text;
    		Symbol s=table.currentScope.resolve(na);
    		//System.out.println(s);
    		
    		Instruction instruction=new Instruction(Instruction.LOD,s.index);
    		code.add(instruction);
			codeindex++;
    		
			/*
	    	 * add
	    	 * */
			Instruction2 instruction2;
			if(s.type.equals("const"))
				instruction2=new Instruction2(Instruction2.LIT,-1,s.value);
			else 
				instruction2=new Instruction2(Instruction2.LOD,level-s.level, s.addr);
	    	code2.add(instruction2);
	    	codeindex2++;
	    	
	    	/*
	    	 * add
	    	 * */
			match(ListLexer.NAME);
    	}
    	else if(lookahead.type==ListLexer.NUMBER)
    	{
    		String na=lookahead.text;
    		//Symbol s=table.currentScope.resolve(na);
    		int number=Integer.parseInt(na);
    		//System.out.println(Integer.parseInt(na));
    		
    		match(ListLexer.NUMBER);
    		
    		Instruction instruction=new Instruction(Instruction.LIT,number);
    		code.add(instruction);
			codeindex++;
			/*
	    	 * add
	    	 * */
	    	Instruction2 instruction2=new Instruction2(Instruction2.LIT,-1,number);
	    	code2.add(instruction2);
	    	codeindex2++;
	    	
	    	/*
	    	 * add
	    	 * */
    	}
    	else {
    		match(ListLexer.LBRACK);
    		Expre();
    		match(ListLexer.RBRACK);
    	}
    	
    	currentNode=_save;
    }
    
    public void conditionsen() {
    	RuleNode r=new RuleNode("conditionsen");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	match(ListLexer.IF);
    	int startcode=codeindex;
    	condition();
    	
    	
    	match(ListLexer.THEN);
    	
    	
    	sentence();
    	int endcode=codeindex;
    	
    	Instruction instruction=new Instruction(Instruction.JPC,-1,-1,-1,endcode);
		code.add(instruction);
		codeindex++;
		
		/*
    	 * add
    	 * */
    	Instruction2 instruction2=new Instruction2(Instruction2.JPC,-1,endcode);
    	code2.add(instruction2);
    	codeindex2++;
    	
    	/*
    	 * add
    	 * */
    	
    	currentNode=_save;
    }
    public void condition() {
    	RuleNode r=new RuleNode("condition");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	Expre();
    	if(lookahead.type==ListLexer.EQUAL)
    	{
    		
    		match(ListLexer.EQUAL);
    		Expre();
    		
    		Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.dengyu);
    		code.add(instruction);
			codeindex++;
    		
			/*
	    	 * add
	    	 * */
	    	Instruction2 instruction2=new Instruction2(Instruction2.OPR,-1,Instruction2.dengyu);
	    	code2.add(instruction2);
	    	codeindex2++;
	    	
	    	/*
	    	 * add
	    	 * */
    	}
    	else if(lookahead.type==ListLexer.GREATER)
    	{
    		match(ListLexer.GREATER);
    		Expre();
    		Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.dayu);
    		code.add(instruction);
			codeindex++;
			
			/*
	    	 * add
	    	 * */
	    	Instruction2 instruction2=new Instruction2(Instruction2.OPR,-1,Instruction2.dayu);
	    	code2.add(instruction2);
	    	codeindex2++;
	    	
	    	/*
	    	 * add
	    	 * */
    	}
    	else if(lookahead.type==ListLexer.GREATER2)
    	{
    		match(ListLexer.GREATER2);
    		Expre();
    		Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.dayudengyu);
    		code.add(instruction);
			codeindex++;
			/*
	    	 * add
	    	 * */
	    	Instruction2 instruction2=new Instruction2(Instruction2.OPR,-1,Instruction2.dayudengyu);
	    	code2.add(instruction2);
	    	codeindex2++;
	    	
	    	/*
	    	 * add
	    	 * */
			
    	}
    	else if(lookahead.type==ListLexer.SMALLER)
    	{
    		match(ListLexer.SMALLER);
    		Expre();
    		Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.xiaoyu);
    		code.add(instruction);
			codeindex++;
			/*
	    	 * add
	    	 * */
	    	Instruction2 instruction2=new Instruction2(Instruction2.OPR,-1,Instruction2.xiaoyu);
	    	code2.add(instruction2);
	    	codeindex2++;
	    	
	    	/*
	    	 * add
	    	 * */
    	}
    	else if(lookahead.type==ListLexer.SMALLER2) {
    		match(ListLexer.SMALLER2);
    		Expre();
    		Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.xiaoyudengyu);
    		code.add(instruction);
			codeindex++;
			/*
	    	 * add
	    	 * */
	    	Instruction2 instruction2=new Instruction2(Instruction2.OPR,-1,Instruction2.xiaoyudengyu);
	    	code2.add(instruction2);
	    	codeindex2++;
	    	
	    	/*
	    	 * add
	    	 * */
    	}
    	else{
    		
    		match(ListLexer.NEQUAL);
    		Expre();
    		Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.budengyu);
    		code.add(instruction);
			codeindex++;
			
			/*
	    	 * add
	    	 * */
	    	Instruction2 instruction2=new Instruction2(Instruction2.OPR,-1,Instruction2.budengyu);
	    	code2.add(instruction2);
	    	codeindex2++;
	    	
	    	/*
	    	 * add
	    	 * */
    	}
    	//Expre();
    	
    	currentNode=_save;
    }
    public void cycle(){
    	RuleNode r=new RuleNode("cycle");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	
    	match(ListLexer.WHILE);
    	
    	
    	int startcode=codeindex;
    	
    	int startcode2=codeindex2;
    	condition();
    	int  middlecode=codeindex;
    	int middlecode2=codeindex2;
		
    	match(ListLexer.DO);
    	sentence();
    	
    	Instruction instruction=new Instruction(Instruction.JMP,-1,-1,-1,startcode);
		code.add(instruction);
		codeindex++;
		
		
		/*
    	 * add
    	 * */
    	Instruction2 instruction2=new Instruction2(Instruction2.JMP,-1,startcode2);
    	code2.add(instruction2);
    	codeindex2++;
    	
    	/*
    	 * add
    	 * */
    	int endcode=codeindex;
    	
    	instruction=new Instruction(Instruction.JPC,-1,-1,-1,endcode+1);
		code.add(middlecode,instruction);
		codeindex++;
		/*
    	 * add
    	 * */
		int endcode2=codeindex2;
    	instruction2=new Instruction2(Instruction2.JPC,-1,endcode2+1);
    	code2.add(middlecode2,instruction2);
    	codeindex2++;
    	
    	/*
    	 * add
    	 * */
    	
    	
    	
    	currentNode=_save;
    }
    
    public void callPro(){
    	RuleNode r=new RuleNode("callPro");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	match(ListLexer.CALL);
    	String string=lookahead.text;
    	Symbol symbol=table.currentScope.resolve(string);
    	
    	//Instruction instruction=new Instruction(Instruction.CAL,symbol.index2);
		//code.add(instruction);
		//codeindex++;
    	
		/*
    	 * add
    	 * */
    	Instruction2 instruction2=new Instruction2(Instruction2.CAL,level-symbol.level,symbol.index2);
    	code2.add(instruction2);
    	codeindex2++;
    	
    	/* 
    	 * add
    	 * */
    	Identifer();
    	
    	currentNode=_save;
    }
    
    public void Readsen() {
    	RuleNode r=new RuleNode("Readsen");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	match(ListLexer.READ); 
    	match(ListLexer.LBRACK);
    	
    	String string=lookahead.text;
    	Symbol symbol=table.currentScope.resolve(string);
    	if(symbol.type.equals("const"))
    		throw new Error("You can not assign a value to a constant!");
    	
    	Instruction instruction=new Instruction(Instruction.OPR,symbol.index,Instruction.read);
		code.add(instruction);
		codeindex++;
    	
		/*
    	 * add
    	 * */
    
    	
		Instruction2 instruction2=new Instruction2(Instruction2.OPR,level-symbol.level,Instruction2.read);
    	code2.add(instruction2);
    	codeindex2++;
    	/*
    	 * add
    	 * */
    	instruction2=new Instruction2(Instruction2.STO, level-symbol.level,symbol.addr);
    	code2.add(instruction2);
    	codeindex2++;
		Identifer();
    	while(lookahead.type==ListLexer.COMMA){
    		match(ListLexer.COMMA);
    		
    		string=lookahead.text;
        	symbol=table.currentScope.resolve(string);
        	if(symbol.type.equals("const"))
        		throw new Error("You can not assign a value to a constant!");
        	instruction=new Instruction(Instruction.OPR,symbol.index,Instruction.read);
    		code.add(instruction);
			codeindex++;
			
			/*
	    	 * add
	    	 * */
	    	instruction2=new Instruction2(Instruction2.OPR,level-symbol.level,Instruction2.read);
	    	code2.add(instruction2);
	    	codeindex2++;
	    	
	    	/*
	    	 * add
	    	 * */
    		Identifer();
    	}
    	match(ListLexer.RBRACK);
    	
    	currentNode=_save;
    }
    public void Writesen() {
    	RuleNode r=new RuleNode("Writesen");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	
    	
    	match(ListLexer.WRITE); 
    	match(ListLexer.LBRACK);
    	String string=lookahead.text;
    	Symbol symbol=table.currentScope.resolve(string);
    	//System.out.println(symbol);
    	Instruction instruction=new Instruction(Instruction.OPR,symbol.index,Instruction.write);
		code.add(instruction);
		codeindex++;
		/*
    	 * add
    	 * */
    	Instruction2 instruction2=new Instruction2(Instruction2.LOD, level-symbol.level, symbol.addr);
    	code2.add(instruction2);
    	codeindex2++;
    	
    	instruction2=new Instruction2(Instruction2.OPR,level-symbol.level,Instruction2.write);
    	code2.add(instruction2);
    	codeindex2++;
    	/*
    	 * add
    	 * */
		
    	Identifer();
    	while(lookahead.type==ListLexer.COMMA){
    		match(ListLexer.COMMA);
    		
    		string=lookahead.text;
        	symbol=table.currentScope.resolve(string);
        	
            instruction=new Instruction(Instruction.OPR,symbol.index,Instruction.write);
    		code.add(instruction);
			codeindex++;
			/*
	    	 * add
	    	 * */
			instruction2=new Instruction2(Instruction2.LOD, level-symbol.level, symbol.addr);
	    	code2.add(instruction2);
	    	codeindex2++;
	    	
	    	instruction2=new Instruction2(Instruction2.OPR,level-symbol.level,Instruction2.write);
	    	code2.add(instruction2);
	    	codeindex2++;
	    	
	    	/*
	    	 * add
	    	 * */
    		Identifer();
    	}
    	match(ListLexer.RBRACK);
    	
    	currentNode=_save;
    }
    
    public void Intesen() {
    	RuleNode r=new RuleNode("Intesen");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	match(ListLexer.BEGIN); 
    	sentence();
    	while(lookahead.type==ListLexer.COLON){
    		match(ListLexer.COLON);
    	
    		sentence();
    	}
    	match(ListLexer.END);
    	
    	currentNode=_save;
    		
    }
    public void nothing(){
    	
    }
}
