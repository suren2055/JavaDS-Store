package CollectionsADT;

import CollectionsADT.Abstract.Queue;
import CollectionsADT.Abstract.Tree;

import java.util.Iterator;

public class BinaryTree<T extends Comparable<T>> implements Tree<T>, Iterable<T> {

    private Node<T> root;
    private int _size;

    private class Node<T> {

        private T _value;
        private Node<T> _left = null;
        private Node<T> _right = null;
        private Node<T> _parent;

        public Node(T value) {
            this._value = value;
            this._parent = null;
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

    private class InOrderIterator implements Iterator<T> {

        Node<T> _currentNode;

        public InOrderIterator() {
            _currentNode = getSmallest(root);
        }

        public boolean hasNext() {
            return _currentNode != null;
        }


        public T next() {
            if (!hasNext())
                return null;
            T value =  _currentNode._value;
            if (_currentNode._right != null)
                _currentNode = getSmallest(_currentNode._right);
            else {
                Node<T> t = _currentNode;
                Node<T> p = _currentNode._parent;
                while (p != null && t == p._right) {
                    t = p;
                    p = p._parent;
                }
                _currentNode = p;
            }
           return value;
        }
    }
    private class PostOrderIteratorStack implements Iterator<T> {

        LinkedList<T> _values;

        public PostOrderIteratorStack() {
            _values = new LinkedList<>();
            propogateValues(root);
        }
        private void propogateValues(Node<T> n){

            if (n==null)
                return;
            propogateValues(n._left);
            propogateValues(n._right);
            _values.addLast(n._value);
        }

        public boolean hasNext() {
            return !_values.isEmpty();
        }

        public T next() {
            if (!hasNext())
                return null;
            T value =  _values.removeFirst();
            return value;
        }
    }

    public Iterator iterator() {
        return new InOrderIterator();
    }
    public Iterator postOrderIteratorStack() {
        return new PostOrderIteratorStack();
    }

    public BinaryTree() {
        root = null;
        _size = 0;
    }

    public boolean insert(T v) {

        if (contains(v))
            return false;
        root = insert(root, v);

        return true;
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

        return current;
    }

    public boolean remove(T v) {
        root = removeNode(root, v);
        return true;

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

    public boolean removeIterative(T v) {
        return removeIterative(root,v);
    }
    private boolean removeIterative(Node<T> n, T v) {
        Node<T> temp = n;
        while(temp!=null) {
            if (v.compareTo(temp._value)==0){
                if (temp._left==null &&temp._right==null){
                    Node<T> parent = temp._parent;
                    if (temp==parent._left)
                        parent._left=null;
                    else
                        parent._right=null;
                    return true;

                }
                if (temp._left!=null && temp._right!=null) {
                    T newValue = getLargest(temp._left);//getSmallest(temp._right);
                    temp._value = newValue;
                    removeIterative(temp._left,newValue);//removeIterative(n._right,newValue);
                    return true;
                }
                Node<T> child = temp._right;
                if (child==null)
                    child=temp._left;
                Node<T> parent = temp._parent;
                if (temp==parent._left)
                    parent._left=child;
                else
                    parent._right = child;
                return true;
            }
            if (v.compareTo(temp._value)>0)
                temp = temp._right;
            if (v.compareTo(temp._value)<0)
                temp = temp._left;
        }
        return false;
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

    public boolean contains(T v) {
        return containsNode(root, v);
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

    public int size() {
        return _size;
    }

    public boolean isEmpty() {
        return _size == 0;
    }


    //#region Traversals BinaryTrees

    public void printInorder() {
        System.out.println("Printing binary tree using inorder");
        inOrder(root);
    }

    private void inOrder(Node<T> r) {

        if (r == null)
            return;
        inOrder(r._left);
        //process(n._value);
        System.out.println(r._value);
        inOrder(r._right);
    }

    public void printLevelorder() {
        System.out.println("Printing binary tree using level order");
        levelOrder(root);
    }

    private void levelOrder(Node<T> r) {
        //create a queue for the nodes
        LinkedListQueue<Node<T>> nodesQueue = new LinkedListQueue<Node<T>>();
        if (root != null)
            nodesQueue.enqueue(r);
        while (nodesQueue.size() > 0) {
            Node<T> n = nodesQueue.dequeue();
            //reached a leaf, nothing to do
            if (n == null) continue;
            //process current node and enqueue its children
            //process (n._value);
            System.out.println(n._value);
            if (n._left != null)
                nodesQueue.enqueue(n._left);
            if (n._right != null)
                nodesQueue.enqueue(n._right);
        }
    }

    //#endregion

    //Red-black tree

    //AVL Tree

}