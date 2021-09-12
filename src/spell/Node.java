package spell;

public class Node implements INode {

    private int count;
    private Node [] children = new Node[26]; //figure out how to do this

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }
}
