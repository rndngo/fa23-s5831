import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size = 0;
    private int last;
    private int front;
    private int fm = 0;
    private int lm = 0;
    private int truelimit = 8;
    private int limit = 8;

    public ArrayDeque() {
        last = 1;
        front = 0;
        this.items = (T[]) new Object[limit];
    }
    @Override
    public void addFirst(T x) {

        if (size == items.length) {
            resize(size + 1);
        }
        if (front < 0 && size != 0) {
            front = limit - 1;
        }
        items[front] = x;
        front -= 1;
        size += 1;
        fm += 1;
    }


    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size + 1);
        }
        if (last > limit - 1) {
            last = 0;
        }
        items[last] = x;
        last += 1;
        size += 1;
        lm += 1;
    }

    private void resize(int l) {
        T[] items2 = (T[]) new Object[l];
        int index = front + 1;
        for (int i = 1; i < l; i++) {
            if (index > limit - 1) {
                index = 0;
            }
            items2[i] = items[index];
            index++;
        }
        items = items2;
        limit = l;
        front = 0;
        last = 0;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int index = front + 1;
        for (int i = 0; i < limit; i++) {
            if (index > limit - 1) {
                index = 0;
            }
            if (items[index] != null) {
                returnList.add(items[index]);
            }
            index++;
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
        int rf = front;
        if (fm != 0) {
            rf += 1;
        }
        if (get(rf) == null) {
            return null;
        }
        if (rf > limit - 1) {
            rf = 0;
        }
        if (rf < 0) {
            rf = limit - 1;
        }

        T x = get(rf);
        items[rf] = null;
        front += 1;
        if (size != 0) {
            size -= 1;
        }
        if (size == limit - 1 && limit != truelimit) {
            reverseresize(size);
        }
        fm -= 1;
        return x;
    }

    @Override
    public T removeLast() {
        int rl = last;
        if (lm != 0) {
            rl -= 1;
        }
        if (get(rl) == null) {
            return null;
        }
        if (rl > limit - 1) {
            rl = 0;
        }
        if (rl < 0) {
            rl = limit - 1;
        }

        T x = get(rl);
        items[rl] = null;
        last -= 1;
        if (size != 0) {
            size -= 1;
        }
        if (size == limit - 1 && limit != truelimit) {
            reverseresize(size);
        }
        lm -= 1;
        return x;
    }

    private void reverseresize(int l){
        T[] items2 = (T[]) new Object[l];
        int index = front;
        for (int i = 1; i < l + 1; i++) {
            if (index > limit - 1) {
                index = 0;
            }
            items2[index] = items[i];
            index++;
        }
        items = items2;
        limit = l;
        front = -1;
        last = 0;
    }
    @Override
    public T get(int index) {
        if (index > limit - 1) {
            index = 0;
        }
        if (index < 0) {
            index = limit - 1;
        }
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
