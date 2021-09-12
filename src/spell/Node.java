package spell;

public class Node implements INode {

    private int count;
    private Node [] children = new Node[26]; //figure out how to do this

    @Override
    public int getValue() {
        return this.count;
    }

    @Override
    public void incrementValue() {
        this.count++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }

}
