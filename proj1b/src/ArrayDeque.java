import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size = 0;
    private int last = 0;
    private int start = 0;

    public ArrayDeque() {
        this.size = 0;
        int limit = 8;
        this.items = (T[]) new Object[limit];
    }


    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size + 1);
        }
        items[start] = x;
        setMoving(-1);
        size += 1;
    }

    private void setMoving(int moving){
        start += moving;
        start %= items.length;
        if (start < 0){
            start += items.length;
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
        for (int i = start; i < items.length ; i++) {
            items2[i - start] = items[i];
        }
        for (int i = items.length - start; i < items.length; i++){
            items2[i] = items[i - items.length + start];
        }
        items = items2;
        start = 0;
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
        return size == 0 || items.length == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        T x = get(start);
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
        return items[(start - index) % items.length];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
