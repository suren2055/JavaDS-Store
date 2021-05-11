package CollectionsADT;

import CollectionsADT.Abstract.List;

import java.util.Iterator;

public class ArrayList<T> implements List<T>, Iterable<T> {
    private T[] _obj;
    int size;

    public ArrayList() {
        _obj = (T[])new Object[10];
        size = 0;
    }

    public ArrayList(int capacity) {
        _obj = (T[]) new Object[capacity];
        size = 0;
    }

    // Task1: Implement swap(Book b1, Book b2) function,
    public void swap(T b1, T b2) {

        if (_obj.length <= 1)
            return;
        int b1Index = 0;
        int b2Index = 0;
        for (int i = 0; i < _obj.length; i++) {
            if (_obj[i].equals(b1))
                b1Index = i;
            if (_obj[i].equals(b2))
                b2Index = i;

        }
        T temp = _obj[b1Index];
        _obj[b1Index] = _obj[b2Index];
        _obj[b2Index] = temp;


    }

    // Task2: Implement index iterator for which starts the iteration from the element
    // under the given index
    public Iterator<T> indexIterator(int index) {

        ArrayListLazyIterator li = new ArrayListLazyIterator();
        if (_obj.length>1) {
            li.currentIndex = index - 1;
            return li;
        }


        return null;
    }

    public class ArrayListLazyIterator implements Iterator<T> {
        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            T toReturn = _obj[currentIndex];
            currentIndex++;
            return toReturn;
        }
    }

    @Override
    public T first() {
        if (isEmpty()) {
            return null;
        }
        return _obj[0];
    }

    @Override
    public T last() {
        if (isEmpty()) {
            return null;
        }
        return _obj[size - 1];
    }

    private void resize() {
        if (size < _obj.length) {
            return;
        }
        Object[] temp = new Object[2 * size];
        for (int i = 0; i < _obj.length; i++) {
            temp[i] = _obj[i];
        }
        _obj = (T[])temp;
    }

    @Override
    public boolean addFirst(T b) {
        resize();
        if (isEmpty()) {
            _obj[0] = b;
        } else {
            for (int i = size - 1; i >= 0; i--) {
                _obj[i + 1] = _obj[i];
            }
            _obj[0] = b;
        }
		/*
		if (!isEmpty()) {
			for (int i = size-1; i >= 0; i--) {
				books[i + 1] = books[i];
			}

		}
		books[0] = b;
		 */
        size++;
        return true;
    }

    @Override
    public boolean addLast(T b) {
        resize();
        _obj[size] = b;
        size++;
        return true;
    }


    public T removeFirst() {
        // TODO Auto-generated method stub
        return null;
    }


    public boolean removeLast() {
        return false;
    }


    public T elementAt(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return _obj[index];
    }

    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return 0 == size;
    }


    public Iterator<T> iterator() {
        return new ArrayListLazyIterator();
    }



    public void clear() {
        size = 0;
    }


}
