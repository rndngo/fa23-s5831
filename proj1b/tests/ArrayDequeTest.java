import edu.princeton.cs.algs4.In;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

////     @Test
////     @DisplayName("ArrayDeque has no fields besides backing array and primitives")
////     void noNonTrivialFields() {
////         List<Field> badFields = Reflection.getFields(ArrayDeque.class)
////                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
////                 .toList();
////
////         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
////     }
    @Test
    void testaddfirst(){
        ArrayDeque<String> lld2 = new ArrayDeque<>();
        lld2.addFirst("back"); //("back")
        lld2.addFirst("middle"); //("middle" "back")
        assertThat(lld2.toList()).containsExactly("middle","back").inOrder();
        lld2.addFirst("front");
        assertThat(lld2.toList()).containsExactly("front", "middle", "back").inOrder();
        lld2.addFirst("go");
        lld2.addFirst("go");
        lld2.addFirst("go");
        lld2.addFirst("go");
        lld2.addFirst("go");
        assertThat(lld2.toList()).containsExactly("go","go","go","go","go","front","middle","back").inOrder();
        lld2.addFirst("resize");
        assertThat(lld2.toList()).containsExactly("resize","go","go","go","go","go","front","middle","back").inOrder();
    }
     @Test
    void testaddlast(){
        ArrayDeque<String> lld2 = new ArrayDeque<>();
        lld2.addLast("front"); //("back")
        lld2.addLast("middle"); //("middle" "back")
        assertThat(lld2.toList()).containsExactly("front","middle").inOrder();
        lld2.addLast("back");
        assertThat(lld2.toList()).containsExactly("front", "middle", "back").inOrder();
        lld2.addLast("go");
        lld2.addLast("go");
        lld2.addLast("go");
        lld2.addLast("go");
        lld2.addLast("go");
        assertThat(lld2.toList()).containsExactly("front","middle","back","go","go","go","go","go").inOrder();
        lld2.addLast("resize");
        assertThat(lld2.toList()).containsExactly("front","middle","back","go","go","go","go","go","resize").inOrder();
    }
    @Test
    void testaddlastandfirst(){
        ArrayDeque<String> lld2 = new ArrayDeque<>();
        lld2.addLast("front"); //("back")
        lld2.addLast("middle"); //("middle" "back")
        assertThat(lld2.toList()).containsExactly("front","middle").inOrder();
        lld2.addLast("back");
        assertThat(lld2.toList()).containsExactly("front", "middle", "back").inOrder();
        lld2.addFirst("go");
        lld2.addLast("go");
        lld2.addFirst("go");
        lld2.addFirst("go");
        lld2.addLast("go");
        assertThat(lld2.toList()).containsExactly("go","go","go","front","middle","back","go","go").inOrder();
        lld2.addFirst("resize");
        assertThat(lld2.toList()).containsExactly("resize","go","go","go","front","middle","back","go","go").inOrder();
        lld2.addLast("resize2");
        assertThat(lld2.toList()).containsExactly("resize","go","go","go","front","middle","back","go","go","resize2").inOrder();
     }
    @Test
    void testing(){
        ArrayDeque<Integer> ArrayDeque = new ArrayDeque<>();
        ArrayDeque.addLast(0);
        ArrayDeque.addFirst(1);
        ArrayDeque.get(0);
        ArrayDeque.addLast(3);
        ArrayDeque.addLast(4);
        ArrayDeque.removeLast();
        ArrayDeque.addFirst(6);
        ArrayDeque.removeFirst();
        ArrayDeque.addLast(8);
        ArrayDeque.removeFirst();
        assertThat(ArrayDeque.toList()).containsExactly(0,3,8).inOrder();
    }

    @Test
    void Testget(){
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        assertThat(lld2.get(2)).isEqualTo(null);
        assertThat(lld2.get(0)).isEqualTo(null);
        lld2.addLast(1); //[1]
        lld2.removeFirst();
        assertThat(lld2.toList()).containsExactly(1).inOrder();
        assertThat(lld2.get(2)).isEqualTo(null);
        assertThat(lld2.get(1)).isEqualTo(1);
        lld2.addFirst(0);
        assertThat(lld2.get(0)).isEqualTo(0);
        lld2.addFirst(9);
        assertThat(lld2.get(-999)).isEqualTo(9);
    }

    @Test
    void Testsizeandempty(){
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        assertThat(lld2.isEmpty()).isTrue();
        assertThat(lld2.size()).isEqualTo(0);
        lld2.removeFirst();
        assertThat(lld2.size()).isEqualTo(0);
        lld2.addLast(100);
        assertThat(lld2.isEmpty()).isFalse();
        lld2.removeLast();
        assertThat(lld2.size()).isEqualTo(0);
    }
//    @Test
//    void Testtolist(){
//        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
//        assertThat(lld2.toList()).containsExactly();
//        lld2.addLast(1); //[1]
//        lld2.addLast(2); //[2 1]
//        lld2.addLast(3); //[3 2 1]
//        assertThat(lld2.toList()).containsExactly(1, 2, 3).inOrder();
//
//        lld2.addLast(4); // [4 3 2 1]
//        lld2.addFirst(5); // [5 4 3 2 1]
//        lld2.addFirst(6); // [6 5 4 3 2 1]
//        lld2.addFirst(7); // [7 6 5 4 3 2 1]
//        lld2.addLast(8); // [8 7 6 5 4 3 2 1]
//        assertThat(lld2.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8).inOrder();
//        lld2.addLast(9); // [9 8 7 6 5 4 3 2 1]
//        lld2.addFirst(0);
//        assertThat(lld2.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 0).inOrder();
//    }
//
    @Test
    void testremoves() {
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        assertThat(lld2.removeFirst()).isEqualTo(null);
        assertThat(lld2.removeLast()).isEqualTo(null);
        lld2.addLast(666);
        lld2.removeLast();
        assertThat(lld2.isEmpty()).isTrue();
        lld2.addFirst(666);
        lld2.removeFirst();
        assertThat(lld2.isEmpty()).isTrue();

        lld2.addLast(666);
        lld2.removeFirst();
        assertThat(lld2.get(1)).isEqualTo(666);
        lld2.addFirst(666);
        lld2.removeLast();
        assertThat(lld2.get(0)).isEqualTo(666);
        lld2 = new ArrayDeque<>();

        lld2.addLast(1);
        lld2.addLast(2);
        assertThat(lld2.toList()).containsExactly(1,2).inOrder();
        lld2.addLast(3);
        assertThat(lld2.toList()).containsExactly(1,2,3).inOrder();
        lld2.removeFirst();
        assertThat(lld2.toList()).containsExactly(1,2,3).inOrder();
        lld2.removeLast();
        assertThat(lld2.toList()).containsExactly(1,2).inOrder();
        lld2.addFirst(4);
        assertThat(lld2.toList()).containsExactly(4,1,2).inOrder();
        lld2.removeFirst();
        assertThat(lld2.toList()).containsExactly(1,2).inOrder();
    }

    @Test
    void testresize(){
        ArrayDeque<String> lld2 = new ArrayDeque<>();
        lld2.addLast("front"); //("back")
        lld2.addLast("middle"); //("middle" "back")
        lld2.addLast("back");
        lld2.addFirst("go");
        lld2.addLast("go");
        lld2.addFirst("go");
        lld2.addFirst("go");
        lld2.addLast("go");
        lld2.addFirst("resize");
        lld2.addLast("resize2");
        assertThat(lld2.toList()).containsExactly("resize","go","go","go","front","middle","back","go","go","resize2").inOrder();
        lld2.removeLast();
        assertThat(lld2.toList()).containsExactly("resize","go","go","go","front","middle","back","go","go").inOrder();
        lld2.removeFirst();
    }

    @Test
    void testingremovelaststuff() {
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        lld2.removeLast();
        lld2.addLast(0);
        lld2.removeLast();
        assertThat(lld2.isEmpty()).isTrue();
        lld2.addLast(1);
        lld2.addFirst(0);
        lld2.removeLast();
        assertThat(lld2.toList()).containsExactly(0);

    }

}

