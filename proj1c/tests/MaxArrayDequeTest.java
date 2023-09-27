import deque.MaxArrayDeque;
import org.junit.jupiter.api.*;
import deque.LinkedListDeque;
import deque.ArrayDeque;
import java.util.Comparator;
import deque.Deque;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {
    @Test
    void firsttest() {

        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<>(Integer::compareTo);
        for (int i = 0; i < 10; i ++) {
            lld1.addFirst(i);
        }
        for (int i = 10; i > 0; i --) {
            lld1.addLast(i);
        }
        lld1.addLast(900);
        for (Integer x : lld1) {
            System.out.println(x);
        }

        System.out.println(lld1.max());
    }

    @Test
    void secondtest() {
        MaxArrayDeque<String> lld1 = new MaxArrayDeque<>(String::compareTo);
        lld1.addFirst("front");
        lld1.addFirst("middle");
        lld1.addFirst("last");
        lld1.addFirst("backtomiddle");
        lld1.addFirst("backtofront");
        System.out.println(lld1.max());

    }
}
