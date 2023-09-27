package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    private class Node {
        private Node prev;
        private Node next;
        private final T item;

        public Node(T item, Node prev, Node next) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }

    private final Node sentinel;
    private int size;

    public LinkedListDeque() {
        this.sentinel = new Node(null, null, null);
        this.sentinel.next = sentinel;
        this.sentinel.prev = sentinel;
        this.size = 0;
    }

    @Override
    public void addFirst(T x) {
        sentinel.next = new Node(x, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        this.size += 1;
    }

    @Override
    public void addLast(T x) {
        sentinel.prev = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        this.size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node S = sentinel.next;
        for (int i = 0; i < this.size; i++) {
            returnList.add(S.item);
            S = S.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (this.size() == 0) {
            return true;
        }
        return this.sentinel.prev == null && this.sentinel.next == null;

    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        Node S1 = this.sentinel;
        Node S2 = S1.next;
        if (S1.next.item != null) {
            S1.next = S1.next.next;
            S1.next.prev = S1;
            this.size -= 1;
            return S2.item;
        }
        return null;
    }


    @Override
    public T removeLast() {
        Node S1 = this.sentinel;
        Node S2 = S1.prev;
        if (S1.prev.item != null) {
            S1.prev = S1.prev.prev;
            S1.prev.next = S1;
            this.size -= 1;
            return S2.item;
        }
        return null;
    }

    @Override
    public T get(int index) {
        if (index >= this.size || index < 0) {
            return null;
        }
        Node S = this.sentinel.next;
        for (int i = 0; i < index; i++) {
            S = S.next;
        }
        return S.item;
    }

    @Override
    public T getRecursive(int index) {
        return getRecursivehelper(index, this.sentinel.next);
    }

    private T getRecursivehelper(int i, Node S) {
        if (i == 0) {
            return S.item;
        } else if (S.next == sentinel) {
            return null;
        } else {
            return getRecursivehelper(i - 1, S.next);
        }
    }
    private class LLSetIterator implements Iterator<T> {
        private Node wizPos;
        public LLSetIterator() {
            wizPos = sentinel;
        }
        @Override
        public boolean hasNext() {
            return wizPos.next.item != sentinel.item;
        }

        @Override
        public T next() {
            T returnItem = wizPos.next.item;
            wizPos = wizPos.next;
            return returnItem;
        }
    }
    @Override
    public String toString() {
        return this.toList().toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Deque otherobj) {
            if (this.size != otherobj.size()) {
                return false;
            }
            for (T x : this) {
                if (!otherobj.contains(x)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean contains(T x) {
        for (int i = 0; i < size; i += 1) {
            if (this.get(i).equals(x)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Iterator<T> iterator() {
        return new LLSetIterator();
    }
}
