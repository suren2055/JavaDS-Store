package CollectionsADT.Abstract;

public interface List<T> {

    public T first();
    public T last();
    public boolean addFirst(T obj);

    public boolean addLast(T obj);
    public T removeFirst();
    public boolean removeLast();
    public T elementAt(int index);

    public int size();
    public boolean isEmpty();



}
