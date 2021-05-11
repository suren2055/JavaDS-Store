package CollectionsADT;

import CollectionsADT.Abstract.Map;


import java.util.Comparator;

//Based on on red-black tree
public class TreeMap<K extends Comparable<K>, V extends Comparable<V>> implements Map<K, V> {

    private Entry<K, V> _root;
    private int _size;
    private Comparator<K> _cmp;
    private Entry<K, V> _leaf;

    public TreeMap() {
        _size = 0;
        _cmp = null;
        _leaf = new Entry<>();
        _leaf.color = 0;
        _leaf._left = null;
        _leaf._right = null;
        _root = _leaf;
    }

    public TreeMap(Comparator<K> cmp) {
        _root = null;
        _size = 0;
        this._cmp = cmp;
    }

    public static class Entry<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Entry<K, V>> {

        private K _key;
        private V _value;
        private Entry<K, V> _left = null;
        private Entry<K, V> _right = null;
        private Entry<K, V> _parent;
        int color; // 1 . Red, 0 . Black

        public Entry() {
        }

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

        public int compareTo(Entry<K, V> o) {

            return this._key.compareTo(o._key);
        }
    }



    public V put(K key, V value) {
        insert(key, value);
        return value;
    }

    public V remove(K key) {
        V toReturn = getEntry(key)._value;
        remove(this._root, key);
        return toReturn;
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

    public Entry successor(Entry x) {
        if (x._right != null) {
            return getSmallest(x._right);
        }
        Entry y = x._parent;
        while (y != null && x == y._right) {
            x = y;
            y = y._parent;
        }
        return y;
    }

    public Entry predecessor(Entry x) {

        if (x._left != null) {
            return getLargest(x._left);
        }

        Entry y = x._parent;
        while (y != null && x == y._left) {
            x = y;
            y = y._parent;
        }

        return y;
    }

    public void prettyPrint() {
        printHelper(this._root, "", true);
    }

    private V insertIterativeCmp(K key, V value) {
        Entry temp = _root;
        Entry<K, V> toInsert = new Entry<>(key, value);
        if (this.isEmpty()) {
            _root = toInsert;
            _root.color = 0;
            _size++;
        } else {
            while (temp != null) {
                if ((_cmp.compare(key, (K) temp._key)) == 0) {
                    V oldValue = (V) temp._value;
                    temp._value = value;
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

    private V insertIterative(Entry<K, V> temp, K key, V value) {
        Entry<K, V> toInsert = new Entry<>(key, value);
        if (this.isEmpty()) {
            toInsert.color = 0;
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
                        return value;
                    } else {
                        temp = temp._right;
                        insertIterative(temp, key, value);
                    }

                }//right
                if (key.compareTo((K) temp._key) < 0) {
                    if (temp._left == null) {
                        temp._left = toInsert;
                        temp._left._parent = temp;
                        _size++;
                        return value;
                    } else {
                        temp = temp._left;
                        insertIterative(temp, key, value);

                    }
                }//left
            }
        }

        return value;
    }

    private void remove(Entry<K, V> node, K key) {
        // find the node containing key
        Entry<K, V> z = _leaf;
        Entry<K, V> x, y;
        while (node != _leaf) {
            if (node._key == key) {
                z = node;
            }

            if (node._key.compareTo(key) < 0 || node._key.compareTo(key) == 0) {
                node = node._right;
            } else {
                node = node._left;
            }
        }

        if (z == _leaf) {
            System.out.println("Couldn't find key in the tree");
            return;
        }

        y = z;
        int yOriginalColor = y.color;
        if (z._left == _leaf) {
            x = z._right;
            rbTransplant(z, z._right);
        } else if (z._right == _leaf) {
            x = z._left;
            rbTransplant(z, z._left);
        } else {
            y = getSmallest(z._right);
            yOriginalColor = y.color;
            x = y._right;
            if (y._parent == z) {
                x._parent = y;
            } else {
                rbTransplant(y, y._right);
                y._right = z._right;
                y._right._parent = y;
            }

            rbTransplant(z, y);
            y._left = z._left;
            y._left._parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == 0) {
            fixAfterDeletion(x);
        }
    }

    private void insert(K key, V value) {
        // Ordinary Binary Search Insertion
        Entry<K, V> entry = new Entry(key, value);
        entry._parent = null;
        entry._key = key;
        entry._left = _leaf;
        entry._right = _leaf;
        entry.color = 1; // new node must be red

        Entry<K, V> y = null;
        Entry<K, V> x = this._root;

        while (x != _leaf) {
            y = x;
            if (entry._key.compareTo(x._key) < 0) {
                x = x._left;
            } else {
                x = x._right;
            }
        }

        // y is parent of x
        entry._parent = y;
        if (y == null) {
            _root = entry;
        } else if (entry._key.compareTo(y._key) < 0) {
            y._left = entry;
        } else {
            y._right = entry;
        }

        // if new node is a root node, simply return
        if (entry._parent == null) {
            entry.color = 0;
            return;
        }

        // if the grandparent is null, simply return
        if (entry._parent._parent == null) {
            return;
        }

        // Fix the tree
        fixAfterInsertion(entry);
    }

    private void leftRotate(Entry<K, V> x) {

        Entry<K, V> y = x._right;
        x._right = y._left;
        if (y._left != null) {
            y._left._parent = x;
        }
        y._parent = x._parent;
        if (x._parent == null) {
            this._root = y;
        } else if (x == x._parent._left) {
            x._parent._left = y;
        } else {
            x._parent._right = y;
        }
        y._left = x;
        x._parent = y;


    }

    private void rightRotate(Entry<K, V> x) {

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

    public Entry<K, V> getSmallest(Entry<K, V> n) {
        while (n._left != _leaf) {
            n = n._left;
        }
        return n;
    }

    public Entry<K, V> getLargest(Entry<K, V> n) {

        while (n._right != _leaf) {
            n = n._right;
        }
        return n;
    }

    private void fixAfterInsertion(Entry<K, V> k) {
        Entry<K, V> u;
        while (k._parent.color == 1) {
            if (k._parent == k._parent._parent._right) {
                u = k._parent._parent._left; // uncle
                if (u.color == 1) {
                    // case 3.1
                    u.color = 0;
                    k._parent.color = 0;
                    k._parent._parent.color = 1;
                    k = k._parent._parent;
                } else {
                    if (k == k._parent._left) {
                        // case 3.2.2
                        k = k._parent;
                        rightRotate(k);
                    }
                    // case 3.2.1
                    k._parent.color = 0;
                    k._parent._parent.color = 1;
                    leftRotate(k._parent._parent);
                }
            } else {
                u = k._parent._parent._right; // uncle

                if (u.color == 1) {
                    // mirror case 3.1
                    u.color = 0;
                    k._parent.color = 0;
                    k._parent._parent.color = 1;
                    k = k._parent._parent;
                } else {
                    if (k == k._parent._right) {
                        // mirror case 3.2.2
                        k = k._parent;
                        leftRotate(k);
                    }
                    // mirror case 3.2.1
                    k._parent.color = 0;
                    k._parent._parent.color = 1;
                    rightRotate(k._parent._parent);
                }
            }
            if (k == _root) {
                break;
            }
        }
        _root.color = 0;
    }

    private void fixAfterDeletion(Entry<K, V> x) {
        Entry<K, V> s;
        while (x != _root && x.color == 0) {
            if (x == x._parent._left) {
                s = x._parent._right;
                if (s.color == 1) {
                    // case 3.1
                    s.color = 0;
                    x._parent.color = 1;
                    leftRotate(x._parent);
                    s = x._parent._right;
                }

                if (s._left.color == 0 && s._right.color == 0) {
                    // case 3.2
                    s.color = 1;
                    x = x._parent;
                } else {
                    if (s._right.color == 0) {
                        // case 3.3
                        s._left.color = 0;
                        s.color = 1;
                        rightRotate(s);
                        s = x._parent._right;
                    }

                    // case 3.4
                    s.color = x._parent.color;
                    x._parent.color = 0;
                    s._right.color = 0;
                    leftRotate(x._parent);
                    x = _root;
                }
            } else {
                s = x._parent._left;
                if (s.color == 1) {
                    // case 3.1
                    s.color = 0;
                    x._parent.color = 1;
                    rightRotate(x._parent);
                    s = x._parent._left;
                }

                if (s._right.color == 0 && s._right.color == 0) {
                    // case 3.2
                    s.color = 1;
                    x = x._parent;
                } else {
                    if (s._left.color == 0) {
                        // case 3.3
                        s._right.color = 0;
                        s.color = 1;
                        leftRotate(s);
                        s = x._parent._left;
                    }

                    // case 3.4
                    s.color = x._parent.color;
                    x._parent.color = 0;
                    s._left.color = 0;
                    rightRotate(x._parent);
                    x = _root;
                }
            }
        }
        x.color = 0;
    }

    private void rbTransplant(Entry<K, V> u, Entry<K, V> v) {
        if (u._parent == null) {
            _root = v;
        } else if (u == u._parent._left) {
            u._parent._left = v;
        } else {
            u._parent._right = v;
        }
        v._parent = u._parent;
    }

    private void printHelper(Entry<K, V> root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != _leaf) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            String sColor = root.color == 1 ? "RED" : "BLACK";
            System.out.println(root._value + "(" + sColor + ")");
            printHelper(root._left, indent, false);
            printHelper(root._right, indent, true);
        }
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

    public HashSet getEntriesSmallerThan(K key) {
        HashSet set = new HashSet();

        inOrderHelper(_root, set, key);

        return set;


    }

    private void inOrderHelper(Entry<K, V> r, HashSet set, K key) {
        if (r == _leaf)
            return;
        inOrderHelper(r._left, set, key);
        if (r._key.compareTo(key) < 0) {
            System.out.println(r._key + " " + r._value);
            set.add(new Entry<>(r._key, r._value));
        }
        inOrderHelper(r._right, set, key);
    }

}