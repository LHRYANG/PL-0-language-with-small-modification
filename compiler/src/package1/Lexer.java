package package1;

/*This is a universal base class for lexer, so we define an abstract class!*/
public abstract class Lexer {
	public static final char EOF = (char)-1; //EOF: the tail of a file
	public static final int EOF_TYPE=0;  // the lexical type of EOF
	String input;   // input string
	int p=0;  //the subscript of current char
	char c;  //the current char
	/*the constructor of class, then make the pointer points to 0 of the input string! */
	public Lexer(String input){
		this.input=input;
		c=input.charAt(p);
	}
	/*this is what we should do when has read one character  */
	public void consume() {
		p++;
		if(p>input.length())
			c=EOF;
		else
			c=input.charAt(p);
	}
	
	/*make sure x is the next char in the input flow*/
	public void match(char x) {
	if(c==x)
		consume();
	else
		throw new Error("Expecting "+x+";found "+c);
	}
	
	public abstract Token nextToken(); /*not a char, but a word*/  
    abstract void WS();  /*every lexer should have a method the skip the \t \n backspace or something else, you can add in this function*/
    public abstract String getTokenName(int x);
}
