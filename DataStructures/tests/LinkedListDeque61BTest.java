import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

// import deque.*;

import java.util.Iterator;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void testIsEmpty() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         assertWithMessage("Your output: " + lld1.isEmpty())
                .that(lld1.isEmpty())
                .isTrue();
         lld1.addFirst(0);
         assertWithMessage("Your output: " + lld1.isEmpty())
                .that(lld1.isEmpty())
                .isFalse();
         lld1.addFirst(12);
         assertWithMessage("Your output: " + lld1.isEmpty())
                .that(lld1.isEmpty())
                .isFalse();
        lld1.removeFirst();
        assertWithMessage("Your output: " + lld1.isEmpty())
                .that(lld1.isEmpty())
                .isFalse();
         lld1.addFirst(-12);
         assertWithMessage("Your output: " + lld1.isEmpty())
                .that(lld1.isEmpty())
                .isFalse();
         lld1.removeFirst();
         assertWithMessage("Your output: " + lld1.isEmpty())
                .that(lld1.isEmpty())
                .isFalse();
         lld1.removeFirst();
         assertWithMessage("Your output: " + lld1.isEmpty())
                .that(lld1.isEmpty())
                .isTrue();
    }

    @Test
    public void testSize() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         assertThat(lld1.size()).isEqualTo(0);
         lld1.addFirst(0);
         assertThat(lld1.size()).isEqualTo(1);
         lld1.addFirst(1);
         assertThat(lld1.size()).isEqualTo(2);
         lld1.removeFirst();
         lld1.removeLast();
         assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void get() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         lld1.addFirst(0);      // [0]
         lld1.addFirst(1);      // [1,0]
         lld1.get(0);
         assertThat(lld1.get(0)).isEqualTo(1);
         assertThat(lld1.get(1)).isEqualTo(0);
         assertThat(lld1.get(lld1.size())).isEqualTo(null);
         assertThat(lld1.get(-1)).isEqualTo(null);
         assertThat(lld1.get(28723)).isEqualTo(null);
    }

    @Test
    public void getRecursive() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(0);
        lld1.addFirst(1);
        assertThat(lld1.getRecursive(0)).isEqualTo(1);
        assertThat(lld1.getRecursive(1)).isEqualTo(0);
        assertThat(lld1.getRecursive(lld1.size())).isEqualTo(null);
        assertThat(lld1.getRecursive(-1)).isEqualTo(null);
        assertThat(lld1.getRecursive(28723)).isEqualTo(null);
    }

    @Test
    public void removeFirst() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         lld1.addFirst(0);
         lld1.addFirst(1);
         lld1.addFirst(2);      // [2, 1, 0]
         assertThat(lld1.removeFirst()).isEqualTo(2);
         assertThat(lld1.toList()).containsExactly(1,0).inOrder();
         assertThat(lld1.removeFirst()).isEqualTo(1);
         assertThat(lld1.toList()).containsExactly(0).inOrder();
         assertThat(lld1.removeFirst()).isEqualTo(0);
    }

    @Test
    public void removeLast() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         lld1.addFirst(0);
         lld1.addFirst(1);
         lld1.addFirst(2);      // [2, 1, 0]
         assertThat(lld1.removeLast()).isEqualTo(0);
         assertThat(lld1.toList()).containsExactly(2,1).inOrder();
         assertThat(lld1.removeLast()).isEqualTo(1);
         assertThat(lld1.toList()).containsExactly(2).inOrder();
         assertThat(lld1.removeLast()).isEqualTo(2);
    }

    @Test
    public void addAfterRemoveTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         assertThat(lld1.isEmpty()).isTrue();
         lld1.addFirst(0);
         assertThat(lld1.isEmpty()).isFalse();
         assertThat(lld1.removeFirst()).isEqualTo(0);
         assertThat(lld1.isEmpty()).isTrue();
         lld1.addLast(1);
         assertThat(lld1.isEmpty()).isFalse();
         assertThat(lld1.removeFirst()).isEqualTo(1);
         assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void toListTest(){
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         assertThat(lld1.toList()).containsExactly();
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]
         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();

    }

    @Test
    void addLastTestBasicWithoutToList() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");  // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back");   // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1).containsExactly("front", "middle", "back");
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();

        lld1.addLast("middle");
        lld1.addFirst("front");
        lld1.addLast("back");

        lld2.addLast("middle");
        lld1.addFirst("front");
        lld2.addLast("back");

        assertThat(lld1.equals(lld2)).isTrue();

        lld2.removeLast();
        assertThat(lld1.equals(lld2)).isFalse();
    }

    @Test
    public void toStringTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        String expectedArray = "[front, middle, back]";
        assertThat(lld1.toString()).isEqualTo(expectedArray);
    }

}