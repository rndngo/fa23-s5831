package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> mycomparator;
    public MaxArrayDeque(Comparator<T> c) {
        mycomparator = c;
    }
    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        T second = null;
        T max = null;
        for (int i = 0; i < this.size(); i++) {
            T first = this.get(i);
            int x = mycomparator.compare(first, second);
            if (x < 0) {
                max = second;
            }
            second = first;
        }
        return max;
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T second = null;
        T max = null;
        for (int i = 0; i < this.size(); i++) {
            T first = this.get(i);
            int x = c.compare(first, second);
            if (x < 0) {
                max = second;
            }
            second = first;
        }
        return max;
    }
}
