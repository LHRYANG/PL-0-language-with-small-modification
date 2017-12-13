package package1;

import javax.swing.ButtonGroup;

import org.omg.PortableServer.ServantActivator;

public class ListLexer extends Lexer{
	//KEYWORDS
	public static int CONST = 1; // KEYWORD: CONST
	public static int VAR = 2;   // KEYWORD: VAR
	public static int PROCEDURE = 3; //KEYWORD: procedure
	public static int BEGIN = 4; //KEYWORD: begin
	public static int END = 5;  //KEYWORD: end
	public static int WHILE = 6; //KEYWORD: while
	public static int DO = 7; //KEYWORD: do
	public static int IF = 8; //KEYWORD: if 
	public static int THEN = 9; //KEYWORD: then
	public static int READ = 10; //KEYWORD: read
	public static int WRITE = 11; // KEYWORD: write
	public static int CALL =12;  // KEYWORD: call 
	
	//DELIMITER  
	/*
	 * Here, i do not include the full point(.) although it has a full point in PL/0 (<程序>-》《分程序》。)。 Because i think it is useless.
	 * */
	
	public static int COMMA = 13;  //,
	public static int LBRACK = 14; //( 
	public static int RBRACK = 15; //)
	public static int COLON =16;  // ;
	public static int LBRACK2= 17; //{ Maybe in PL/0 it's useless!
	public static int RBRACK2=18 ;//}  Maybe in PL/0 it's useless!
	
	//calculate symbol
	public static int ADD = 19;  //+
	public static int MINUS = 20; //-
	public static int MULTIPLY = 21; //*
	public static int DIVIDE =22;  //  /
	public static int EQUAL= 23; //  =
	public static int SET=24 ; // :=
	
	public static int GREATER = 25; //>          
	public static int SMALLER = 26;  //<
	public static int GREATER2 = 27; //>=
	public static int SMALLER2 =28;  //<=
	public static int NEQUAL= 29;   //Not equal 
	
	//ELSE
	
	//identifier
	public static int NAME = 30;  // All identifier we  	
	public static int NUMBER =31;
	
	//nop just nothing, you can ignore it. 
	public static String[] keyWords= {"nop","CONST","VAR","procedure","begin","end","while","do","if","then","read","write","call"};
	//the tokenNames is for the convenience what an integer represent!
	public static String[] tokenNames= {"n/a","CONST","VAR","PROCEDURE","BEGIN", "END", "WHILE", "DO", "IF",
			"THEN","READ","WRITE","CALL","COMMA","LBRACK","RBRACK","COLON","LBRACK2","RBRACK2","ADD","MINUS","MULTIPLY","DIVIDE"
			,"EQUAL","SET","GREATER","SMALLER","GREATER2","SMALLER2","NEQUAL","NAME","NUMBER"};
	//inherit Lexer, directly call class Lexer's construction function.
	public ListLexer(String input) {
		super(input);
		// TODO Auto-generated constructor stub
	}
	
	//realize the abstract function in Lexer  
	public String getTokenName(int x) {
		return tokenNames[x];
	}
	
	//judge whether the current char of input is a letter !
	public boolean isLetter() {
		return (c>='a'&&c<='z'||c>='A'&&c<='Z');
	}
	//judge whether the current char of input is a Num
	public boolean isNumber() {
		return (c>='0'&&c<='9');
	}
	
	// if the input is blankspace |\t|\n|\r just jump to the next char!
	void WS() {
		while(c==' '||c=='\t'||c=='\n'||c=='\r')
			consume();
	}
	
	// If it's an identifier, it must fit this form(char{num|char}*) 
	Token NAME(){
		StringBuilder buf=new StringBuilder();
		do {
			buf.append(c);
			consume();
		}while(isLetter()||isNumber());
		
		String temp=buf.toString();
		// scan the whole keywords, to see whether the input is the same with current input.
		for(int i=1;i<keyWords.length;i++){
			if(temp.equals(keyWords[i]))
				return new Token(i, temp); //type, text
		}
		
		return new Token(NAME, temp);   // or it is an identifier defined by ourselves
	}
	// The circumstance is to read a number
	Token NUMBER(){
		StringBuilder buf=new StringBuilder();
		do {
			buf.append(c);
			consume();
		}while(isNumber());
		return new Token(NUMBER, buf.toString());
	}
	//the token is > or >=?
	Token GREATER() {
		StringBuilder buf=new StringBuilder();
		buf.append(c);
		consume();
		if(c!='='){
			return new Token(GREATER, ">");
		}
		else {
			consume();
			return new Token(GREATER2, ">=");
		}
	}
	//the token is < or <=?
	Token SMALLER() {
		StringBuilder buf=new StringBuilder();
		buf.append(c);
		consume();
		if(c!='='){
			return new Token(SMALLER, "<");
		}
		else {
			consume();
			return new Token(SMALLER2, "<=");
		}
	}
	
	// Assignment statement
	Token SET() {
		StringBuilder buf=new StringBuilder();
		buf.append(c);
		consume();
		if(c!='='){
			throw new Error(": should follow =.");
		}
		else {
			consume();
			return new Token(SET, ":=");
		}
	}
	// It is the main function used to GetTokens!!!!
	public Token nextToken() {
		while(c!=EOF){
			switch (c){
				case ' ': 
			    case '\t':
			    case '\r':
			    case '\n':
			    	WS();
			    	continue;
			    case ',': 
			    	consume();
			    	return new Token(COMMA, ",");
			    case '(':
			    	consume();
			    	return new Token(LBRACK, "(");
			    case ')':
			    	consume();
			    	return new Token(RBRACK, ")");
			    case '{':
			    	consume();
			    	return new Token(LBRACK2, "{");
			    case '}':
			    	consume();
			    	return new Token(RBRACK2, "}");
			    case ';':
			    	consume();
			    	return new Token(COLON, ";");
			    case '+':
			    	consume();
			    	return new Token(ADD, "+");
			    case '-':
			    	consume();
			    	return new Token(MINUS, "-");
			    case '*':
			    	consume();
			    	return new Token(MULTIPLY, "*");
			    case '/':
			    	consume();
			    	return new Token(DIVIDE, "/");
			    case '=':
			    	consume();
			    	return new Token(EQUAL, "=");
			    case '#':
			    	consume();
			    	return new Token(NEQUAL, "#");
			    case '>':
			    	return GREATER();
			    case '<':
			    	return SMALLER();
			    case ':':
			    	return SET();
			    	
			    default:
			    	if(isLetter())
			    		return NAME();
			    	else if(isNumber())
			    		return NUMBER(); 
			    	throw new Error("invalid character: "+c);
			}
			     	
		}
		return new Token(EOF_TYPE, "<EOF>");
	}
}
