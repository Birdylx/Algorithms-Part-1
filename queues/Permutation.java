/* *****************************************************************************
 *  Name: XiaoLiu
 *  Date: 2020/9/19
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        // read string
        while (!StdIn.isEmpty()) {
            if (k == 0)
                return;
            String s = StdIn.readString();
            rq.enqueue(s);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
