package CollectionsADT.Abstract;

import CollectionsADT.HashSet;
import CollectionsADT.LinkedList;

public interface Map<K  extends Comparable<K>,V extends Comparable<V>> extends Collection {

    V put(K key, V value);
    V get(K key, V value);
    V get(K key);
    V remove(K key);
    HashSet keySet();
    LinkedList<V> values();
    HashSet entrySet();

}
