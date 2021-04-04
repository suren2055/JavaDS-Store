package CollectionsADT;

import CollectionsADT.Abstract.Queue;

public class LinkedListQueue<T> extends LinkedList<T> implements Queue<T> {


    @Override
    public void enqueue(T obj) {
        addLast(obj);
    }

    @Override
    public T dequeue() {

        return removeFirst();
    }

    @Override
    public void reverse() {

    }
}
