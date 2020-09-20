/* *****************************************************************************
 *  Name: XiaoLiu
 *  Date: 2020/9/19
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// array implementation is memory fast
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int CAPACITY = 8;
    private int head;
    private int tail;
    private int size;
    private Item[] array;

    // construct an empty randomize queue
    public RandomizedQueue() {
        head = 0;
        tail = 0;
        size = 0;
        array = (Item[]) new Object[CAPACITY];
    }

    // the queue is empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // get the size of queue
    public int size() {
        return size;
    }

    // add item i
    public void enqueue(Item item) {
        if (isNull(item))
            throw new IllegalArgumentException();
        if (size == array.length) resize(array.length * 2);
        array[tail++] = item;
        if (size > 0 && tail == array.length) tail = 0;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int randPos = StdRandom.uniform(size);
        Item item = array[randPos];
        remove(randPos);
        size--;
        return item;
    }

    private void remove(int pos) {
        for (int i = pos; i < size - pos; i++) {
            array[i] = array[(i + 1) % array.length];
        }
        array[tail--] = null;
    }

    // return a random item(but not remove)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int randPos = StdRandom.uniform(size);
        Item item = array[randPos];
        return item;
    }

    private void resize(int len) {
        Item[] copy = (Item[]) new Object[len];
        for (int i = 0; i < size; i++) {
            copy[i] = array[(head + i) % array.length];
        }
        array = copy;
        head = 0;
        tail = size;
    }

    private boolean isNull(Item item) {
        return item == null;
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    // inner class
    private class QueueIterator implements Iterator<Item> {

        private int[] randomIndexs;
        private int cur;

        public QueueIterator() {
            randomIndexs = new int[size];
            for (int i = 0; i < size; i++)
                randomIndexs[i] = i;
            StdRandom.shuffle(randomIndexs);
            cur = 0;
        }

        public boolean hasNext() {
            return cur != randomIndexs.length;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return array[randomIndexs[cur++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit test (required)
    public static void main(String[] args) {
        // test 1
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 4; i++) {
            queue.enqueue(i);
        }
        for (Integer i : queue) {
            for (Integer j : queue)
                StdOut.print(i + "-" + j + " ");
            StdOut.println();
        }
        StdOut.println("size:" + queue.size);

        // test 2
        RandomizedQueue<Integer> queue1 = new RandomizedQueue<Integer>();
        for (int i = 0; i < 4; i++) {
            queue1.enqueue(i);
            queue1.dequeue();
        }
        for (Integer i : queue1) StdOut.println(i);
        StdOut.println(queue1.isEmpty());

        // test 3
        RandomizedQueue<Integer> queue2 = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            queue2.enqueue(i);
        }
        Iterator<Integer> it = queue2.iterator();
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; ++i)
                queue2.dequeue();
            it = queue2.iterator();
            while (it.hasNext()) StdOut.print(it.next() + " ");
            StdOut.println(" size: " + queue2.size);
        }
    }
}
