import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

//     @Test
//     @DisplayName("ArrayDeque has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }

    @Test
    void Testfirst(){
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        lld2.addFirst(1); //[1]
        lld2.addFirst(2); //[2 1]
        lld2.addFirst(3); //[3 2 1]
        assertThat(lld2.size).isEqualTo(3);

        lld2.addFirst(4); // [4 3 2 1]
        lld2.addFirst(5); // [5 4 3 2 1]
        lld2.addFirst(6); // [6 5 4 3 2 1]
        lld2.addFirst(7); // [7 6 5 4 3 2 1]
        lld2.addFirst(8); // [8 7 6 5 4 3 2 1]
        assertThat(lld2.size).isEqualTo(8);
        lld2.addFirst(9); // [9 8 7 6 5 4 3 2 1]
        assertThat(lld2.size).isEqualTo(9);

    }
    @Test
    void Testlast(){
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        lld2.addLast(1); //[1]
        lld2.addLast(2); //[2 1]
        lld2.addLast(3); //[3 2 1]
        assertThat(lld2.size).isEqualTo(3);

        lld2.addLast(4); // [4 3 2 1]
        lld2.addLast(5); // [5 4 3 2 1]
        lld2.addLast(6); // [6 5 4 3 2 1]
        lld2.addLast(7); // [7 6 5 4 3 2 1]
        lld2.addLast(8); // [8 7 6 5 4 3 2 1]
        assertThat(lld2.size).isEqualTo(8);
        lld2.addLast(9); // [9 8 7 6 5 4 3 2 1]
        assertThat(lld2.size).isEqualTo(9);

    }
    @Test
    void Testget(){
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        assertThat(lld2.get(2)).isEqualTo(null);
        assertThat(lld2.get(0)).isEqualTo(null);
        lld2.addLast(1); //[1]
        lld2.addLast(2); //[2 1]
        lld2.addLast(3); //[3 2 1]
        assertThat(lld2.get(2)).isEqualTo(3);

        lld2.addLast(4); // [4 3 2 1]
        lld2.addLast(5); // [5 4 3 2 1]
        lld2.addLast(6); // [6 5 4 3 2 1]
        lld2.addLast(7); // [7 6 5 4 3 2 1]
        lld2.addLast(8); // [8 7 6 5 4 3 2 1]
        assertThat(lld2.get(7)).isEqualTo(8);
        lld2.addLast(9); // [9 8 7 6 5 4 3 2 1]
        assertThat(lld2.get(8)).isEqualTo(9);

    }

    @Test
    void Testempty(){
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        assertThat(lld2.isEmpty()).isTrue();
        lld2.addLast(1); //[1]
        lld2.addFirst(2); //[2 1]
        lld2.addLast(3); //[3 2 1]
        assertThat(lld2.isEmpty()).isFalse();
        lld2.addLast(4); // [4 3 2 1]
        lld2.addFirst(5); // [5 4 3 2 1]
        lld2.addLast(6); // [6 5 4 3 2 1]
        lld2.addLast(7); // [7 6 5 4 3 2 1]
        lld2.addFirst(8); // [8 7 6 5 4 3 2 1]
        assertThat(lld2.isEmpty()).isFalse();
        lld2.addFirst(9); // [9 8 7 6 5 4 3 2 1]
        assertThat(lld2.isEmpty()).isFalse();
    }

    @Test
    void Testsize(){
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        assertThat(lld2.size()).isEqualTo(0);
        lld2.addLast(1); //[1]
        lld2.addLast(2); //[2 1]
        lld2.addLast(3); //[3 2 1]
        assertThat(lld2.size()).isEqualTo(3);

        lld2.addLast(4); // [4 3 2 1]
        lld2.addLast(5); // [5 4 3 2 1]
        lld2.addLast(6); // [6 5 4 3 2 1]
        lld2.addLast(7); // [7 6 5 4 3 2 1]
        lld2.addLast(8); // [8 7 6 5 4 3 2 1]
        assertThat(lld2.size()).isEqualTo(8);
        lld2.addLast(9); // [9 8 7 6 5 4 3 2 1]
        lld2.addFirst(0);
        assertThat(lld2.size()).isEqualTo(10);
    }
    @Test
    void Testtolist(){
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        assertThat(lld2.toList()).containsExactly();
        lld2.addLast(1); //[1]
        lld2.addLast(2); //[2 1]
        lld2.addLast(3); //[3 2 1]
        assertThat(lld2.toList()).containsExactly(1, 2, 3).inOrder();

        lld2.addLast(4); // [4 3 2 1]
        lld2.addFirst(5); // [5 4 3 2 1]
        lld2.addFirst(6); // [6 5 4 3 2 1]
        lld2.addFirst(7); // [7 6 5 4 3 2 1]
        lld2.addLast(8); // [8 7 6 5 4 3 2 1]
        assertThat(lld2.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8).inOrder();
        lld2.addLast(9); // [9 8 7 6 5 4 3 2 1]
        lld2.addFirst(0);
        assertThat(lld2.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 0).inOrder();
    }

    @Test
    void Testremoves(){
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        assertThat(lld2.removeFirst()).isEqualTo(null);
        assertThat(lld2.removeLast()).isEqualTo(null);
        lld2.addLast(1); //[1]
        lld2.addLast(2); //[2 1]
        lld2.addLast(3); //[3 2 1]
        assertThat(lld2.toList()).containsExactly(1, 2, 3).inOrder();

        lld2.addLast(4); // [4 3 2 1]
        lld2.addFirst(5); // [5 4 3 2 1]
        lld2.addFirst(6); // [6 5 4 3 2 1]
        lld2.addFirst(7); // [7 6 5 4 3 2 1]
        lld2.addLast(8); // [8 7 6 5 4 3 2 1]
        assertThat(lld2.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8).inOrder();
        lld2.addLast(9); // [9 8 7 6 5 4 3 2 1]
        lld2.addFirst(0);
        assertThat(lld2.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 0).inOrder();
    }

}

