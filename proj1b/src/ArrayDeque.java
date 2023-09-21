import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int last;
    private int front;
//    private int truelimit;
    private int limit;

    public ArrayDeque() {
        size = 0;
        last = 1;
        front = 0;
        limit = 8;
        items = (T[]) new Object[limit];
//        truelimit = limit * 2;
    }
    @Override
    public void addFirst(T x) {

        if (size == limit) {
            resize(size * 3 / 2);
        }
        if (front < 0) {
            front = limit - 1;
        }
        items[front] = x;
        front -= 1;
        size += 1;
    }


    @Override
    public void addLast(T x) {

        if (size == limit) {
            resize(size * 3 / 2);
        }
        if (last > limit - 1) {
            last = 0;
        }
        items[last] = x;
        last += 1;
        size += 1;
    }

    private void resize(int l) {
        T[] items2 = (T[]) new Object[l];
        int index = front + 1;
        for (int i = 1; i < limit + 1; i++) {
            if (index > limit - 1) {
                index = 0;
            }
            items2[i] = items[index];
            index++;
        }
        items = items2;
        limit = l;
        front = 0;
        last = size + 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int index = front + 1;
        for (int i = 0; i < limit; i++) {
            if (index > limit - 1) {
                index = 0;
            }
            if (index < 0) {
                index = limit -1;
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
        int prev = front + 1;
        if (prev > limit -1) {
            prev = 0;
        }
        T x = get(prev);
        if (x == null) {
            return null;
        } else {
            items[prev] = null;
            front = front + 1;
            size -= 1;
            if (size() < 0) {
                size = 0;
            }
            if (size == 0) {
                front = 0;
                last = 1;
            }
            if (size == (limit / 4) && limit > 16) {
                reverseresize(size );
            }
        }
        return x;
    }
//        front += 1;
//        if (front > limit - 1) {
//            front = 0;
//        }
//        if (get(front) == null) {
//            front -= 1;
//            return null;
//        } else {
//            T x = get(front);
//            items[front] = null;
//            if (size != 0) {
//                size -= 1;
//            }
//            if (size == 0) {
//                front = 0;
//                last = 1;
//            }
//            if (size == (limit / 4) && limit > truelimit) {
//                reverseresize(size);
//            }
//            return x;
//        }

    @Override
    public T removeLast() {
        int prev = last - 1;
        if (prev < 0) {
            prev = limit -1;
        }
        T x = get(prev);
        if (x == null) {
            return null;
        } else {
            items[prev] = null;
            last = last - 1;
            size -= 1;
            if (size() < 0) {
                size = 0;
            }
            if (size == 0) {
                front = 0;
                last = 1;
            }
            if (size == (limit / 4) && limit > 16) {
                reverseresize(size);
            }
        }
        return x;
    }
//        last -= 1;
//        if (last < 0) {
//            last = limit - 1;
//        }
//        if (get(last) == null) {
//            last += 1;
//            return null;
//        } else {
//            T x = get(last);
//            items[last] = null;
//            if (size != 0) {
//                size -= 1;
//            }
//            if (size == 0) {
//                front = 0;
//                last = 1;
//            }
//            if (size == (limit / 4) && limit > truelimit) {
//                reverseresize(size);
//            }
//            return x;
//        }

    private void reverseresize(int l) {
        T[] items2 = (T[]) new Object[l];
        int index = front + 1;
        for (int i = 1; i < l; i++) {
            if (index > limit - 1) {
                index = 0;
            }
            items2[index] = items[i];
            index++;
        }
        items = items2;
        limit = l;
        front = 0;
        last = size + 1;
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
