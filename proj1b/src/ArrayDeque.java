import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int last;
    private int front;
    private int limit;

    public static final int L = 16;

    public ArrayDeque() {
        size = 0;
        last = 0;
        front = 0;
        limit = 8;
        items = (T[]) new Object[limit];
    }

    @Override
    public void addFirst(T x) {
        if (size == limit) {
            resize(limit * 2);
        }
        front = (front - 1 + limit) % limit;
        items[front] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == limit) {
            resize(limit * 2);
        }
        items[last] = x;
        last = (last + 1) % limit;
        size++;
    }

    private void resize(int newLimit) {
        T[] newArray = (T[]) new Object[newLimit];
        for (int i = 0; i < size; i++) {
            newArray[i] = items[(front + i) % limit];
        }
        items = newArray;
        front = 0;
        last = size;
        limit = newLimit;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T removedItem = items[front];
        items[front] = null;
        front = (front + 1) % limit;
        size--;

        if (limit > L && size <= limit / 4) {
            resize(limit / 2);
        }
        return removedItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        last = (last - 1 + limit) % limit;
        T removedItem = items[last];
        items[last] = null;
        size--;

        if (limit > L && size <= limit / 4) {
            resize(limit / 2);
        }
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index >= limit || index < 0) {
            return null;
        }
        return items[(front + index) % limit];
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(items[(front + i) % limit]);
        }
        return returnList;
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
