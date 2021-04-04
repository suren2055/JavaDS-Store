package CollectionsADT;

import CollectionsADT.Abstract.Queue;

public class ArrayQueue<T> implements Queue<T> {

    private Object[] objects;
    private int first;
    private int size;

    public ArrayQueue() {
        objects = new Object[10];
    }

    public ArrayQueue(int capacity) {
        objects = new Object[capacity];
    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;

    }

    public void print() {
        for (int x = 0; x < objects.length; x++)
            System.out.println(objects[x]);
    }

    @Override
    public void enqueue(T obj) {
        if ((first + size) == objects.length)
            resize();

        objects[(first + size) % objects.length] = obj;
        size++;
    }

    @Override
    public T dequeue() {
        T toReturn = (T) objects[first];
        first = (first + 1) % objects.length;
        size--;
        return toReturn;
    }

    private void resize() {


    }
}
