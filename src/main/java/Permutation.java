import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        if (args.length > 0) {
            Integer k = Integer.parseInt(args[0]);
            RandomizedQueue<String> queue = new RandomizedQueue<>();
            while (!StdIn.isEmpty()) {
                queue.enqueue(StdIn.readString());
            }
            while (k > 0) {
                System.out.println(queue.dequeue());
                k--;
            }
        }
    }

}
