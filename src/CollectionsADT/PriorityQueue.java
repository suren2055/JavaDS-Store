package CollectionsADT;

import CollectionsADT.Abstract.Collection;

import java.util.Iterator;

public class PriorityQueue<T extends Comparable> implements Collection, Iterable<T> {

    private T[] _heap;
    private int _capacity = 10;
    private int _size = 0;

    public PriorityQueue() {
        _heap = (T[]) new Object[_capacity];
    }

    public void enqueue(T o) {
        resize();
        _heap[_size] = o;
        _size++;
        maxHeapify();
    }

    public T dequeue(T o) {

        if (isEmpty())
            return null;
        T toReturn = _heap[0];
        _heap[0] = _heap[_size - 1];
        _size--;
        maxHeapify();
        return toReturn;

    }

    private void maxHeapify() {
        if (isEmpty())
            return;

        int currentIndex = _size - 1;
        int parentIndex = (currentIndex - 1) / 2;
        while (_heap[currentIndex].compareTo(_heap[parentIndex]) > 0) {

            //Swap
            T temp = _heap[currentIndex];
            _heap[currentIndex] = _heap[parentIndex];
            _heap[parentIndex] = temp;
            currentIndex = parentIndex;
            parentIndex = (currentIndex - 1) / 2;

        }

    }

    private void resize() {
    }

    public Iterator<T> iterator() {
        return null;
    }

    public int size() {
        return _size;
    }

    public boolean isEmpty() {
        return _size == 0;
    }


}
