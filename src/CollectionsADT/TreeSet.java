package CollectionsADT;

import CollectionsADT.Abstract.Set;

import java.util.Comparator;
import java.util.Iterator;

//Based on AVL tree
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

    static class Node<T extends Comparable<T>> implements  Comparable<Node<T>>{

        private T _value;
        private Node<T> _left = null;
        private Node<T> _right = null;
        private Node<T> _parent;
        private int _height;
        private int _bf;

        public Node(T value) {
            this._value = value;
            this._parent = null;
            this._height = 0;
            this._bf = 0;
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


        public int compareTo(Node<T> o) {
            return this._value.compareTo(o._value);
        }
    }


    public boolean add(T e) {
        if (contains(e))
            return false;

        insert(e);

        return true;
    }
    public boolean remove(T e) {
        removeNode(this._root, e);
        return true;

    }
    public boolean contains(T e) {
        return containsNode(_root, e);
    }
    public void prettyPrint() {
        printHelper(this._root, "", true);
    }

    public int size() {
        return _size;
    }
    public boolean isEmpty() {
        return _size==0;
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
        HashSet set = new HashSet();
        inOrderHelper(this._root, set, p1,p2);

        return set;
    }

    private void insert(T v) {
        // PART 1: Ordinary BST insert
        Node node = new Node(v);
        Node y = null;
        Node x = this._root;

        while (x != null) {
            y = x;
            if (node._value.compareTo(x._value) < 0) {
                x = x._left;
            } else {
                x = x._right;
            }
        }

        // y is parent of x
        node._parent = y;
        if (y == null) {
            _root = node;
        } else if (node._value.compareTo( y._value) < 0) {
            y._left = node;
        } else {
            y._right = node;
        }
        // PART 2: re-balance the node if necessary
        updateBalance(node);
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


    private Node<T> getSmallest(Node<T> node) {
        while (node._left != null) {
            node = node._left;
        }
        return node;
    }
    private Node<T> getLargest(Node<T> node) {
        while (node._right != null) {
            node = node._right;
        }
        return node;
    }
    private void leftRotate(Node<T> x) {
        Node y = x._right;
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

        // update the balance factor
        x._bf = x._bf - 1 - Math.max(0, y._bf);
        y._bf = y._bf - 1 + Math.min(0, x._bf);
    }
    private void rightRotate(Node<T> x) {
        Node y = x._left;
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

        // update the balance factor
        x._bf = x._bf + 1 - Math.min(0, y._bf);
        y._bf = y._bf + 1 + Math.max(0, x._bf);
    }
    private void updateBalance(Node node) {
        if (node._bf < -1 || node._bf > 1) {
            rebalance(node);
            return;
        }

        if (node._parent != null) {
            if (node == node._parent._left) {
                node._parent._bf -= 1;
            }

            if (node == node._parent._right) {
                node._parent._bf += 1;
            }

            if (node._parent._bf != 0) {
                updateBalance(node._parent);
            }
        }
    }
    private void rebalance(Node node) {
        if (node._bf > 0) {
            if (node._right._bf < 0) {
                rightRotate(node._right);
                leftRotate(node);
            } else {
                leftRotate(node);
            }
        } else if (node._bf < 0) {
            if (node._left._bf > 0) {
                leftRotate(node._left);
                rightRotate(node);
            } else {
                rightRotate(node);
            }
        }
    }

    private void printHelper(Node currPtr, String indent, boolean last) {

        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            System.out.println(currPtr._value + "(BF = " + currPtr._bf + ")");

            printHelper(currPtr._left, indent, false);
            printHelper(currPtr._right, indent, true);
        }
    }
    private Node removeNode(Node<T> node, T v) {
        // search the key
        if (node == null) return node;
        else if (v.compareTo(node._value) < 0) node._left = removeNode(node._left, v);
        else if (v.compareTo(node._value) > 0) node._right = removeNode(node._right, v);
        else {
            // the key has been found, now delete it

            // case 1: node is a leaf node
            if (node._left == null && node._right == null) {
                node = null;
            }

            // case 2: node has only one child
            else if (node._left == null) {
                Node<T> temp = node;
                node = node._right;
            }

            else if (node._right == null) {
                Node<T> temp = node;
                node = node._left;
            }

            // case 3: has both children
            else {
                Node<T> temp = getSmallest(node._right);
                node._value = temp._value;
                node._right = removeNode(node._right, temp._value);
            }

        }

        // Write the update balance logic here
        // YOUR CODE HERE

        return node;
    }

    private void inOrderHelper(Node<T> node, HashSet set, T p1, T p2) {
        if (node != null) {
            inOrderHelper(node._left, set, p1, p2);
            if (node._value.compareTo(p1)>0 && node._value.compareTo(p2)<0){
                set.add(new Node(node._value));
                System.out.print(node._value + " ");
            }
            inOrderHelper(node._right,set, p1, p2);
        }
    }

}
