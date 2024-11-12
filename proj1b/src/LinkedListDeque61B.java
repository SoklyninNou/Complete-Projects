// package deque;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList; // import the ArrayList class


public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node sentinel;
    private int size;
    private class Node {
        private Node next;
        private Node prev;
        private T item;
        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        sentinel.next = new Node(x, sentinel.next, sentinel);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        sentinel.prev = new Node(x, sentinel, sentinel.prev);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node currNode = sentinel.next;
        for (int i = 0; i < size; i++) {
            list.add(currNode.item);
            currNode = currNode.next;
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

    // Citation: https://www.geeksforgeeks.org/linkedlist-removefirst-method-in-java/
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            T removedItem = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return removedItem;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            T removedItem = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return removedItem;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node currNode = sentinel.next;
        for (int i = 0; i < index; i++) {
            currNode = currNode.next;
        }
        return currNode.item;
    }

    @Override
    public T getRecursive(int index) {
        int i = 0;
        Node currNode = sentinel.next;
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(index, i, currNode);
    }

    private T getRecursiveHelper(int index, int i, Node currNode) {
        if (index == i) {
            return currNode.item;
        } else {
            currNode = currNode.next;
            return getRecursiveHelper(index, i + 1, currNode);
        }
    }

    public Iterator<T> iterator() {
        return new LinkedListSetIterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Deque61B) {
            Deque61B<T> o = (Deque61B<T>) other;
            if (o.size() != size) {
                return false;
            }
            for (int i = 0; i < this.size; i++) {
                if (!this.get(i).equals(o.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        String x = "[";
        Node currNode = sentinel.next;
        while (currNode != sentinel.prev) {
            x += currNode.item + ", ";
            currNode = currNode.next;
        }
        x += currNode.item + "]";
        return x;
    }

    public class LinkedListSetIterator implements Iterator<T> {
        private Node currNode;
        public LinkedListSetIterator() {
            currNode = sentinel;
        }

        @Override
        public boolean hasNext() {
            return currNode.next != sentinel;
        }

        @Override
        public T next() {
            currNode = currNode.next;
            return currNode.item;
        }
    }
}
