package CollectionsADT;


import CollectionsADT.Abstract.Deque;
import CollectionsADT.Abstract.List;
import Models.Product;

import java.util.Iterator;


public class LinkedList<T> implements List<T>, Deque<T>, Comparable<LinkedList<Product>>, Iterable<T> {

    Node first;
    Node last;
    int size;

    private class Node {
        public T _obj;
        public Node _next;

        public Node(T obj) {
            _obj = obj;
            _next = null;
        }

        public Node(T obj, Node next) {
            _obj = obj;
            _next = next;
        }
    }

    public LinkedList() {
        first = last = null;
        size = 0;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Node current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    T data = current._obj;
                    current = current._next;
                    return data;
                }
                return null;
            }

        };

    }

    public void printAll() {
        System.out.println("-----------------Linked List----------------------");
        if (isEmpty())
            System.out.println("List is empty");
        else
            for (int i = 0; i < size; i++)
                System.out.println(elementAt(i));


    }

    public int compareTo(LinkedList<Product> o) {
        return 0;
    }

    public T first() {
        return first._obj;
    }

    public T last() {
        return last._obj;
    }

    public boolean addFirst(T obj) {
        Node node = new Node(obj, first);

        if (isEmpty()) {
            first = last = node;
        } else {
            first = node;
        }
        size++;
        return true;
    }

    public boolean addLast(T obj) {
        Node node = new Node(obj, null);

        if (isEmpty()) {
            first = last = node;
        } else {
            last._next = node;
            last = node;

        }
        size++;
        return true;
    }

    public T removeFirst() {

        if (isEmpty()) {
            return null;
        }
        T toReturn = first._obj;
        if (first._next == null) {
            first = last = null;
        } else {
            Node temp = first;

            first = first._next;
            temp._next = null;
        }
        size--;
        return toReturn;
    }

    public boolean removeLast() {
        if (isEmpty()) {
            return false;
        }
        if (first._next == null) {
            first = last = null;
        } else {
            Node temp = first;
            for (int i = 0; i < size - 1; i++) {
                temp = temp._next;
            }
            last = temp;
        }
        size--;
        return true;
    }

    public T elementAt(int index) {

        if (isEmpty() || index > size)
            return null;
        Node temp = first;
        for (int i = 0; i < index; i++)
            temp = temp._next;

        return temp._obj;
    }

    public void pushFront(T o) {

        addFirst(o);


    }

    //Deque
    public void pushBack(T o) {

        addLast(o);
    }

    public T popFront() {
        return removeFirst();
    }

    public T popBack() {
        removeLast();

        return null;
    }

    public T front() {
        return first();
    }

    public T back() {
        return last();
    }

    public int size() {
        return getSize();
    }

    public boolean isEmpty() {
        return 0 == getSize();
    }

    public void reverse() {
        if (size <= 1)
            return;
        Node n = first;
        removeFirst();
        reverse();
        addLast(n._obj);
    }

    public int getSize() {
        return size;
    }
}
