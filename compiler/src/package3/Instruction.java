package package3;

public class Instruction {
	int ins;    //这个是什么指令
	int index;
	int ope;
	int value;// 压入常数的时候
	int address; // 跳转的时候调到代码的什么位置
	public Instruction(int ins,int index)
	{
		this.ins=ins;
		this.index=index;
		this.ope=-1;
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
	
	public Instruction(int ins,int index,int ope,int value,int address)
	{
		this.index=index;this.ins=ins;this.ope=ope;this.value=value;this.address=address;
	}
	
	public String toString() {
		String string="";
		string="instruct:\t"+instruct[this.ins]+"\t index:\t"+this.index +"\t value:\t"+this.value+"\t address:\t"+this.address+"\t";
		if(this.ope!=-1)
			string+=" ope: "+operation[this.ope];
		else
			string+=" ope: "+this.ope;
		return string;
	}
	
	String[] instruct= {"LIT","LOD","STO","CAL","INT","JMP","JPC","OPR"};
	public static final int LIT=0;
	public static final int LOD=1;
	public static final int STO=2;
	public static final int CAL=3;
	public static final int INT=4;
	public static final int JMP=5;
	public static final int JPC=6;
	public static final int OPR=7;
	
	String[] operation= {"jia","jian","cheng","chu","dayu","dayudengyu","xiaoyu","xiaoyudengyu","dengyu","budengyu","read","write","ret"};
	public static final int jia=0;
	public static final int jian=1;
	public static final int cheng=2;
	public static final int chu=3;
	public static final int dayu=4;
	public static final int dayudengyu=5;
	public static final int xiaoyu=6;
	public static final int xiaoyudengyu=7;
	public static final int dengyu=8;
	public static final int budengyu=9;
	public static final int read=10;
	public static final int write=11;
	public static final int ret=12;
}
