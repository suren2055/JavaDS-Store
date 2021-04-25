package CollectionsADT;

import CollectionsADT.Abstract.Map;


import java.util.Comparator;
import java.util.Iterator;

//Based on on red-black
public class TreeMap<K extends Comparable<K>, V extends Comparable<V>> implements Map<K, V>, Iterable<TreeMap.Entry<K, V>> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

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

    static class Entry<K, V> {

        private K _key;
        private V _value;
        private Entry<K, V> _left = null;
        private Entry<K, V> _right = null;
        private Entry<K, V> _parent;
        boolean color = BLACK;

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

    public Iterator<Entry<K, V>> iterator() {
        return null;
    }

    public V put(K key, V value) {
        if (_cmp == null)
            return insertIterative(key, value);
        return insertIterativeCmp(key, value);
    }
    public V remove(K key) {
        Entry<K, V> p = getEntry(key);
        if (p == null)
            return p._value;
        V v = p._value;
        if (_cmp == null)
            removeIterative(_root, key);
        else
            removeIterativeCmp(_root, key);
        return v;
    }
    public V get(K key, V value) {
        return getEntry(key)._value;
    }
    public V get(K key) {
        return getEntry(key)._value;
    }
    public int size() {
        return _size;
    }
    public boolean isEmpty() {
        return _size == 0;
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

    public void prettyPrint() {
        printHelper(this._root, "", true);
    }

    private V insertIterativeCmp(K key, V value) {
        Entry temp = _root;
        Entry<K, V> toInsert = new Entry<>(key, value);
        if (this.isEmpty()) {
            _root = toInsert;
            _root.color = BLACK;
            _size++;
        } else {
            while (temp != null) {
                if ((_cmp.compare(key, (K) temp._key)) == 0) {
                    V oldValue = (V) temp._value;
                    temp._value = value;
                    fixAfterInsertion(toInsert);
                    return oldValue;
                }//update
                if ((_cmp.compare(key, (K) temp._key)) > 0) {
                    if (temp._right == null) {
                        temp._right = toInsert;
                        temp._right._parent = temp;
                        _size++;
                        return value;
                    } else
                        temp = temp._right;
                }//right
                if ((_cmp.compare(key, (K) temp._key)) < 0) {
                    if (temp._left == null) {
                        temp._left = toInsert;
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
    private boolean removeIterativeCmp(Entry<K, V> n, K key) {
        Entry<K, V> temp = n;
        while (temp != null) {
            if (_cmp.compare(key, temp._key) == 0) {
                if (temp._left == null && temp._right == null) {
                    Entry<K, V> parent = temp._parent;
                    if (temp == parent._left)
                        parent._left = null;
                    else
                        parent._right = null;
                    return true;

                }
                if (temp._left != null && temp._right != null) {
                    K newKey = getLargest(temp._left)._key;//getSmallest(temp._right);
                    temp._key = newKey;
                    removeIterative(temp._left, newKey);//removeIterative(n._right,newValue);
                    return true;
                }
                Entry<K, V> child = temp._right;
                if (child == null)
                    child = temp._left;
                Entry<K, V> parent = temp._parent;
                if (temp == parent._left)
                    parent._left = child;
                else
                    parent._right = child;
                return true;
            }
            if (_cmp.compare(key, temp._key) > 0)
                temp = temp._right;
            if (_cmp.compare(key, temp._key) < 0)
                temp = temp._left;
        }
        return false;
    }
    private V insertIterative(K key, V value) {
        Entry temp = _root;
        Entry<K, V> toInsert = new Entry<>(key, value);
        toInsert.color = RED;
        if (this.isEmpty()) {
            _root = toInsert;
            _size++;
        } else {
            while (temp != null) {
                if (key.compareTo((K) temp._key) == 0) {
                    V oldValue = (V) temp._value;
                    temp._value = value;
                    return oldValue;
                }//update
                if (key.compareTo((K) temp._key) > 0) {
                    if (temp._right == null) {
                        temp._right = toInsert;
                        temp._right._parent = temp;
                        _size++;
                        if (toInsert._parent == null){
                            toInsert.color = BLACK;
                            return value;
                        }
                        if (toInsert._parent._parent == null) {
                            return value;
                        }
                        fixAfterInsertion(toInsert);
                        return value;
                    } else
                        temp = temp._right;
                }//right
                if (key.compareTo((K) temp._key) < 0) {
                    if (temp._left == null) {
                        temp._left = toInsert;
                        temp._left._parent = temp;
                        _size++;
                        if (toInsert._parent == null){
                            toInsert.color = BLACK;
                            return value;
                        }

                        if (toInsert._parent._parent == null) {
                            return value;
                        }
                        fixAfterInsertion(toInsert);
                        return value;
                    } else
                        temp = temp._left;
                }//left
            }
        }

        return value;
    }
    private boolean removeIterative(Entry<K, V> n, K key) {
        Entry<K, V> temp = n;
        while (temp != null) {
            if (key.compareTo(temp._key) == 0) {
                if (temp._left == null && temp._right == null) {
                    Entry<K, V> parent = temp._parent;
                    if (temp == parent._left)
                        parent._left = null;
                    else
                        parent._right = null;
                    return true;

                }
                if (temp._left != null && temp._right != null) {
                    K newKey = getLargest(temp._left)._key;//getSmallest(temp._right);
                    temp._key = newKey;
                    removeIterative(temp._left, newKey);//removeIterative(n._right,newValue);
                    return true;
                }
                Entry<K, V> child = temp._right;
                if (child == null)
                    child = temp._left;
                Entry<K, V> parent = temp._parent;
                if (temp == parent._left)
                    parent._left = child;
                else
                    parent._right = child;
                return true;
            }
            if (key.compareTo(temp._key) > 0)
                temp = temp._right;
            if (key.compareTo(temp._key) < 0)
                temp = temp._left;
        }
        return false;
    }


    private void leftRotate(Entry<K, V> n) {
        Entry<K, V> x = n;
        Entry<K, V> y = n._right;
        Entry<K, V> k = y._left;

        if (n == n._parent._left)
            n._parent._left = n._right;
        else
            n._parent._right = n._right;

        n._right._parent = n._parent;
        y._left = x;
        x._parent = y;
        x._left = k;
        if (k == null)
            k._parent = x;

//        Entry<K, V> y = x._right;
//        x._right = y._left;
//        if (y._left != null) {
//            y._left._parent = x;
//        }
//        y._parent = x._parent;
//        if (x._parent == null) {
//            this._root = y;
//        } else if (x == x._parent._left) {
//            x._parent._left = y;
//        } else {
//            x._parent._right = y;
//        }
//        y._left = x;
//        x._parent = y;


    }

    private void rightRotate(Entry<K, V> x) {

//        Entry<K, V> x = n;
//        Entry<K, V> y = n._left;
//        Entry<K, V> k = y._right;
//
//        if (n == n._parent._left)
//            n._parent._left = y;
//        else
//            n._parent._right = y;
//
//        y._right = x;
//        x._parent = y;
//        x._right = k;
//        if (k == null)
//           k._parent = x;

        Entry<K, V> y = x._left;
        x._left = y._right;
        if (y._right != null) {
            y._right._parent = x;
        }
        y._parent = x._parent;
        if (x._parent == null) {
            this._root = y;
        } else if (x == x._parent._right) {
            x._parent._right = y;
        } else {
            x._parent._left = y;
        }
        y._right = x;
        x._parent = y;
    }

    private Entry<K, V> getSmallest(Entry<K, V> n) {

        if (n == null)
            return null;
        Entry<K, V> t = n;
        while (t._left != null)
            t = t._left;

        return t;
    }

    private Entry<K, V> getLargest(Entry<K, V> n) {

        if (n != null)
            return null;
        Entry<K, V> t = n;
        while (t._right != null)
            t = t._right;


        return t;
    }

    private Entry<K, V> getEntry(K key) {

        Entry<K, V> p = _root;
        int cmp;
        while (p != null) {
            if (_cmp == null)
                 cmp = key.compareTo(p._key);
            else
                 cmp = _cmp.compare(key, p._key);
            if (cmp < 0)
                p = p._left;
            else if (cmp > 0)
                p = p._right;
            else
                return p;
        }
        return null;
    }


    private void fixAfterInsertion(Entry<K, V> k) {
        Entry<K, V> u;
        while (k._parent.color == RED) {
            if (k._parent == k._parent._parent._right) {
                u = k._parent._parent._left;
                if (u.color == RED) {
                    u.color = BLACK;
                    k._parent.color = BLACK;
                    k._parent._parent.color = RED;
                    k = k._parent._parent;
                } else {
                    if (k == k._parent._left) {
                        k = k._parent;
                        rightRotate(k);
                    }
                    k._parent.color = BLACK;
                    k._parent._parent.color = RED;
                    leftRotate(k._parent._parent);
                }
            } else {
                u = k._parent._parent._right;
                if (u.color == RED) {
                    u.color = BLACK;
                    k._parent.color = BLACK;
                    k._parent._parent.color = RED;
                    k = k._parent._parent;
                } else {
                    if (k == k._parent._right) {
                        k = k._parent;
                        leftRotate(k);
                    }
                    k._parent.color = BLACK;
                    k._parent._parent.color = RED;
                    rightRotate(k._parent._parent);
                }
            }
            if (k == _root) {
                break;
            }
        }
        _root.color = BLACK;
    }
    private void fixAfterDeletion(Entry<K, V> x) {
        Entry<K, V> s;
        while (x != _root && x.color == BLACK) {
            if (x == x._parent._left) {
                s = x._parent._right;
                if (s.color == RED) {
                    s.color = BLACK;
                    x._parent.color = RED;
                    leftRotate(x._parent);
                    s = x._parent._right;
                }
                if (s._left.color == BLACK && s._right.color == BLACK) {
                    s.color = RED;
                    x = x._parent;
                } else {
                    if (s._right.color == BLACK) {
                        s._left.color = BLACK;
                        s.color = RED;
                        rightRotate(s);
                        s = x._parent._right;
                    }
                    s.color = x._parent.color;
                    x._parent.color = BLACK;
                    s._right.color = BLACK;
                    leftRotate(x._parent);
                    x = _root;
                }
            } else {
                s = x._parent._left;
                if (s.color == RED) {
                    s.color = BLACK;
                    x._parent.color = RED;
                    rightRotate(x._parent);
                    s = x._parent._left;
                }
                if (s._right.color == BLACK && s._right.color == BLACK) {
                    s.color = RED;
                    x = x._parent;
                } else {
                    if (s._left.color == BLACK) {

                        s._right.color = BLACK;
                        s.color = RED;
                        leftRotate(s);
                        s = x._parent._left;
                    }
                    s.color = x._parent.color;
                    x._parent.color = BLACK;
                    s._left.color = BLACK;
                    rightRotate(x._parent);
                    x = _root;
                }
            }
        }
        x.color = BLACK;

    }

    private void printHelper(Entry<K, V> root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            String sColor = root.color == RED ?"RED":"BLACK";
            System.out.println(root._value + "(" + sColor + ")");
            printHelper(root._left, indent, false);
            printHelper(root._right, indent, true);
        }
    }
    public void printInorder() {
        System.out.println("Printing binary tree using inorder");
        inOrder(_root);
    }

    private void inOrder(Entry<K, V> r) {

        if (r == null)
            return;
        inOrder(r._left);
        //process(n._value);
        System.out.println(r._value);
        inOrder(r._right);
    }
}