package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int last;
    private int front;
    private int limit;

    public static final int L = 16;

    public ArrayDeque() {
        this.size = 0;
        this.last = 0;
        this.front = 0;
        this.limit = 8;
        this.items = (T[]) new Object[this.limit];
    }

    @Override
    public void addFirst(T x) {
        if (this.size == this.limit) {
            resize(this.limit * 2);
        }
        this.front = (this.front - 1 + this.limit) % this.limit;
        this.items[this.front] = x;
        this.size++;
    }

    @Override
    public void addLast(T x) {
        if (this.size == this.limit) {
            resize(this.limit * 2);
        }
        this.items[this.last] = x;
        this.last = (this.last + 1) % this.limit;
        this.size++;
    }

    private void resize(int newLimit) {
        T[] newArray = (T[]) new Object[newLimit];
        for (int i = 0; i < this.size; i++) {
            newArray[i] = this.items[(this.front + i) % this.limit];
        }
        this.items = newArray;
        this.front = 0;
        this.last = this.size;
        this.limit = newLimit;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T removedItem = this.items[this.front];
        this.items[this.front] = null;
        this.front = (this.front + 1) % this.limit;
        this.size--;

        if (this.limit > L && this.size <= this.limit / 4) {
            resize(this.limit / 2);
        }
        return removedItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        this.last = (this.last - 1 + this.limit) % this.limit;
        T removedItem = this.items[this.last];
        this.items[this.last] = null;
        this.size--;

        if (this.limit > L && this.size <= this.limit / 4) {
            resize(this.limit / 2);
        }
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index >= this.limit || index < 0) {
            return null;
        }
        return this.items[(this.front + index) % this.limit];
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            returnList.add(this.items[(this.front + i) % this.limit]);
        }
        return returnList;
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
    private class ArraySetIterator implements Iterator<T> {
        private int wizPos;

        public ArraySetIterator() {
            wizPos = 0;
        }
        @Override
        public boolean hasNext() {
            return wizPos < size;
        }

        @Override
        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
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
            for (Object x : otherobj) {
                if (!this.contains((T) x)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    private  boolean contains(T x) {
        for (int i = 0; i < size; i += 1) {
            if (this.get(i).equals(x)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

}
