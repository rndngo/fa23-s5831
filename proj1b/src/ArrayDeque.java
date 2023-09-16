import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    public T[] items;
    public int size;
    public int limit;

    public ArrayDeque() {
        size = 0;
        limit = 8;
        items = (T[]) new Object[limit];
    }


    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size + 1);
        }
        items[size] = x;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size + 1);
        }
        items[size] = x;
        size += 1;
    }
    private void resize(int l) {
        T[] items2 = (T[]) new Object[l];
        for (int i = 0; i < items.length; i++){
              items2[i] = items[i];
        }
        items = items2;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++){
            returnList.add(items[i]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0 || items.length == 0 || limit == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        T x = get(0);
        size -= 1;
        return x;
    }

    @Override
    public T removeLast() {
        T x = get(-1);
        size -= 1;
        return x;
    }

    @Override
    public T get(int index) {
        if (index > size){
            return null;
        }
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
