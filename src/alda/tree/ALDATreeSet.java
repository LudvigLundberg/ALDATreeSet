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
            ++size;
            return true;

        } else {
            int tempsize = root.size();
            root = root.add(data);
            size = root.size();
            first.setSuccessor(root.findMin());
            last.setPredecessor(root.findMax());
            return size > tempsize;
        }
    }

    /**
     *
     * @param data The element to be added to the TreeSet
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

    public int height(){
        return root == null ? 0 : root.height();
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
