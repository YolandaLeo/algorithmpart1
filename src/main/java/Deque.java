import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node<Item> first;
    private Node<Item> last;

    private class Node<Item> {
        Item value;
        Node front;
        Node back;
        Node(Item value) {
            this.value = value;
        }
        Node() { }
    }

    // construct an empty deque
    public Deque() {
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of elements on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (first == null) {
            first = new Node<>(item);
            last = first;
        } else {
            Node node = new Node<>(item);
            node.back = first;
            first.front = node;
            first = node;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (last == null) {
            last = new Node<>(item);
            first = last;
        } else {
            Node node = new Node<>(item);
            node.front = last;
            last.back = node;
            last = node;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item value = first.value;
        Node node = first.back;
        if (node != null) {
            node.front = null;
            first = node;
        } else {
            last = null;
            first = null;
        }
        size--;
        return value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item value = last.value;
        Node node = last.front;
        if (node != null) {
            node.back = null;
            last = node;
        } else {
            first = null;
            last = null;
        }
        size--;
        return value;
    }


    // return an iterator over elements in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node<Item> current = first;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item result = current.value;
                current = current.back;
                return result;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.addFirst(2);

        for (Integer i : deque) {
            System.out.print(i + " ");
        }
    }

}
