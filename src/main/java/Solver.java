import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {
    private int moves;
    private Iterable<Board> solution;
    private Board initial;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Null initial value is not allowed");
        }
        this.initial = initial;
        //cache the hamming and manhattan result
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(initial, null,false));
        pq.insert(new Node(initial.twin(), null,true));
        Node node = pq.delMin();
        Board b = node.getBoard();

        while (!b.isGoal()) {
            for (Board bb : b.neighbors()) {
                // The critical optimization.
                // A* search has one annoying feature: search nodes corresponding to the same board are enqueued on the priority queue many times.
                // To reduce unnecessary exploration of useless search nodes, when considering the neighbors of a search node, don’t enqueue a neighbor if its board is the same as the board of the previous search node in the game tree.
                if (node.getParent() == null || !bb.equals(node.getParent().getBoard())) {
                    pq.insert(new Node(bb, node, false));
                }
            }
            node = pq.delMin();
            b = node.getBoard();
        }
        //To detect unresolvable situations, use the fact that boards are divided into two equivalence classes with respect to reachability:
        //Those that can lead to the goal board
        //Those that can lead to the goal board if we modify the initial board by swapping any pair of tiles (the blank square is not a tile).
        solvable = !node.isTwin();

        if (!solvable) {
            // 注意不可解的地图，moves 是 -1，solution 是 null
            moves = -1;
            solution = null;
        } else {
            // 遍历，沿着 parent 走上去
            ArrayList<Board> list = new ArrayList<>();
            while (node != null) {
                list.add(node.getBoard());
                node = node.getParent();
            }
            // 有多少个状态，减 1 就是操作次数
            moves = list.size() - 1;
            // 做一次反转
            Collections.reverse(list);
            solution = list;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private static class Node implements Comparable<Node> {
        private final Board board; // 结点
        private final Node parent; // 父亲
        private final boolean twin;
        private final int moves;
        private final int distance; //uses manhattan distance
        private final int priority;

        //boolean twin is the param distinguishing twin boards
        public Node(Board board, Node parent, boolean twin) {
            this.board = board;
            this.parent = parent;
            this.twin = twin;
            if (parent == null) {
                moves = 0;
            } else {
                moves = parent.moves + 1;
            }
            distance = board.manhattan();
            priority = distance + moves;
        }

        public Board getBoard() {
            return board;
        }

        public Node getParent() {
            return parent;
        }

        public boolean isTwin() {
            return twin;
        }

        @Override
        public int compareTo(Node node) {
            if (priority == node.priority) {
                return Integer.compare(distance, distance);
            } else {
                return Integer.compare(priority, node.priority);
            }
        }

        @Override
        public boolean equals(Object input) {
            if (input == null) {
                return false;
            }
            if (input instanceof Node) {
                Node inputNode = (Node) input;
                return getBoard().equals(inputNode.getBoard());
            }
            return false;
        }
    }



    // test client (see below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}