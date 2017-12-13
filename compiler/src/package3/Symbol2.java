package package3;

public class Symbol2 {
	public String name;
	public String kind;
	public int value;
	public int level;
	public int addr;
	
	public Symbol2(String name,String kind,int value) {
		this.name=name;
		this.kind=kind;
		this.value=value;
	}
	
	public Symbol2(String name,String kind,int level,int addr) {
		this.name=name;
		this.kind=kind;
		this.level=level;
		this.addr=addr;
	}
	public String toString() {
		String string="";
		if(this.kind.equals("const"))
			string+="name:\t"+this.name+"\t kind: \t"+this.kind+"\t val: \t"+this.value;
		else
			string+="name:\t"+this.name+"\t kind: \t"+this.kind+"\t level: \t"+this.level+"\t addr \t"+this.addr;
		return string;
	}
}
