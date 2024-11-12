import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    void addFirstTest() {
        ArrayDeque61B deque = new ArrayDeque61B();
        deque.addFirst(0);
        deque.addFirst(1);
        deque.addFirst(2);
        assertThat(deque.toList()).containsExactly(2, 1, 0).inOrder();
    }

    @Test
    void addLastTest() {
        ArrayDeque61B deque = new ArrayDeque61B();
        deque.addLast(0);
        deque.addLast(-1);
        deque.addLast(-2);
        assertThat(deque.toList()).containsExactly(0, -1, -2).inOrder();
    }

    @Test
    void addFirstAndLastTest(){
        ArrayDeque61B deque = new ArrayDeque61B();
        deque.addFirst(0);
        deque.addLast(-1);
        deque.addFirst(1);
        deque.addLast(-2);
        deque.addFirst(2);     // [2, 1, 0, -1, -2]
        assertThat(deque.toList()).containsExactly(2, 1, 0, -1, -2).inOrder();
    }

    @Test
    void removeFirstTest() {
        ArrayDeque61B deque = new ArrayDeque61B();
        deque.addFirst(0);
        deque.addLast(-1);
        deque.addFirst(1);     // [1, 0, -1]
        assertThat(deque.removeFirst()).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(0, -1).inOrder();
        assertThat(deque.removeFirst()).isEqualTo(0);
        assertThat(deque.toList()).containsExactly(-1).inOrder();
        assertThat(deque.removeFirst()).isEqualTo(-1);
        assertThat(deque.toList()).containsExactly();
    }

    @Test
    void removeLastTest() {
        ArrayDeque61B deque = new ArrayDeque61B();
        deque.addFirst(0);
        deque.addLast(-1);
        deque.addFirst(1);      // [1, 0, -1]
        deque.removeLast();
//        assertThat(deque.removeLast()).isEqualTo(-1);
//        assertThat(deque.toList()).containsExactly(1, 0).inOrder();
        assertThat(deque.removeLast()).isEqualTo(0);
        assertThat(deque.toList()).containsExactly(1).inOrder();
        assertThat(deque.removeLast()).isEqualTo(1);
        assertThat(deque.toList()).containsExactly();
    }

    @Test
    void toListTest() {
        ArrayDeque61B deque = new ArrayDeque61B();
        deque.addFirst(0);
        deque.addLast(-1);
        deque.addFirst(1);
        deque.addLast(-2);
        deque.addFirst(2);     // [2, 1, 0, -1, -2]
        assertThat(deque.toList()).containsExactly(2, 1, 0, -1, -2).inOrder();
    }

    @Test
    void getTest() {
        ArrayDeque61B deque = new ArrayDeque61B();
        deque.addFirst(0);
        deque.removeFirst();
        deque.addLast(2);
        deque.removeFirst();
        deque.addFirst(4);
        deque.removeLast();
        deque.addFirst(6);
        deque.addFirst(7);
        assertThat(deque.get(1)).isEqualTo(6);
        deque.get(1);
        deque.removeFirst();
        assertThat(deque.get(0)).isEqualTo(6);
        deque.get(0);
        deque.removeFirst();
        deque.addFirst(12);
        deque.removeFirst();
        deque.addFirst(14);
        deque.addFirst(15);
        deque.addLast(16);
        deque.removeLast();
        deque.removeLast();
        deque.removeFirst();
        deque.addLast(20);
        deque.addLast(21);
        deque.removeLast();
        deque.get(0);
        assertThat(deque.get(0)).isEqualTo(20);
        assertThat(deque.get(-1)).isEqualTo(null);
        assertThat(deque.get(deque.size())).isEqualTo(null);
    }

    @Test
    void getTest2() {
        ArrayDeque61B testArrayDeque = new ArrayDeque61B();
        testArrayDeque.addLast(0);
        assertThat(testArrayDeque.removeFirst()).isEqualTo(0);
        testArrayDeque.addFirst(2);
        assertThat(testArrayDeque.removeLast()).isEqualTo(2);
        testArrayDeque.addLast(4);
        assertThat(testArrayDeque.removeLast()).isEqualTo(4);
        testArrayDeque.addLast(6);
        assertThat(testArrayDeque.get(0)).isEqualTo(6);
        testArrayDeque.addLast(8);
        assertThat(testArrayDeque.removeFirst()).isEqualTo(6);
        testArrayDeque.addFirst(10);
        assertThat(testArrayDeque.removeFirst()).isEqualTo(10);
        assertThat(testArrayDeque.removeLast()).isEqualTo(8);
    }

    @Test
    void getTest3() {
        ArrayDeque61B testArrayDeque = new ArrayDeque61B();
        testArrayDeque.addLast(0);
        testArrayDeque.addLast(1);
        assertThat(testArrayDeque.removeFirst()).isEqualTo(0);
        testArrayDeque.addLast(3);
        assertThat(testArrayDeque.removeFirst()).isEqualTo(1);
    }

    @Test
    void getTest4() {
        ArrayDeque61B testArrayDeque = new ArrayDeque61B();
        testArrayDeque.addFirst(0);
        assertThat(testArrayDeque.isEmpty()).isFalse();
        testArrayDeque.addFirst(2);
        testArrayDeque.addFirst(3);
        testArrayDeque.addFirst(4);
        testArrayDeque.addFirst(5);
        testArrayDeque.addFirst(6);
        testArrayDeque.addFirst(7);
        testArrayDeque.addFirst(8);
        assertThat(testArrayDeque.removeLast()).isEqualTo(0);
        testArrayDeque.addFirst(10);
        testArrayDeque.addFirst(11);
        testArrayDeque.addFirst(12);
        testArrayDeque.addFirst(13);
        testArrayDeque.addFirst(14);
        assertThat(testArrayDeque.isEmpty()).isFalse();
        testArrayDeque.addFirst(16);
        testArrayDeque.addFirst(17);
        testArrayDeque.addFirst(18);
        testArrayDeque.addFirst(19);
        assertThat(testArrayDeque.removeLast()).isEqualTo(2);
        testArrayDeque.addFirst(21);
        testArrayDeque.addFirst(22);
        testArrayDeque.addFirst(23);
        testArrayDeque.addFirst(24);
        testArrayDeque.addFirst(25);
        testArrayDeque.addFirst(26);
        testArrayDeque.addFirst(27);
        testArrayDeque.addFirst(28);
        testArrayDeque.addFirst(29);
        testArrayDeque.addFirst(30);
        testArrayDeque.addFirst(31);
        testArrayDeque.addFirst(32);
        testArrayDeque.addFirst(33);
        testArrayDeque.addFirst(34);
        testArrayDeque.addFirst(35);
        testArrayDeque.addFirst(36);
        testArrayDeque.addFirst(37);
        testArrayDeque.addFirst(38);
        testArrayDeque.addFirst(39);
        testArrayDeque.addFirst(40);
        testArrayDeque.addFirst(41);
        testArrayDeque.addFirst(42);
        assertThat(testArrayDeque.removeLast()).isEqualTo(3);
    }

    @Test
    void sizeTest(){
        ArrayDeque61B deque = new ArrayDeque61B();
        deque.addFirst(0);
        deque.addLast(-1);
        deque.addFirst(1);
        deque.addLast(-2);
        deque.addFirst(2);     // [2, 1, 0, -1, -2]
        assertThat(deque.size()).isEqualTo(5);
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    void isEmptyTest() {
        ArrayDeque61B deque = new ArrayDeque61B();
        assertThat(deque.isEmpty()).isTrue();
        deque.addFirst(0);
        assertThat(deque.isEmpty()).isFalse();
        deque.addLast(-1);
        assertThat(deque.isEmpty()).isFalse();
        deque.removeFirst();
        assertThat(deque.isEmpty()).isFalse();
        deque.removeLast();
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    void addAfterRemoveTest(){
        ArrayDeque61B deque = new ArrayDeque61B();
        deque.addFirst(0);
        assertThat(deque.removeLast()).isEqualTo(0);
        deque.addFirst(2);
        assertThat(deque.removeLast()).isEqualTo(2);
        deque.addLast(3);
    }

    @Test
    void resizeUpAndDownTest(){
        ArrayDeque61B deque = new ArrayDeque61B();
        for (int i = 0; i < 16; i++){
            deque.addLast(i);
        }
        for (int i = 0; i < 16; i++){
            deque.removeLast();
        }
    }

    @Test
    void addLastTestBasicWithoutToList() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1).containsExactly("front", "middle", "back");
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1.equals(lld2)).isTrue();

        lld2.removeLast();
        assertThat(lld1.equals(lld2)).isFalse();
    }

    @Test
    public void toStringTest() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        String expectedArray = "[front, middle, back]";
        assertThat(lld1.toString()).isEqualTo(expectedArray);
    }
}
