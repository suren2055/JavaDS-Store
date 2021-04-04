package CollectionsADT;

import CollectionsADT.Abstract.List;

public class GeneralTree<T extends Comparable> {

    public Node<T> _root = null;

    public class Node<T> {

        private LinkedList<Node<T>> _children;
        private T _value;
        private Node<T> _parent;

        public Node(T value) {
            this._value = value;
            this._parent = null;
            this._children = new LinkedList<>();//chenge to arraylist before creat it


        }
        public Node(T value, Node<T> parent) {
            this._value = value;
            this._parent = parent;
            this._children = new LinkedList<>();//chenge to arraylist before creat it
            parent.addChild(this);
        }

        public void set_parent(Node<T> _parent) {
            this._parent = _parent;
        }
        public Node<T> get_parent() {
            return _parent;
        }
        public T get_value() {
            return _value;
        }
        public void set_value(T _value) {
            this._value = _value;
        }
        public LinkedList<Node<T>> get_children() {
            return _children;
        }
        public void set_children(LinkedList<Node<T>> _children) {
            this._children = _children;
        }

        public Node<T> addChild(Node<T> child) {
            _children.addLast(child);
            child.set_parent (this);
            return child;
        }
    }

    public Node<T> get_root() {
        return _root;
    }

    public void set_root(Node<T> _root) {
        this._root = _root;
    }

    public boolean isExternal(Node<T> n) {
        return  false;
    }
    //#region Traversals
    public void printPreorder() {
        System.out.println("Using PREORDER traversal");
        preOrder(_root);

    }
    private void preOrder(Node<T> n) {
        if (n==null)
            return;
        System.out.println(n.get_value());
        //process(n.get_value());
        for (Node<T> child : n.get_children())
            preOrder(child);

    }
    public void printPostorder() {
        System.out.println("Using POSTORDER traversal");
        postOrder(_root);

    }
    private void postOrder(Node<T> n) {
        for (Node<T> child : n._children)
            postOrder(child);
        //process(n.get_value());
        System.out.println(n.get_value());
    }

    //#region Non Recursive
//    int depth(V v) {
//
//        int counter = 0;
//        while(!isRoot(v)) {
//            v = parent(v);
//            counter++;
//        }
//
//        return counter;
//    }
//#endregion

    //#region   Recursive
//    int depth(V v) {
//        if (isRoot(v))
//            return 0;
//        else
//            return 1 + depth(parent(v));
//
//    }
//#endregion

    //  region  Height -(The depth of the deepest node) (O(n^2))
//    int height() {
//        LinkedList<V> allNodes = positions();
//        int max = 0;
//        for (v:
//             allNodes) {
//            int h = depth(v);
//            if (h > max)
//                max = h;
//        }
//        return max;
//
//    }
    //#endregion

    //#region    Height improved O(n)
//    int height(V v) {
//        int h = 0;
//
//        for (w:children(v)) {
//
//            int n = height(w);
//            if (n > h)
//                h = n;
//        }
//        return h + 1;
//    }
    //#endregion

    //#endregion

    //Binary TRee types
    //.Strictly binary tree
    //.Skew tree
    //....Left skewed
    //....Right skewed
    //.Full or proper binary tree
    //.Complete binary tree
    //.Almost complete binary tree
}
