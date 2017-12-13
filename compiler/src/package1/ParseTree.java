package package1;


import java.util.*;
// Nodes are instances of this class; there's no Node class per se
public abstract class ParseTree {
    public List<ParseTree> children; // normalized child list
    public RuleNode addChild(String value) {
        RuleNode r = new RuleNode(value);
        addChild(r);
        return r;
    }
    public TokenNode addChild(Token value) {
        TokenNode t = new TokenNode(value);
        addChild(t);
        return t;
    }
    public void addChild(ParseTree t) {
        if ( children==null ) children = new ArrayList<ParseTree>();
        children.add(t);
    }
}
