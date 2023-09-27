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

        T max = this.get(0);
        for (int i = 0; i < this.size(); i++) {
            T next = this.get(i);
            int x = mycomparator.compare(next, max);
            if (x >= 0) {
                max = next;
            }
        }
        return max;
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T max = this.get(0);
        for (int i = 0; i < this.size(); i++) {
            T next = this.get(i);
            int x = c.compare(next, max);
            if (x >= 0) {
                max = next;
            }
        }
        return max;
    }
}
