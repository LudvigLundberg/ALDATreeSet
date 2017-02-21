package alda.tree;

import java.lang.Math;

public class ALDATreeSetNode<E extends Comparable<E>> {
    private static final int MAXIMUM_IMBALANCE = 1;
    private E data;
    private int size  = 1;
    private int height = 0;

    private ALDATreeSetNode<E> predecessor, successor, left, right;


    public ALDATreeSetNode(E data) {
        this.data = data;
    }

    public ALDATreeSetNode(E data, ALDATreeSetNode<E> predecessor, ALDATreeSetNode<E> successor) {
        this.data = data;
        this.predecessor = predecessor;
        this.successor = successor;
    }

    //Package Private
    ALDATreeSetNode<E> getSuccessor() {
        return successor;
    }

    ALDATreeSetNode<E> getPredecessor(){
        return predecessor;
    }

    void setPredecessor(ALDATreeSetNode<E> predecessor){
        this.predecessor = predecessor;
    }

    void setSuccessor(ALDATreeSetNode<E> successor){
        this.successor = successor;
    }

    //Package Private
    E getData(){
        return data;
    }

    protected int size(){
        return size;
    }

    protected ALDATreeSetNode<E> add(E data) {
        int compareInt = data.compareTo(this.data);

        if (compareInt < 0) {
            if (left == null) {
                left = new ALDATreeSetNode<E>(data,null,this);
                adjustPredecessorAndSuccessor(predecessor, left);
                predecessor = left;
                size++;
                height = Math.max(height(left), height(right)) + 1;
            }
            else {
                int tempsize = left.size;
                left = left.add(data);
                if(left.size > tempsize){
                    size++;
                    height = Math.max(height(left), height(right)) + 1;
                    predecessor = left.findMax();
                }
            }
        }

        else if(compareInt > 0) {
            if (right == null) {
                right = new ALDATreeSetNode<E>(data, this, null);
                adjustPredecessorAndSuccessor(right, successor);
                successor = right;
                size++;
                height = Math.max(height(left), height(right)) + 1;
            }
            else{
                int tempsize = right.size;
                right = right.add(data);
                if(right.size > tempsize){
                    size++;
                    height = Math.max(height(left), height(right)) + 1;
                    successor = right.findMin();
                }

            }
        }

        else{
            return this;
        }

        return balance();
    }

    protected ALDATreeSetNode<E> remove(E data){
        int compareInt = data.compareTo(this.data);

        if(compareInt != 0){
            ALDATreeSetNode choice = compareInt < 0 ? left : right;
            if(choice != null){
                int tempsize = choice.size;
                if(compareInt < 0){
                   left = left.remove(data);
                }
                else{
                    right = right.remove(data);
                }
                choice = (compareInt < 0 ? left : right);
                if(choice == null || choice.size < tempsize ){
                    size--;
                    height = Math.max(height(left), height(right)) + 1;
                }

            }
            return balance();
        }

        else{
            if(left != null){
                this.data = predecessor.data;
                left = left.remove(predecessor.data);
                adjustHeightAndSize(this);
                return balance();
            }
            else if(right != null){
                this.data = successor.data;
                right =  right.remove(successor.data);
                adjustHeightAndSize(this);
                return balance();

            }
            else{
                adjustPredecessorAndSuccessor(predecessor, successor);
                return null;
            }

        }
    }


    private void adjustPredecessorAndSuccessor(ALDATreeSetNode<E> predecessor, ALDATreeSetNode<E> successor){
        if(predecessor != null) {
            predecessor.successor = successor;
        }
        if(successor != null) {
            successor.predecessor = predecessor;
        }
    }

    private ALDATreeSetNode<E> balance(){
        if(right != null && left != null){
            if(left.height - right.height > MAXIMUM_IMBALANCE){
                return leftImbalance();
            }
            else if (right.height - left.height > MAXIMUM_IMBALANCE){
                return rightImbalance();
            }
            else{
                return this;
            }
        }
        else if(right == null){
            if(height(left) > MAXIMUM_IMBALANCE){
                return leftImbalance();
            }
        }
        else{
            if(height(right) > MAXIMUM_IMBALANCE){
                return rightImbalance();
            }
        }
        return this;
    }

    private ALDATreeSetNode<E> leftImbalance(){
        if (height(left.left) > height(left.right)){
            return rotateLeftToRight();
        }
        else{
            return doubleLeftToRight();
        }

    }

    private ALDATreeSetNode<E> rightImbalance(){
        if (height(right.right) > height(right.left)){
            return rotateRightToLeft();
        }
        else{
            return doubleRightToLeft();
        }
    }

    protected ALDATreeSetNode<E> findMax(){
        if(right != null){
            return right.findMax();
        }
        else{
            return this;
        }
    }

    protected ALDATreeSetNode<E> findMin() {
        if(left != null){
            return left.findMin();
        }
        else{
            return this;
        }
    }

    private ALDATreeSetNode<E> rotateRightToLeft(){
        ALDATreeSetNode<E> root = right;
        right = root.left;
        root.left = this;
        adjustHeightAndSize(this);
        adjustHeightAndSize(root);
        return root;
    }

    private ALDATreeSetNode<E> rotateLeftToRight(){
        ALDATreeSetNode<E> root = left;
        left = root.right;
        root.right = this;
        adjustHeightAndSize(this);
        adjustHeightAndSize(root);
        return root;
    }

    private void adjustHeightAndSize(ALDATreeSetNode<E> node){
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        node.size = size(node.left) + size(node.right) + 1;
    }

    private ALDATreeSetNode<E> doubleRightToLeft(){
        right = right.rotateLeftToRight();
        return rotateRightToLeft();
    }

    private ALDATreeSetNode<E> doubleLeftToRight(){
        left = left.rotateRightToLeft();
        return rotateLeftToRight();
    }

    private int size(ALDATreeSetNode<E> node){
        return node == null ? 0 : node.size;
    }

    private int height(ALDATreeSetNode<E> node){
        return node == null ? -1 : node.height;
    }

    protected boolean contains(E data) {
        int compareInt = data.compareTo(this.data);
        if (compareInt == 0){
            return true;
        }
        else if(left != null && compareInt < 0){
            return left.contains(data);
        }
        else if (right != null){
            return right.contains(data);
        }
        else{
            return false;
        }
    }

    protected int height(){
        return height;
    }

    public String toString() {
        String returnString = "";
        if(left != null){
            returnString += left.toString() + ", ";
        }
        returnString += data;
        if (right != null){
            returnString += ", " + right.toString();
        }
        return returnString;
    }
}
