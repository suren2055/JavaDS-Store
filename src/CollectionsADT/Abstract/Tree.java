package CollectionsADT.Abstract;



public interface Tree<T extends Comparable<T>> {

    boolean insert(T v);
    boolean remove(T v);
    boolean contains(T v);
    int size();
    boolean isEmpty();
    int height();

//    T element(T node);
//
//    T root();
//
//    T parent(T node);
//
//    LinkedList<T> children(T node);
//
//    boolean isInternal(T node);
//
//    boolean isExternal(T node);//isLeaf
//
//    boolean isRoot(T node);

//    positions();
//
//    elements();




}
