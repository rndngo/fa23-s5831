import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V extends Comparable<V>> implements Map61B<K,V> {

    private class BSTNode {
        private K key;
        private V value;
        private BSTNode leftChild;
        private BSTNode rightChild;
        public BSTNode(K key, V value){
            this.key = key;
            this.value = value;
            BSTNode leftChild = null;
            BSTNode rightChild = null;
        }
        public int compareNode(BSTNode other) {
            return this.key.compareTo(other.key);
        }
    }
    private int Size;
    private BSTNode root;
    public BSTMap () {
        root = null;
        Size = 0;
    }


    @Override
    public void put(K key, V value) {
        BSTNode newNode = new BSTNode(key, value);
        if (root == null) {
            Size++;
            // we have nothing in the map
            root = newNode;
            return;
        }
        if (CheckforKey(root, newNode))
            ReplaceValue(root,newNode);
        else {
            Size++;
            Insert(root,newNode);
        }
    }
    private void ReplaceValue(BSTNode r,BSTNode node) {
        int compared = r.compareNode((node));
        if (compared == 0) {
            r.value = node.value;
        } else if (compared < 0) {
            ReplaceValue(r.leftChild,node);
        } else {
            ReplaceValue(r.rightChild,node);
        }
    }
    private BSTNode Insert(BSTNode r, BSTNode newnode) {
        if (r == null) {
            r = newnode;
        } else {
            int compared = r.compareNode(newnode);
            if (compared < 0) {
                r.leftChild = Insert(r.leftChild, newnode);
            } else {
                r.rightChild = Insert(r.rightChild, newnode);
            }
        }
        return r;
    }
    private V ReturnValue(BSTNode root,BSTNode newnode) {
        if (root == null) {
            return null;
        }
        int compared = root.compareNode((newnode));
        if (compared == 0) {
            return root.value;
        } else if (compared < 0) {
            return ReturnValue(root.leftChild,newnode);
        } else {
            return ReturnValue(root.rightChild,newnode);
        }
    }

    @Override
    public V get(K key) {
        BSTNode newnode = new BSTNode(key,null);
        return ReturnValue(root,newnode);
    }

    @Override
    public boolean containsKey(K key) {
        BSTNode newnode = new BSTNode(key,null);
        return CheckforKey(root,newnode);
    }
    private boolean CheckforKey(BSTNode root,BSTNode newnode) {
        if (root == null) {
            return false;
        }
        int compared = root.compareNode((newnode));
        if (compared == 0) {
            return true;
        } else if (compared < 0) {
            return CheckforKey(root.leftChild,newnode);
        } else {
            return CheckforKey(root.rightChild,newnode);
        }
    }

    @Override
    public int size() {
        return Size;
    }

    @Override
    public void clear() {
        Size = 0;
        root = null;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}