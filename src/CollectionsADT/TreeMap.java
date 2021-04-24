package CollectionsADT;

import CollectionsADT.Abstract.Map;


import java.util.Comparator;
import java.util.Iterator;


public class TreeMap<K extends Comparable<K>, V extends Comparable<V>> implements Map<K, V>, Iterable<HashMap.Entry<K, V>> {

    private Entry<K, V> _root;
    private int _size;
    private Comparator<K> _cmp;

    public TreeMap() {
        _root = null;
        _size = 0;
        _cmp = null;
    }

    public TreeMap(Comparator<K> cmp) {
        _root = null;
        _size = 0;
        this._cmp = cmp;
    }


    public Iterator<HashMap.Entry<K, V>> iterator() {
        return null;
    }

    private class Entry<K, V> {

        private K _key;
        private V _value;
        private Entry<K, V> _left = null;
        private Entry<K, V> _right = null;
        private Entry<K, V> _parent;

        public Entry(K _key, V _value) {
            this._key = _key;
            this._value = _value;
            _parent = null;
        }


        public V get_value() {
            return _value;
        }

        public void set_value(V _value) {
            this._value = _value;
        }

        public Entry<K, V> get_parent() {
            return _parent;
        }

        public void set_parent(Entry<K, V> _parent) {
            this._parent = _parent;
        }
    }

    public V put(K key, V value) {

        if (_cmp==null)
            return insertIterative(key,value);

        return insertIterativeCmp(key,value);
    }
    private V insertIterativeCmp(K key,V value) {
        Entry temp = _root;
        if (this.isEmpty()) {
            _root = new Entry(key, value);
            _size++;
        } else {
            while (temp != null) {
                if ((_cmp.compare(key,(K)temp._key)) == 0) {
                    V oldValue = (V)temp._value;
                    temp._value = value;
                    return oldValue;
                }//update

                if ((_cmp.compare(key,(K)temp._key)) > 0) {
                    if (temp._right == null) {
                        temp._right = new Entry(key,value);
                        temp._right._parent = temp;
                        _size++;
                        return value;
                    } else
                        temp = temp._right;
                }//right
                if ((_cmp.compare(key,(K)temp._key)) < 0) {
                    if (temp._left == null) {
                        temp._left = new Entry(key,value);
                        temp._left._parent = temp;
                        _size++;
                        return value;
                    } else
                        temp = temp._left;
                }//left
            }
        }
        return value;
    }
    private V insertIterative(K key,V value) {
        Entry temp = _root;
        if (this.isEmpty()) {
            _root = new Entry(key, value);
            _size++;
        } else {
            while (temp != null) {
                if (key.compareTo((K) temp._key) == 0) {
                    V oldValue = (V)temp._value;
                    temp._value = value;
                    return oldValue;
                }//update

                if (key.compareTo((K) temp._key) > 0) {
                    if (temp._right == null) {
                        temp._right = new Entry(key,value);
                        temp._right._parent = temp;
                        _size++;
                        return value;
                    } else
                        temp = temp._right;
                }//right
                if (key.compareTo((K) temp._key) < 0) {
                    if (temp._left == null) {
                        temp._left = new Entry(key,value);
                        temp._left._parent = temp;
                        _size++;
                        return value;
                    } else
                        temp = temp._left;
                }//left
            }
        }
        return value;
    }

    public int size() {
        return _size;
    }

    public boolean isEmpty() {
        return _size == 0;
    }

    public V get(K key, V value) {
        return null;
    }

    public V get(K key) {
        return null;
    }

    public V remove(K key) {
        return null;
    }

    public HashSet keySet() {
        return null;
    }

    public LinkedList<V> values() {
        return null;
    }

    public HashSet entrySet() {
        return null;
    }

}