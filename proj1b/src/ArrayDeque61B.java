import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int first;
    private int last;
    private final int capacity = 2;
    private final int sizeMultiplier = 2;
    public ArrayDeque61B() {
        items = (T[]) new Object[capacity];
        size = 0;
        first = 0;
        last = 0;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resizeUp();
        }
        if (items[first] == null) {
            items[first] = x;
        } else {
            first = (first - 1 + items.length) % items.length;
            items[first] = x;
        }
        ++size;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resizeUp();
        }
        if (items[last] == null) {
            items[last] = x;
        } else {
            last = (last + 1) % items.length;
            items[last] = x;
        }
        ++size;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<T>(size);
        int index = first;
        for (int i = 0; i < size; ++i) {
            if (index == items.length) {
                index = 0;
            }
            list.add(items[index]);
            ++index;
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removedItem = items[first];
        items[first] = null;
        --size;
        if (size == 0) {
            first = 0;
            last = 0;
        } else {
            first = (first + 1) % items.length;
        }
        if (items.length > capacity && size <= items.length / sizeMultiplier) {
            resizeDown();
        }
        return removedItem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removedItem = items[last];
        items[last] = null;
        --size;
        if (size == 0) {
            last = 0;
            first = 0;
        } else {
            last = (last - 1 + items.length) % items.length;
        }
        if (items.length > capacity && size <= items.length / sizeMultiplier) {
            resizeDown();
        }
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index > size || index < 0) {
            return null;
        } else {
            return items[(first + index) % items.length];   // return the index relative to the Arraylist created
        }
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    public void resizeUp() {
        T[] newArrayList = (T[]) new Object[items.length * sizeMultiplier];
        int index = first;
        for (int i = 0; i < size; ++i) {
            newArrayList[i] = items[index];
            index = (index + 1) % items.length;
        }
        items = newArrayList;
        first = 0;
        last = size - 1;
    }

    public void resizeDown() {
        T[] newArrayList = (T[]) new Object[items.length / sizeMultiplier];
        int index = first;
        for (int i = 0; i < size; ++i) {
            newArrayList[i] = items[index];
            index = (index + 1) % items.length;
        }
        items = newArrayList;
        first = 0;
        last = size - 1;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Deque61B) {
            Deque61B<T> o = (Deque61B<T>) other;
            if (o.size() != size) {
                return false;
            }
            for (int i = 0; i < size; ++i) {
                if (!items[(first + i) % items.length].equals(o.get((first + i) % items.length))) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String x = "[";
        for (int i = 0; i < size - i; ++i) {
            x += items[i];
            x += ", ";
        }
        x += items[size - 1];
        x += "]";
        return x;
    }

    public int arrayCapacity() {
        return items.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    public class ArraySetIterator implements Iterator<T> {
        private int index;
        private int i;
        public ArraySetIterator() {
            index = first;
            i = 0;
        }
        public boolean hasNext() {
            return i < size;
        }
        public T next() {
            T returnItem = items[(index + i) % items.length];
            ++i;
            return returnItem;
        }
    }
}
