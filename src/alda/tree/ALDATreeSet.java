package alda.tree;

import java.util.AbstractSet;
import java.util.Iterator;

/**
 *
 * @param <E> The type of the element in the TreeSet
 */

public class ALDATreeSet<E extends Comparable<E>> extends AbstractSet<E> implements Iterable<E> {
    private ALDATreeSetNode<E> root;
    private ALDATreeSetNode<E> first = new ALDATreeSetNode<E>(null, null, null);
    private ALDATreeSetNode<E> last = new ALDATreeSetNode<E>(null,null,null);
    private int size = 0;

    /**
     *
     * @return An iterator connected from the first element.
     */
    @Override
    public Iterator<E> iterator() {
        return new ALDATreeSetIterator<E>(first);
    }

    /**
     *
     * @param data The element to be added to the TreeSet
     * @return True if the element has been added, false if it already has been added to TreeSet
     */

    public boolean add(E data) {
        if (root == null) {
            root = new ALDATreeSetNode<E>(data);
            first.setSuccessor(root);
            last.setPredecessor(root);
            ++size;
            return true;

        } else {
            int tempsize = root.size();
            root = root.add(data);
            if(root.size() > tempsize){
                size = root.size();
                first.setSuccessor(root.findMin());
                last.setPredecessor(root.findMax());
                return true;
            }
            return false;
        }
    }

    /**
     *
     * @param data The data to be removed
     * @return True if the data has been removed from the TreeSet
     */

    public boolean remove(E data) {
        int tempsize  = root.size();
        if(root != null){
            root = root.remove(data);
            if(root != null && root.size() < tempsize){
                size = root.size();
                first.setSuccessor(root.findMin());
                last.setPredecessor(root.findMax());
                return true;
            }
            else if(root == null){
                clear();
                return true;
            }
            else
                return false;
        }
        else {
            return false;
        }
    }

    /**
     *
     * @param data The element that will be compared to the elements in the list
     * @return True if the Set contains the element, false otherwise
     */

    public boolean contains(E data){
        if(root != null){
            return root.contains(data);
        }
        else{
            return false;
        }
    }

    /**
     *
     * @return the number of elements in the TreeSet
     */

    @Override
    public int size() {
        return size;
    }

    /**
     *
     * @return the height of the root
     */
    public int height(){
        return root == null ? -1 : root.height();
    }

    /**
     *
     * @return a string representation of this TreeSet
     */

    @Override
    public String toString(){
        if (root != null)
            return "[" + root.toString() + "]";
        else{
            return "[]";
        }
    }

    /**
     * Clears the tree from elements
     */

    @Override
    public void clear(){
        size = 0;
        root = null;
        first.setSuccessor(null);
        last.setPredecessor(null);
    }

    private class ALDATreeSetIterator<E extends Comparable<E>> implements Iterator<E> {
        private ALDATreeSetNode<E> current;

        private ALDATreeSetIterator(ALDATreeSetNode<E> first){
            this.current = first;
        }

        @Override
        public boolean hasNext() {
            return current.getSuccessor() != null;
        }

        @Override
        public E next() {
            current = current.getSuccessor();
            return current.getData();
        }
    }


}
