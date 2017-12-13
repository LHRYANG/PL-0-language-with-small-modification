package package3;

public class Instruction2 {
	public int f;
	public int l;
	public int a;
	
	public Instruction2(int f,int l,int a) {
		this.f=f;
		this.l=l;
		this.a=a;
	}
	
	public String toString() {
		String string="";
		string+="ins:\t"+instruct[f]+"\t l: \t"+this.l+"\t a: \t"+this.a;
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
