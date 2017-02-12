package alda.tree;

public class ALDATreeSetNode<E extends Comparable<E>> {
    private E data;
    private int size  = 1;
    private int rightDepth;
    private int leftDepth;
    private ALDATreeSetNode<E> predecessor, successor, left, right;


    public ALDATreeSetNode(E data) {
        this.data = data;
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

    public int add(E data) {
        int lessOrGreaterInt = data.compareTo(this.data);

        if (lessOrGreaterInt < 0) {
            if (left == null) {
                left = new ALDATreeSetNode<E>(data);
                return ++size;
            } else {
                int leftSize = left.size;
                return size += left.add(data) - leftSize;
            }
        }

        else if(lessOrGreaterInt > 0) {
            if (right == null) {
                right = new ALDATreeSetNode<E>(data);
                return ++size;
            }
            else{
                int rightSize = right.size;
                return size += right.add(data) - right.size;
            }
        }

        return size;
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

    public boolean contains(E data) {
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

    private int compareDepths(int first, int second){
        if(first >= second){
            return first;
        }
        else{
            return second;
        }
    }

    public int depth() {

        if(left == null && right == null){
            return 0;
        }
        else if(left != null && right != null){
            return 1 + compareDepths(left.depth(), right.depth());
        }
        else if(left != null){
            return 1 + left.depth();
        }
        else{
            return 1 + right.depth();
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
