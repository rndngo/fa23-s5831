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
        T prev = this.get(0);
        T max = prev;
        for (int i = 0; i < this.size(); i++) {
            T next = this.get(i);
            int x = mycomparator.compare(next, prev);
            int y = mycomparator.compare(next, max);
            if (x < 0 && y >= 0) {
                max = prev;
            }
            prev = next;
        }
        return max;
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T prev = this.get(0);
        T max = prev;
        for (int i = 0; i < this.size(); i++) {
            T next = this.get(i);
            int x = c.compare(next, prev);
            int y = c.compare(next, max);
            if (x < 0 && y >= 0) {
                max = prev;
            }
            prev = next;
        }
        return max;
    }
}
