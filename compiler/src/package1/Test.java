package package1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ProcessBuilder.Redirect.Type;

import package3.Instruction;
import package3.Instruction2;
import package3.Interpreter;
import package3.Symbol;

/*
 * LL (1) recursive descent lexical analyzer
 * */

public class Test {
	
	 public static String readFileByChars(String fileName) {
		 File file = new File(fileName);
		 Reader reader = null;
		 StringBuilder builder=new StringBuilder();		 
		 try {
			 System.out.println("read a file: ");
			 reader = new InputStreamReader(new FileInputStream(file));
			 int tempchar;
			 while((tempchar=reader.read())!=-1) {
				 builder.append((char)tempchar);
			 }
			 builder.append((char)tempchar);
			 reader.close();
		 } catch (Exception e) {
	            e.printStackTrace();
	            }
		 
		 return builder.toString();
	 }
	
	public static void main(String[] args){
		String code=readFileByChars("src/package1/code.txt");
		ListLexer lexer=new ListLexer(code);
		ListParser parser=new ListParser(lexer);
		parser.program();
		
		System.out.println("---------------------------------------");
		for(int i=0;i<parser.arraysymbol.size();i++)
		{
			System.out.println(parser.arraysymbol.get(i));
		}
		System.out.println("---------------------------------------");
		for(int i=0;i<parser.arrarsymbol2.size();i++)
		{
			System.out.println(parser.arrarsymbol2.get(i));
		}
		System.out.println("---------------------------------------");
		for(int i=0;i<parser.code.size();i++) {
			System.out.println(i+"\t"+parser.code.get(i));
		}
		
		System.out.println("---------------------------------------");
		for(int i=0;i<parser.code2.size();i++) {
			
			if(i==9)
				parser.code2.get(i).a=0;
			if(i==12)
				parser.code2.get(i).a=8;
			
			System.out.println(i+"\t"+parser.code2.get(i));
		}
		System.out.println("---------------------------------------");
		Instruction instruct[] = new Instruction[parser.code.size()];  
		parser.code.toArray(instruct);   
		
		Instruction2 instruct2[] = new Instruction2[parser.code2.size()];  
		parser.code2.toArray(instruct2);   
		
		Symbol sym[]=new Symbol[parser.arraysymbol.size()];
		parser.arraysymbol.toArray(sym);
		
		
		//System.out.println("parser.ip2"+parser.ip2);
		//System.out.println("parser.ip"+parser.ip);
		//System.out.println(parser.code2.size());
		Interpreter iter=new Interpreter(parser.ip2-1,instruct2, parser.code2.size(), sym);
		iter.interpret();
	}
}
