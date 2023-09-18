import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size = 0;
    private int last = 0;
    private int front = 0;
    private final int limit = 8;

    public ArrayDeque() {
        this.size = 0;

        this.items = (T[]) new Object[limit];
    }


    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size + 1);
        }
        if (size != 0){
            MovingFoward();
        }
        items[front] = x;
        size += 1;
    }

    private void MovingFoward() {
        front -= 1;
        if (front < 0) {
            front += limit;
        }
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size + 1);
        }
        items[size] = x;
        last += 1;
        size += 1;
    }
    private void resize(int l) {
        T[] items2 = (T[]) new Object[l];
        for (int i = front; i < items.length; i++) {
            items2[i - front] = items[i];
        }
        for (int i = items.length - front; i < items.length; i++) {
            items2[i] = items[i - items.length + front];
        }
        items = items2;
        front = 0;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(items[i]);
        }
        return returnList;
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
        T x = get(front);
        items[size - 1] = null;
        size -= 1;
        return x;
    }

    @Override
    public T removeLast() {

        T x = get(last);
        items[last] = null;
        last -= 1;
        size -= 1;
        return x;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= items.length) {
            return null;
        }
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
