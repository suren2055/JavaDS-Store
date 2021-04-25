package CollectionsADT;

import CollectionsADT.Abstract.Set;
import CollectionsADT.Abstract.Tree;

import java.util.Comparator;
//Based on AVL
public class TreeSet<T extends Comparable<T>> implements Set<T> {

    private Node<T> _root;
    private int _size;
    private Comparator<T> _cmp;

    public TreeSet() {
        _root = null;
        _size = 0;
        _cmp = null;
    }

    public TreeSet(Comparator<T> cmp) {
        _root = null;
        _size = 0;
        _cmp = cmp;
    }
    private class Node<T> {

        private T _value;
        private Node<T> _left = null;
        private Node<T> _right = null;
        private Node<T> _parent;
        private int _height;

        public Node(T value) {
            this._value = value;
            this._parent = null;
            this._height = 0;
        }

        public T get_value() {
            return _value;
        }

        public void set_value(T _value) {
            this._value = _value;
        }

        public Node<T> get_parent() {
            return _parent;
        }

        public void set_parent(Node<T> _parent) {
            this._parent = _parent;
        }
    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean add(T e) {
        if (contains(e))
            return false;
        _root = insert(_root, e);

        return true;
    }



    public boolean remove(T e) {
        return false;
    }

    public boolean contains(T e) {
        return containsNode(_root, e);
    }

    public boolean equals(Set set) {
        return false;
    }

    public boolean containsAll(Set set) {
        return false;
    }

    public boolean addAll(Set set) {
        return false;
    }

    public boolean removeAll(Set set) {
        return false;
    }

    public boolean retainAll(Set set) {
        return false;
    }

    public HashSet<T> subset(T p1, T p2) {
        //returns a new set which contains all products from parent set which are larger than the p1 and smaller than p2



        return null;
    }

    private Node insert(Node<T> current, T value) {
        if (current == null) {
            _size++;
            return new Node<T>(value);

        }
        if (value.compareTo(current._value) > 0) {
            current._right = insert(current._right, value);
            current._right._parent = current;
            //return current;
        }
        if (value.compareTo(current._value) < 0) {
            current._left = insert(current._left, value);
            current._left._parent = current;
        }
        if (value.compareTo(current._value) == 0)
            return current;


        if (!isBalanced(current)) {

            int c = current._left._height - current._right._height;
            if (c < 0) {

                if (value.compareTo(current._right._value) < 0) {
                    //perform right rotation of current._right
                    rightRotate(current._right);
                }
                //left less than right: perform left rotation of current
                leftRotate(current);
            } else {
                if (value.compareTo(current._right._value) > 0) {
                    //perform left rotation current._left
                    leftRotate(current._left);
                }
                //right less than or equal left: perform right rotation of current
                rightRotate(current);
            }

        }

        return current;
    }
    private boolean containsNode(Node<T> current, T value) {
        if (current == null) {
            return false;
        }
        if (current._value.compareTo(value) == 0) {
            return true;
        }
        if (current._value.compareTo(value) > 0) {
            return containsNode(current._left, value);
        } else {
            return containsNode(current._right, value);
        }
    }
    private Node removeNode(Node<T> current, T value) {
        if (current == null) return null;
        if (value.compareTo(current._value) == 0) {
            _size--;
            //found node, proceed to deletion
            //Remove Case 1 No children
            if (current._left == null && current._right == null) {
                return null;
            }
            //Remove Case 2 Two children
            if (current._left != null && current._right != null) {
                T newValue = getSmallest(current._right)._value;//newVlaue = findBiggest(current.left)
                current._value = newValue;
                current._right = removeNode(current._right, newValue);//remove(n.left, newValue);
            }
            //Remove Case 3 Only 1 child
            if (current._right != null) {
                return current._left;
            }
            if (current._left != null) {
                return current._right;
            }

        }

        if (value.compareTo(current._value) < 0)
            current._left = removeNode(current._left, value);

        if (value.compareTo(current._value) > 0)
            current._right = removeNode(current._right, value);

        return current;
    }

    private void leftRotate(Node<T> n) {
        Node<T> x = n;
        Node<T> y = n._right;
        Node<T> k = y._left;

        if (n == n._parent._left)
            n._parent._left = n._right;
        else
            n._parent._right = n._right;

        n._right._parent = n._parent;
        y._left = x;
        x._parent = y;
        x._left = k;
        if (k==null)
            k._parent = x;


    }

    private void rightRotate(Node<T> n) {

        Node<T> x = n;
        Node<T> y = n._left;
        Node<T> k = y._right;

        if (n == n._parent._left)
            n._parent._left = y;
        else
            n._parent._right = y;

        y._right = x;
        x._parent = y;
        x._right = k;
        if (k==null)
            k._parent =x;
    }

    private boolean isBalanced(Node<T> n) {
        if (n == null)
            return true;

        int lh = getHeight(n._left);
        int rh = getHeight(n._right);

        if (Math.abs(lh - rh) <= 1) {
            return isBalanced(n._left) && isBalanced(n._right);
        }
        return false;


    }

    private int getHeight(Node<T> n) {

        if (n == null) return 0;
        return 1 + Math.max(getHeight(n._left), getHeight(n._right));


    }

    private Node<T> getSmallest(Node<T> n) {

        if (n == null)
            return null;
        Node<T> t = n;
        while (t._left != null)
            t = t._left;

        return t;
    }

    private T getLargest(Node<T> n) {

        if (n != null)
            return null;
        Node<T> t = n;
        while (t._right != null)
            t = t._right;


        return t._value;
    }
}
