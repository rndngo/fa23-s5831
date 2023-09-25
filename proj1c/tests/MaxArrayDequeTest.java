import deque.MaxArrayDeque;
import org.junit.jupiter.api.*;
import deque.LinkedListDeque;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {
    @Test
    void firsttest() {

        Deque<Integer> lld1 = new ArrayDeque<>();
        for (int i = 0; i < 10; i ++) {
            lld1.addFirst(i);
        }
        for (int i = 10; i > 0; i --) {
            lld1.addLast(i);
        }
        for (Integer x : lld1) {
            System.out.println(x);
        }

        System.out.println(lld1.stream().max(Integer::compareTo));
    }

    @Test
    void secondtest() {
        Deque<String> lld1 = new ArrayDeque<>();
        lld1.addFirst("front");
        lld1.addFirst("middle");
        lld1.addFirst("last");
        lld1.addFirst("backtomiddle");
        lld1.addFirst("backtofront");
        System.out.println(lld1.stream().max(String::compareTo));
    }
}
