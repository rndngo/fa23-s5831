package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public void put(K key, V value) {
        if (containsKey(key)) {
            replacekey(key,value);
        }
        else {
            size++;
            buckets[Math.abs(key.hashCode() % cap)].add(new Node(key, value));
        }
        if ( (double) size / cap >= factor) {
            extend(cap * 2);
        }

    }
    private void replacekey(K key, V value) {
        Collection<Node> searching = buckets[Math.abs(key.hashCode() % cap)];
        for (Node node : searching) {
            if (node.key == key) {
                node.value = value;
            }
        }
    }

    private void extend(int resize) {

        cap = resize;
        size = 0;
        Collection<Node>[] buckets2 = buckets;
        buckets = new Collection[resize];
        for (int i = 0; i < resize; i++) {
            buckets[i] = createBucket();
        }
        for (Collection<Node> bucket : buckets2) {
            for (Node node : bucket) {
                put(node.key, node.value);
            }
        }

    }

    @Override
    public V get(K key) {
        Collection<Node> searching = buckets[Math.abs(key.hashCode() % cap)];
        for (Node node : searching) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        Collection<Node> searching = buckets[Math.abs(key.hashCode() % cap)];
        for (Node node : searching) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < cap; i++) {
            buckets[i].clear();
        }
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
        throw new UnsupportedOperationException();
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!

    private int cap;
    private final double factor;
    private int size;

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        factor = loadFactor;
        cap = initialCapacity;
        size = 0;
        buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

}
