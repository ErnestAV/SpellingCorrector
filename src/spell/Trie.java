package spell;

import java.util.Dictionary;

public class Trie implements ITrie {

    private INode root = new Node();
    private int wordCount;
    private int nodeCount = 1;

    @Override
    public void add(String word) { //Check with TA
        INode thisNode = this.root;
        String fixedWord = word.toLowerCase();
        int index = 0;
        char letter;
        //boolean fixedWordEnd = false;

        for (int i = 0; i < fixedWord.length(); i++) { //traverse through the trie
            letter = fixedWord.charAt(i);
            index = letter - 'a';

            if (thisNode.getChildren()[index] == null) { //if a children is not found...
                thisNode.getChildren()[index] = new Node(); //create it
                this.nodeCount++;
            }

            thisNode = thisNode.getChildren()[index]; //go to the next node
        }

        if (thisNode.getValue() == 0) { // if the last node is 0, advance the word count
            this.wordCount++;
        }
        thisNode.incrementValue(); //increment the count of the current node (because word was found)
    }

    @Override
    public INode find(String word) { //Check with TA
        INode thisNode = root;
        String fixedWord = word.toLowerCase();
        int index = 0;
        char letter;
        INode aChild = thisNode.getChildren()[index];

        for (int i = 0; i < fixedWord.length(); i++) { //traverse through the trie
            letter = fixedWord.charAt(i);
            index = letter - 'a';
            aChild = thisNode.getChildren()[index]; //child found

            if (!(thisNode.getChildren()[index].getValue() > 0)) { //If it is greater than 0 return the child
                return thisNode.getChildren()[index];
            }
            else if (aChild == null) { //child not found
                return null;
            }
        }

        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    //toString – from video
    @Override
    public String toString() {
        StringBuilder currWord = new StringBuilder();
        StringBuilder output = new StringBuilder();

        toString_Helper(root, currWord, output);

        return output.toString();
    }

    private void toString_Helper(INode n, StringBuilder currWord, StringBuilder output) {
        if (n.getValue() > 0) {
            output.append(currWord.toString());
            output.append("\n"); //append the node's word to the output
        }

        for (int i = 0; i < n.getChildren().length; ++i) {
            INode child = n.getChildren()[i];
            if (child != null) {

                char childLetter = (char)('a' + i);
                currWord.append(childLetter);

                toString_Helper(child, currWord, output);

                currWord.deleteCharAt(currWord.length() - 1);
            }
        }
    }

    //equals
    @Override
    public boolean equals(Object o) {

        //is o == null?
        if (o == null) {
            return false;
        }

        //is o == this?
        if (o == this) {
            return true;
        }

        //do this and node have the same class?
        if (this.getClass() != o.getClass()) {
            return false;
        }

        Trie d = (Trie) o;

        //do this and d have the same wordCount and nodeCount?
        if (d.wordCount != this.wordCount || d.nodeCount != this.nodeCount) {
            return false;
        }

        return equals_Helper(this.root, d.root);
    }

    private boolean equals_Helper(INode n1, INode n2) {
        //Compare n1 and n2 to see if they are the same
        if (n1.getValue() != n2.getValue()) {
            return false;
            //Do n1 and n2 have the same count?
            //Do n1 and n2 have non-null children in exactly the same indexes?
        }

        //Recurse on the children and compare the child subtrees
        for (int i = 0; i < n1.getChildren().length; i++) {
            INode child1 = n1.getChildren()[i];
            INode child2 = n2.getChildren()[i];

            if (child1 == null && child2 != null || child1 != null && child2 == null) {
                return false;
            }
            else if (child1 == null && child2 == null) {
                continue;
            }
            else if (child1 != null && child2 != null) {
                if (!equals_Helper(child1, child2)) {
                    return false;
                }
            }
        }

        return true;
    }

    //hashcode
    @Override
    public int hashCode() {
        //Combine the following values:
        //1. wordCount
        //2. nodeCount
        //3. index of each of the root node's non-null children
        int rootIndex = 1;

        for (int i = 0; i < root.getChildren().length; i++) {
            if (root.getChildren()[i] != null) {
                rootIndex = i;
                break;
            }
        }
        return wordCount * nodeCount * rootIndex;
    }
}
