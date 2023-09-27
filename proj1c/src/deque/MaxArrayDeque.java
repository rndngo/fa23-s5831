package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> my_comparator;
    public MaxArrayDeque (Comparator<T> c) {
        my_comparator = c;
    }
    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        T first = null;
        T second = null;
        T max = null;
        for (int i = 0; i < this.size(); i++) {
            first = this.get(i);
            int x = my_comparator.compare(first, second);
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
        T first = null;
        T second = null;
        T max = null;
        for (int i = 0; i < this.size(); i++) {
            first = this.get(i);
            int x = c.compare(first, second);
            if (x < 0) {
                max = second;
            }
            second = first;
        }
        return max;
    }
}
