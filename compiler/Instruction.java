package package3;

public class Instruction {
	int ins;    //这个是什么指令
	int index;
	int ope;
	int value;// 压入常数的时候
	public Instruction(int ins,int index)
	{
		this.ins=ins;
		this.index=index;
	}
	public Instruction(int ins,int index,int ope)
	{
		this.ins=ins;
		this.index=index;
		this.ope=ope;
	}
	public Instruction(int ins,int index,int ope,int value)
	{
		this.index=index;this.ins=ins;this.ope=ope;this.value=value;
	}
	
	public String toString() {
		String string="";
		string="instruct: "+instruct[this.ins]+" index: "+this.index+" ope: "+operation[this.ope]+" value: "+this.value;
		return string;
	}
	
	String[] instruct= {"lIT","LOD","STO","CAL","INT","JMP","JPC","OPR"};
	public static int LIT=0;
	public static int LOD=1;
	public static int STO=2;
	public static int CAL=3;
	public static int INT=4;
	public static int JMP=5;
	public static int JPC=6;
	public static int OPR=7;
	
	String[] operation= {"jia","jian","cheng","chu","dayu","dayudengyu","xiaoyu","xiaoyudengyu","dengyu","budengyu"};
	public static int jia=0;
	public static int jian=1;
	public static int cheng=2;
	public static int chu=3;
	public static int dayu=4;
	public static int dayudengyu=5;
	public static int xiaoyu=6;
	public static int xiaoyudengyu=7;
	public static int dengyu=8;
	public static int budengyu=9;
	
}
