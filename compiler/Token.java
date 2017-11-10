package package1;

/*A single word should has such attribute use a class to store them*/
public class Token {
	public int type; // type number
	public String text; // what the type number represent in a real input . such as keywords 'for'.type=14
	//the construction function of A token
	public Token(int type,String text) {
		this.type=type;
		this.text=text;
	}
	//output the token!
	public String toString(){
		String tname=ListLexer.tokenNames[type];
		return "<'"+text+"',"+tname+">";   //For reader's convenience, we use 'tokenNmes' to make it more readable than just integer!
	}
	
}
