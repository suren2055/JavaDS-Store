package CollectionsADT;

import CollectionsADT.Abstract.Map;

import java.util.Iterator;


public class HashMap<K extends Comparable<K>, V extends Comparable<V>> implements Map<K, V>, Iterable<HashMap.Entry<K, V>> {


    public class EntryIterator implements Iterator<Entry<K, V>> {
        Entry<K, V> next;
        Entry<K, V> current;
        int index;

        public EntryIterator() {

            Entry<K, V>[] t = hashtable;
            current = next = hashtable[0];
            index = 0;
            if (t != null && size > 0) {
                do {
                } while (index < t.length && (next = t[index++]) == null);
            }
        }

        public boolean hasNext() {
            return next != null;

        }

        public Entry<K, V> next() {
            Entry<K, V>[] t;
            Entry<K, V> e = next;

            if ((next = (current = e)._next) == null && (t = hashtable) != null) {
                do {
                } while (index < t.length && (next = t[index++]) == null);
            }
            return e;
        }
    }

    public static class Entry<K, V> {

        public K _key;
        public V _value;
        public Entry _next;

        public Entry(K _key, V _value, Entry _next) {
            this._key = _key;
            this._value = _value;
            this._next = _next;
        }

        @Override
        public String toString() {
            return "Key=" + _key + ", Value=" + _value;
        }
    }

    public Iterator<Entry<K, V>> iterator() {

        return new EntryIterator();
    }

    private int size;
    private Entry[] hashtable;
    private int capacity = 5;

    public HashMap() {
        size = 0;
        hashtable = new Entry[capacity];
    }

    public HashMap(int c) {
        size = 0;
        capacity = c;
        hashtable = new Entry[capacity];
    }

    private int hash(K key) {

        return Math.abs(key.hashCode()) % capacity;

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V put(K key, V value) {

        int index = hash(key);
        if (hashtable[index] == null) {
            hashtable[index] = new Entry(key, value, null);
            size++;
            return value;
        }
        Entry e = hashtable[index];
        while (e != null) {
            if (e._key.equals(key)) {
                V oldValue = (V) e._value;
                e._value = value;
                return oldValue;
            }
            e = e._next;
        }
        hashtable[index] = new Entry(key, value, hashtable[index]);
        size++;
        return value;
    }

    public V get(K key, V value) {// I think Value parameter is not necessary, however I added as said in the task
        int index = hash(key);
        Entry e = hashtable[index];

        while (e != null) {
            if (e._key.equals(key) /*&& e._value.equals(value)*/) {
                return (V) e._value;
            }
            e = e._next;

        }
        return null;

    }

    public V get(K key) {
        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        Entry e = hashtable[index];
        V v = (V) e._value;
        if (e._next == null) {
            v = (V) e._value;
            hashtable[index] = null;
        } else {
            while (e != null) {
                if (e._next._key.equals(key)) {
                    v = (V) e._next._value;
                    e._next = e._next._next;
                    break;
                } else
                    e = e._next;
            }
        }
        size--;
        return v;
    }

    public HashSet keySet() {

        HashSet set = new HashSet();
        for (int i = 0; i < capacity; i++) {
            Entry n = hashtable[i];
            while (n != null) {
                set.add((K) n._key);
                n = n._next;


            }
        }

        return set;
    }

    public LinkedList values() {
        return null;
    }

    public HashSet entrySet() {
        return null;
    }

    public boolean hasValue(V v) {
        if (isEmpty())
            return false;
        for (int x = 0; x < hashtable.length; x++) {
            if (hashtable[x] != null) {
                if (hashtable[x]._value == v)
                    return true;
                else {
                    if (hashtable[x]._next != null) {
                        Entry temp = hashtable[x]._next;
                        while (temp != null) {
                            if (temp._value.equals(v))
                                return true;
                            temp = temp._next;
                        }
                    }
                }
            }
        }
        return false;
        //The complexity of given function is O(n^2)
    }

}
