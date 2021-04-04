package CollectionsADT;

import CollectionsADT.Abstract.Set;
import Models.Book;

import java.util.Random;

public class HashSet<T extends Comparable<T>> implements Set<T> {
    private class Node<T> {
        T _element;
        Node _next;

        public Node(T _element, Node _next) {
            this._element = _element;
            this._next = _next;
        }

    }

    private int size;
    private Node[] hashtable;
    private int capacity = 15;

    public HashSet() {
        size = 0;
        hashtable = new Node[capacity];
    }

    public HashSet(int c) {
        size = 0;
        capacity = c;
        hashtable = new Node[capacity];
    }

    private int hash(T element) {
        return Math.abs(element.hashCode()) % capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T e) {

        if (contains(e))
            return false;

        int hashIndex = hash(e);

        if (hashtable[hashIndex] == null) {
            hashtable[hashIndex] = new Node(e, null);
            return true;
        } else {

            Node n = hashtable[hashIndex];
            while (n != null) {

                if (e.equals(n._element))
                    return false;

                n = n._next;
            }

            hashtable[hashIndex] = new Node(e, hashtable[hashIndex]);
            size++;
            return true;

        }
    }

    @Override
    public boolean remove(T e) {
        if (!contains(e))
            return false;
        int index = hash(e);
        if (hashtable[index]._element.equals(e)) {
            hashtable[index] = hashtable[index]._next;
            return true;
        }
        Node n = hashtable[index];
        Node p = null;
        while (n != null) {
            if (n._element.equals(e))
                p._next = n._next;

            p = n;
            n = n._next;
        }
        size--;
        return false;
    }

    @Override
    public boolean contains(T e) {

        int index = hash(e);
        if (hashtable[index] == null)
            return false;
        Node n = hashtable[index];
        while (n != null) {

            //also can use compareTo()==0
            if (n._element.equals(e))
                return true;
            n = n._next;
        }


        return false;
    }

    @Override
    public boolean equals(Set set) {

        if (!(size==set.size()))
            return false;

        for (int i = 0;i<hashtable.length;i++) {
            if (hashtable[i]!=null) {
                Node n = hashtable[i];
                while(n!=null) {
                    if (!set.contains((T)n._element))
                        return false;
                    n=n._next;
                }

            }

        }

        return true;
    }

    @Override
    public boolean containsAll(Set set) {
        return false;
    }

    @Override
    public boolean addAll(Set set) {
        return false;
    }

    @Override
    public boolean removeAll(Set set) {
        return false;
    }

    @Override
    public boolean retainAll(Set set) {
        return false;
    }


}
