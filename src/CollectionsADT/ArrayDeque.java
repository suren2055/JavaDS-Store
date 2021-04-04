package CollectionsADT;

import CollectionsADT.Abstract.Deque;
import Models.Product;

import java.util.Iterator;


public class ArrayDeque<T> implements Deque<T>, Iterable<T>, Comparable<ArrayDeque<Product>> {

    private Object[] objects;
    private int first;
    private int size;




    public class ArrayDequeLazyIterator implements Iterator<T> {

        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex<size;
        }

        @Override
        public T next() {
           T toReturn  = (T)objects[currentIndex];
           currentIndex++;
           return toReturn;

        }


    }
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeLazyIterator();
    }
    @Override
    public int compareTo(ArrayDeque<Product> o) {
        return 0;
    }


    public ArrayDeque() {
        objects = new Object[10];
    }

    public ArrayDeque(int capacity) {
        objects = new Object[capacity];
    }

    public void print() {
        for (int x = 0; x < objects.length; x++)
            System.out.println(objects[x]);
    }

    @Override
    public void pushFront(T o) {
        if ((first + size) > objects.length - 1)
            resize();

        for (int k = size; k > first; k--)
            objects[(first + k) % objects.length] = objects[(first + k - 1) % objects.length];

        objects[first % objects.length] = o;
        size++;
    }

    @Override
    public void pushBack(T o) {

        if ((first + size) > objects.length - 1)
            resize();

        objects[(first + size) % objects.length] = o;
        size++;
    }

    @Override
    public T popFront() {
        first = (first + 1) % objects.length;
        size--;
        return (T) objects[first];
    }

    @Override
    public T popBack() {
        objects[(first + size - 1) % objects.length] = null;
        size--;
        return (T) null;
    }

    @Override
    public T front() {
        return (T)objects[first % objects.length];
    }

    @Override
    public T back() {
        return (T)objects[(first + size - 1) % objects.length];
    }

    @Override
    public int size() {
        return getSize();
    }
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void reverse() {
        if (size==0)
            return;
        T n = front();
        popFront();
        reverse();
        pushBack(n);


    }


    private void resize() {
        Object[] newArray = new Object[size * 2];
        for (int x = 0; x < objects.length; x++)
            newArray[x] = objects[x];
        objects = newArray;
    }


}
