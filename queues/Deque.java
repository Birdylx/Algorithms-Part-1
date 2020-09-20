/* *****************************************************************************
 *  Name: XiaoLiu
 *  Date: 2020/9/19
 *  Description:generalization of stack and queue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

// bidirection linked-list
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int length;

    // construct an empty Deque
    public Deque() {
        first = null;
        last = null;
        length = 0;
    }

    // inner class Node
    private class Node {
        Item item;
        Node prev;
        Node next;

        public Node() {
            item = null;
            prev = null;
            next = null;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return length == 0;
    }

    // return the number of items in deque
    public int size() {
        return length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (isNull(item))
            throw new IllegalArgumentException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (isEmpty()) last = first;
        else oldfirst.prev = first;
        length++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (isNull(item))
            throw new IllegalArgumentException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        length++;
    }

    // remove the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        if (length != 1) {
            first = first.next;
            first.prev = null;
        }
        else // special case if only one element
            first = null;
        length--;
        return item;
    }

    // remove the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        if (length != 1) {
            last = last.prev;
            last.next = null;
        }
        else // special case if only one element
            last = null;
        length--;
        return item;
    }

    private boolean isNull(Item item) {
        return item == null;
    }

    // return the iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new DeckIterator();
    }

    // implement the iterator
    private class DeckIterator implements Iterator<Item> {
        private Node cur = first;
        private int cur_len = length;

        public boolean hasNext() {
            return cur_len != 0;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;
            cur_len--;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit test, required;
    public static void main(String[] args) {
        // test 1
        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 0; i < 5; i++) {
            deque.addFirst(i);
            deque.addLast(i + 1);
        }
        for (Integer i : deque)
            StdOut.print(i + " ");
        StdOut.println("size:" + deque.size());

        // test 2
        Deque<Integer> deque_2 = new Deque<Integer>();
        for (int i = 0; i < 5; i++) {
            deque_2.addFirst(i);
            deque_2.removeLast();
            deque_2.addLast(i);
            deque_2.removeFirst();
        }
        for (Integer i : deque_2)
            StdOut.print(i + " ");
        StdOut.println("size:" + deque_2.size());

    }
}
