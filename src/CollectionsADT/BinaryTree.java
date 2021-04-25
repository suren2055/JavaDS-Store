package CollectionsADT;

import CollectionsADT.Abstract.Queue;
import CollectionsADT.Abstract.Tree;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTree<T extends Comparable<T>> implements Tree<T>, Iterable<T> {

    private Node<T> root;
    private int _size;

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
            T value = _currentNode._value;
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

    private class InOrderIteratorStack implements Iterator<T> {

        private Stack<Node> s;

        public InOrderIteratorStack() {

            s = new Stack<>();
            Node c = root;
            while (c != null) {
                s.push(c);
                c = c._left;
            }

        }

        public boolean hasNext() {
            return !s.isEmpty();
        }


        public T next() {

            if (!hasNext())
                return null;
            Node n = s.pop();
            T toReturn = (T) s.peek()._value;
            if (n._right != null) {
                s.push(n._right);
                n = n._right._left;
                while (n != null) {
                    s.push(n);
                    n = n._left;

                }
            }
            return toReturn;
        }
    }

    private class PostOrderIteratorStack implements Iterator<T> {

        LinkedList<T> _values;

        public PostOrderIteratorStack() {
            _values = new LinkedList<>();
            propogateValues(root);
        }

        private void propogateValues(Node<T> n) {

            if (n == null)
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
            T value = _values.removeFirst();
            return value;
        }
    }

    private class PreOrderIterator implements Iterator<T> {

        Node cur = root;


        public boolean hasNext() {
            return cur != null;
        }


        public T next() {
            if (!hasNext())
                return null;
            T toReturn = (T) cur._value;
            cur = getNextInPreOrder(cur);

            return toReturn;

        }
    }


    public Iterator iterator() {
        return new InOrderIterator();
    }

    public Iterator inOrderIteratorStack() {
        return new InOrderIteratorStack();
    }

    public Iterator postOrderIteratorStack() {
        return new PostOrderIteratorStack();
    }

    public Iterator preOrderIterator() {
        return new PreOrderIterator();
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


    public boolean insertIterative(T v) {
        if (contains(v))
            return false;
        Node temp = root;
        if (this.isEmpty()) {
            root = new Node<T>(v);
            _size++;
        } else {
            while (temp != null) {
                if (v.compareTo((T) temp._value) == 0) {
                    return false;
                }
                if (v.compareTo((T) temp._value) > 0) {
                    if (temp._right == null) {
                        temp._right = new Node(v);
                        temp._right._parent = temp;
                        _size++;
                        return true;
                    } else
                        temp = temp._right;
                }//right
                if (v.compareTo((T) temp._value) < 0) {
                    if (temp._left == null) {
                        temp._left = new Node(v);
                        temp._left._parent = temp;
                        _size++;
                        return true;
                    } else
                        temp = temp._left;
                }//left
            }
        }
        return true;
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

        return removeIterative(root, v);
    }

    private boolean removeIterative(Node<T> n, T v) {
        Node<T> temp = n;
        while (temp != null) {
            if (v.compareTo(temp._value) == 0) {
                if (temp._left == null && temp._right == null) {
                    Node<T> parent = temp._parent;
                    if (temp == parent._left)
                        parent._left = null;
                    else
                        parent._right = null;
                    return true;

                }
                if (temp._left != null && temp._right != null) {
                    T newValue = getLargest(temp._left);//getSmallest(temp._right);
                    temp._value = newValue;
                    removeIterative(temp._left, newValue);//removeIterative(n._right,newValue);
                    return true;
                }
                Node<T> child = temp._right;
                if (child == null)
                    child = temp._left;
                Node<T> parent = temp._parent;
                if (temp == parent._left)
                    parent._left = child;
                else
                    parent._right = child;
                return true;
            }
            if (v.compareTo(temp._value) > 0)
                temp = temp._right;
            if (v.compareTo(temp._value) < 0)
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

    public int height() {
        return getHeight(root);
    }

    public boolean isBalanced() {

        return isBalanced(root);
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

    private Node getNextInPreOrder(Node n) {
        if (n._left != null)
            return n._left;

        if (n._right != null)
            return n._right;

        Node p = n._parent;
        while ((p != null && p._right != null && p._right.equals(n)) ||
                (p != null && p._left != null && p._left.equals(n) && p._right == null)) {
            n = n._parent;
            p = n._parent;

        }
        if (p == null) {
            return null;
        }
        return p._right;


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