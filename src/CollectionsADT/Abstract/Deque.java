package CollectionsADT.Abstract;

public interface Deque<T> {

    void pushFront(T o);
    void pushBack(T o);
    T popFront();
    T popBack();
    T front();
    T back();
    int size();
    boolean isEmpty();
    void reverse();

}
