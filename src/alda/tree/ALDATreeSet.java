package alda.tree;

import java.util.AbstractSet;
import java.util.Iterator;


public class ALDATreeSet<E extends Comparable<E>> extends AbstractSet<E> implements Iterable<E> {
    private ALDATreeSetNode<E> root;
    private int size = 0;

    @Override
    public Iterator<E> iterator() {
        return new ALDATreeSetIterator<E>(root);
    }

    public boolean add(E data) {
        if (root == null) {
            root = new ALDATreeSetNode<E>(data);
            ++size;
            return true;

        } else {
            int previousSize = size;
            size = root.add(data);
            return size > previousSize;
        }
    }

    public boolean contains(E data){
        if(root != null){
            return root.contains(data);
        }
        else{
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

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

        private ALDATreeSetIterator(ALDATreeSetNode<E> root){
            this.current = root;
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
