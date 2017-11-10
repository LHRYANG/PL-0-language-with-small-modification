package package1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ProcessBuilder.Redirect.Type;

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
		
		/*
		for(int i=0;i<parser.code.size();i++) {
			System.out.println(parser.code.get(i));
		}
		*/
	}
}
