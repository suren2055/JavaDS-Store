package CollectionsADT.Abstract;

public interface Set<T extends Comparable<T>> extends Collection {
    public boolean add (T e);
    public boolean remove (T e);
    public boolean contains (T e);
    public boolean equals(Set set);
    public boolean containsAll(Set set);
    public boolean addAll(Set set);
    public boolean removeAll(Set set);
    public boolean retainAll(Set set);


}
