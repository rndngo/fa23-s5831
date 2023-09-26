import deque.ArrayDeque;
import deque.LinkedListDeque;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

    @Test
    void teststring() {
        ArrayDeque<String> lld1 = new ArrayDeque<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("last");
        assertThat(lld1.toString()).isEqualTo("[front, middle, last]");

        lld1 = new ArrayDeque<>();
        lld1.addLast("front");
        lld1.addFirst("middle");
        lld1.addLast("last");
        assertThat(lld1.toString()).isEqualTo("[middle, front, last]");
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.toString()).isEqualTo("[]");
    }
    @Test
    void testequals() {
        ArrayDeque<String> lld1 = new ArrayDeque<>();
        ArrayDeque<String> lld2 = new ArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1.equals(lld2)).isTrue();
        assertThat(lld1).isEqualTo(lld2);
    }
    @Test
    void testequalswithll() {
        ArrayDeque<String> lld1 = new ArrayDeque<>();
        LinkedListDeque<String> lld2 = new LinkedListDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1.equals(lld2)).isTrue();
        assertThat(lld1).isEqualTo(lld2);
    }
    @Test
    void testiteration() {
        ArrayDeque<String> lld1 = new ArrayDeque<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        for (String s : lld1) {
            System.out.println(s);
        }
    }
}
