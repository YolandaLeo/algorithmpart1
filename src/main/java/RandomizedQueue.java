import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] elements;
    private int head;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.elements = (Item[]) new Object[4];
        this.head = 0;
    }

    private RandomizedQueue(Item[] items, int head) {
        this.elements = items;
        this.head = head;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return head == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return head;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        elements[head] = item;
        if (head == elements.length - 1) {
            resize();
        }
        head++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(0, head);
        Item result = elements[idx];
        elements[idx] = elements[--head];
        elements[head] = null;
        if (head == elements.length / 4) {
            resize();
        }
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(0, head);
        return elements[idx];
    }

    private void resize() {
        int newLen;
        if (head == elements.length - 1) {
            newLen = elements.length * 2;
        } else {
            newLen = elements.length / 2;
        }
        Item[] newArray = (Item[]) new Object[newLen];
        for (int i = 0; i <= head; i++) {
            newArray[i] = elements[i];
        }
        elements = newArray;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int index;
        private Item[] tmpElements;

        public RandomizedIterator() {
            this.index = head;
            this.tmpElements = (Item[]) new Object[head];
            System.arraycopy(elements, 0, tmpElements, 0, index);
        }

        @Override
        public boolean hasNext() {
            return index != 0;
        }

        @Override
        public Item next() {
            if (index == 0) {
                throw new NoSuchElementException();
            }
            int target = StdRandom.uniform(0, index);
            Item item = tmpElements[target];
            tmpElements[target] = tmpElements[index - 1];
            tmpElements[index - 1] = null;
            index--;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        System.out.println(queue.sample());
        System.out.println(queue.sample());
        System.out.println(queue.sample());
//        for (Integer i : queue) {
//            System.out.println(i + " ");
//        }
    }

}
