package package1;

import java.awt.List;
import java.util.ArrayList;

import javax.management.timer.TimerMBean;
import javax.swing.LookAndFeel;
import javax.xml.parsers.FactoryConfigurationError;

import package3.BaseScope;
import package3.Instruction;
import package3.Symbol;
import package3.SymbolTable;

//在这个过程中已经完成了解析树的构建


public class ListParser extends Parser {
    public ListParser(Lexer input) { super(input); }
   
    /*
     * This is about how to built table!!
     * */
    
    SymbolTable table=new SymbolTable();
    
    /*
     * This is about how to build parse tree.
     * 
     * 
     * */
    
    ParseTree root;
    ParseTree currentNode;
    
    public ArrayList<Symbol> arraysymbol=new ArrayList<Symbol>(); // 这个表格是一个统一的表格，用于记录各种符号所在的位置
    int index=0;                               //这个是符号表当前填入的位置
    
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
    	
    	RuleNode r=new RuleNode("subprogram");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	if(lookahead.type==ListLexer.CONST)
    		constDel();//常量说明
    	if(lookahead.type==ListLexer.VAR) 
    		variaDel();  //变量说明
        if(lookahead.type==ListLexer.PROCEDURE)
    		proceDel();  //过程说明
        sentence(); //语句
        
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
    	Symbol symbol=new Symbol(varName,"variable",-65536,index++);
    	arraysymbol.add(symbol);
    	table.currentScope.define(symbol);
    	while(lookahead.type==ListLexer.COMMA)
    	{
    		match(ListLexer.COMMA);
    		varName=lookahead.text;
    		Identifer();
    		symbol=new Symbol(varName,"variable",-65536,index++);
    		arraysymbol.add(symbol);
        	table.currentScope.define(symbol);
    	}
    	
    	match(ListLexer.COLON);
    	currentNode=_save;
    }
    
    
    
    
    public void proceDel() {
    	RuleNode r=new RuleNode("proceDel");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	proHead();
    	subprogram();
    	match(ListLexer.COLON);
    	if(lookahead.type==ListLexer.PROCEDURE)
    		proceDel();
    	currentNode=_save;
    	//在这表明的程序的当前局部表已经完成了，下一步应该是进行退出当前的表，进去上一层的表
    	table.currentScope=(BaseScope) table.currentScope.getEnclosingScope();
    	
    }
    public void proHead() {
    	RuleNode r=new RuleNode("proHead");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	match(ListLexer.PROCEDURE); 
    	
    	String proName=lookahead.text;
    	Identifer();
    	
    	Symbol symbol=new Symbol(proName,"procedure",index++,codeindex,-1);
    	arraysymbol.add(symbol);
    	
    	table.currentScope.define(symbol);
    	BaseScope localScope=new BaseScope(table.currentScope,proName);
    	table.currentScope=localScope;
    	
    	match(ListLexer.COLON);
    	currentNode=_save;
    }
    
    
    //这个句子应该进行的是解析，符号表在上面已经完全构建完毕。，下一步就是确定这些符号的名称所在的位置。，这个工作应该结合解释程序完成，这儿就先不写了。
    public void sentence() {
    	
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
    	System.out.println(s.toString());
    	
    	Identifer();
    	match(ListLexer.SET);
    	Expre();
    	
    	Instruction instruction=new Instruction(Instruction.STO,s.index);
    	code.add(instruction);
    	codeindex++;
    	
    	currentNode=_save;
    }
    public void Expre() {
    	RuleNode r=new RuleNode("Expre");
    	currentNode.addChild(r);
    	ParseTree _save=currentNode;
    	currentNode=r;
    	
    	if(lookahead.type==ListLexer.ADD)
    		match(ListLexer.ADD);
    	else if(lookahead.type==ListLexer.MINUS)
    		match(ListLexer.MINUS);
    	term();
    	Instruction instruction=new Instruction(Instruction.LIT,-1,-1,-1);
    	Instruction instruction2=new Instruction(Instruction.OPR,-1,Instruction.cheng);
    	code.add(instruction);
    	code.add(instruction2);
    	codeindex++;
    	
    	while(lookahead.type==ListLexer.ADD||lookahead.type==ListLexer.MINUS)
    	{
    		if(lookahead.type==ListLexer.ADD)
    		{
    			match(ListLexer.ADD);
    			term();
    			instruction=new Instruction(Instruction.OPR,-1,Instruction.jia);
    			code.add(instruction);
    			codeindex++;
    		}
    		else if(lookahead.type==ListLexer.MINUS)
    		{
    			match(ListLexer.MINUS);
    			instruction=new Instruction(Instruction.OPR,-1,Instruction.jian);
    			code.add(instruction);
    			codeindex++;
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
    		}
    		else if(lookahead.type==ListLexer.DIVIDE)
    		{
    			match(ListLexer.DIVIDE);
    			factor();
    			Instruction instruction=new Instruction(Instruction.OPR,-1,Instruction.chu);
    			code.add(instruction);
    			codeindex++;
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
    		System.out.println(s);
    		
    		Instruction instruction=new Instruction(Instruction.LOD,s.index);
    		code.add(instruction);
			codeindex++;
    		
			match(ListLexer.NAME);
    	}
    	else if(lookahead.type==ListLexer.NUMBER)
    	{
    		String na=lookahead.text;
    		//Symbol s=table.currentScope.resolve(na);
    		int number=Integer.parseInt(na);
    		System.out.println(Integer.parseInt(na));
    		
    		match(ListLexer.NUMBER);
    		Instruction instruction=new Instruction(Instruction.LIT,number);
    		code.add(instruction);
			codeindex++;
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
    	condition();
    	match(ListLexer.THEN);
    	sentence();
    	
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
    	}
    	else if(lookahead.type==ListLexer.GREATER)
    	{
    		match(ListLexer.GREATER);
    	}
    	else if(lookahead.type==ListLexer.GREATER2)
    	{
    		match(ListLexer.GREATER2);
    	}
    	else if(lookahead.type==ListLexer.SMALLER)
    	{
    		match(ListLexer.SMALLER);
    	}
    	else if(lookahead.type==ListLexer.SMALLER2) {
    		match(ListLexer.SMALLER2);
    	}
    	else{
    		match(ListLexer.NEQUAL);
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
    	condition();
    	match(ListLexer.DO);
    	sentence();
    	
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
    	System.out.println(symbol);
    	
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
    	System.out.println(symbol);
    	
    	Identifer();
    	while(lookahead.type==ListLexer.COMMA){
    		match(ListLexer.COMMA);
    		
    		string=lookahead.text;
        	symbol=table.currentScope.resolve(string);
        	System.out.println(symbol);
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
    	System.out.println(symbol);
    	
    	Identifer();
    	while(lookahead.type==ListLexer.COMMA){
    		match(ListLexer.COMMA);
    		
    		string=lookahead.text;
        	symbol=table.currentScope.resolve(string);
        	System.out.println(symbol);
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
