import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }
    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        assertThat(mad.max()).isNull();
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    // https://www.geeksforgeeks.org/comparator-reverseorder-method-in-java-with-examples/
    @Test
    public void basicTest2() {
        MaxArrayDeque61B<Integer> MaxArrayDeque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        assertThat(MaxArrayDeque.isEmpty()).isTrue();
        MaxArrayDeque.addFirst(1);
        MaxArrayDeque.addFirst(8);
        MaxArrayDeque.addFirst(42);
        Comparator<Integer> reverseOrder = Comparator.reverseOrder();
        assertThat(MaxArrayDeque.max(reverseOrder)).isEqualTo(1);
    }

    @Test
    public void basicTest3() {
        MaxArrayDeque61B<Integer> MaxArrayDeque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        MaxArrayDeque.addFirst(0);
        MaxArrayDeque.isEmpty();
        MaxArrayDeque.addFirst(2);
        assertThat(MaxArrayDeque.removeLast()).isEqualTo(0);
        MaxArrayDeque.addLast(4);

        MaxArrayDeque.addFirst(0);
        assertThat(MaxArrayDeque.removeFirst()).isEqualTo(0);
        MaxArrayDeque.addLast(2);
        MaxArrayDeque.addFirst(3);
        assertThat(MaxArrayDeque.removeFirst()).isEqualTo(3);
        assertThat(MaxArrayDeque.removeLast()).isEqualTo(2);
    }


}
