package alda.tree;

import java.lang.Math;

public class ALDATreeSetNode<E extends Comparable<E>> {
    private static final int MAXIMUM_INBALANCE = 1;
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

    //Package Private
    E getData(){
        return data;
    }

    public int size(){
        return size;
    }

    public ALDATreeSetNode<E> add(E data) {
        int lessOrGreaterInt = data.compareTo(this.data);

        if (lessOrGreaterInt < 0) {
            if (left == null) {
                left = new ALDATreeSetNode<E>(data);
                size++;
                height = Math.max(height(left), height(right)) + 1;
            }
            else {
                int tempsize = left.size;
                left = left.add(data);
                if(left.size > tempsize){
                    size++;
                    height = Math.max(height(left), height(right)) + 1;
                }
            }
        }

        else if(lessOrGreaterInt > 0) {
            if (right == null) {
                right = new ALDATreeSetNode<E>(data);
                size++;
                height = Math.max(height(left), height(right)) + 1;
            }
            else{
                int tempsize = right.size;
                right = right.add(data);
                if(right.size > tempsize){
                    size++;
                    height = Math.max(height(left), height(right)) + 1;
                }

            }
        }
        else{
            return this;
        }

        return balance();
    }

    private ALDATreeSetNode<E> balance(){
        if(right != null && left != null){
            if(left.height - right.height > MAXIMUM_INBALANCE){
                return leftImbalance();
            }
            else if (right.height - left.height > MAXIMUM_INBALANCE){
                return rightImbalance();
            }
            else{
                return this;
            }
        }
        else if(right == null){
            if(left.height > MAXIMUM_INBALANCE){
                return leftImbalance();
            }
        }
        else{
            if(right.height > MAXIMUM_INBALANCE){
                return rightImbalance();
            }
        }
        return this;
    }

    private ALDATreeSetNode<E> leftImbalance(){
        if (height(left.left) > height(left.right)){
            return rotateLeftToRight();
        }
        else if(height(left.right) > height(left.left)){
            return doubleLeftToRight();
        }
        else{
            throw new AssertionError();
        }
    }

    private ALDATreeSetNode<E> rightImbalance(){
        if (height(right.right) > height(right.left)){
            return rotateRightToLeft();
        }
        else if(height(right.left) > height(right.right)){
            return doubleRightToLeft();
        }
        else{
            throw new AssertionError();
        }
    }

    private E findMax(){
        if(right != null){
            return right.findMax();
        }
        else{
            return data;
        }
    }

    private E findMin() {
        if(left != null){
            return left.findMin();
        }
        else{
            return data;
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
        right.rotateLeftToRight();
        return rotateRightToLeft();
    }

    private ALDATreeSetNode<E> doubleLeftToRight(){
        left.rotateRightToLeft();
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
