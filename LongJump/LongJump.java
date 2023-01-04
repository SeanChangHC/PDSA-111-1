//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import java.util.NoSuchElementException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import edu.princeton.cs.algs4.*;
class BST {
    private Node root;             // root of BST


    private class Node {
        private int key;           // sorted by key
        private int val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree
        private int total;
        public Node(int key, int val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.total = key;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BST() {
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(int key) {
        if (key == 0) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != 0;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int get(int key) {
        return get(root, key);
    }

    private int get(Node x, int key) {
        if (key == 0) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return 0;
        int cmp = key - x.key;
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public int getTotal(int key) {
        return getTotal(root, key);
    }

    private int getTotal(Node x, int key) {
        if (key == 0) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return 0;
        int cmp = key - x.key;
        if (cmp < 0) return getTotal(x.left, key);
        else if (cmp > 0) return getTotal(x.right, key);
        else return x.total;
    }


    private boolean minInrange(Node x, int lo, int hi){
        return min(x).key >= lo && min(x).key <=hi;
    }

    private boolean maxInrange(Node x, int lo, int hi){
        return max(x).key >= lo && max(x).key <=hi;
    }
    public int getSum(int lo, int hi) {

        return getSum(root, lo, hi, 0);
    }
    private int getSum(Node x, int lo, int hi, int sum) {

 
        if (x == null || x.key == 0)
            return sum;


        boolean minInrange = minInrange(x, lo, hi);
        boolean maxInrange = maxInrange(x, lo, hi);
        int minx = min(x).key;
        int maxx = max(x).key;

        //left O right O
        if(x.left != null && x.right != null) {
            //min O max O
            if (minInrange && maxInrange) {
                sum += getTotal(x.key);
                return sum;
            }
            //min X max O
            else if (!minInrange && maxInrange) {
                // me in range
                if (x.key >= lo && x.key <= hi) {
                    sum = sum + x.key + getTotal(x.right.key);
                    return getSum(x.left, lo, hi, sum);
                }
                //me not in range
                else {
                    return getSum(x.right, lo, hi, sum);
                }
            }
            //min O max X
            else if (minInrange && !maxInrange) {
                //me in range
                if (x.key >= lo && x.key <= hi) {
                    sum = sum + x.key + getTotal(x.left.key);
                    return getSum(x.right, lo, hi, sum);
                }
                //me not in range
                else {
                    return getSum(x.left, lo, hi, sum);
                }
            }
            //min X max X
            else {
                //me in range
                if (x.key >= lo && x.key <= hi) {
                    sum += x.key;
                    sum = getSum(x.left,lo,hi, sum);
                    return getSum(x.right,lo,hi, sum);
                }
                //me not in range
                else {
                    if (x.key > hi && min(x).key < lo && max(x).key > hi) {
                        return getSum(x.left,lo,hi, sum);
                    }
                    if (x.key < lo && min(x).key < lo && max(x).key > hi) {
                        return getSum(x.right,lo,hi, sum);
                    }
                    if (x.key > hi && min(x).key > hi && max(x).key > hi) {
                        return sum;
                    }
                    if (x.key > hi && min(x).key < lo && max(x).key < lo) {
                        return sum;
                    }

                }

            }
        } else if (x.left != null && x.right == null) {
            if(x.key >= lo && x.key <= hi){
                if(minInrange(x,lo,hi)){
                    sum += getTotal(x.key);
                     return sum;
                }
                else {
                    sum += x.key;
                    return getSum(x.left,lo,hi, sum);

                }
            }
            else {
                if(minInrange(x,lo,hi)){
                    return getSum(x.left,lo,hi, sum);
                }
                else {
                    if(x.key> hi && min(x).key < lo){
                        return getSum(x.left,lo,hi,sum);
                    }
                    else {
                        return sum;
                    }

                }

            }

        } else if (x.left == null && x.right != null) {
            if(x.key >= lo && x.key <= hi){
                if(maxInrange){
                    sum += getTotal(x.key);
                    return sum;
                }
                else {
                    sum += x.key;
                    return getSum(x.right, lo,hi, sum);

                }
            }
            else {
                if(maxInrange){
                    return getSum(x.right, lo,hi, sum);
                }
                else {
                    if(x.key < lo && max(x).key > hi){
                        return getSum(x.right,lo,hi,sum);
                    }
                    else {
                        return sum;
                    }

                }
            }

        }else {

            if(x.key >= lo && x.key <= hi){
                sum += getTotal(x.key);
                return sum;
            }
            else {
                return sum;
            }

        }

        return sum;



    }




    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(int key, int val) {
        if (key == 0) throw new IllegalArgumentException("calls put() with a null key");
        if (val == 0) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }

    private Node put(Node x, int key, int val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int cmp = key - x.key;
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.key = key;
        x.size = 1 + size(x.left) + size(x.right);
        x.total = x.key + total(x.left) + total(x.right);
        return x;

    }

    private int total(Node x) {
        if (x == null) return 0;
        else return x.total;
    }


    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
        assert check();
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
        assert check();
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(int key) {
        if (key == 0) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node x, int key) {
        if (x == null) return null;

        int cmp = key - x.key;
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }


    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public int min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public int max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int floor(int key) {
        if (key == 0) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x.key;
    }

    private Node floor(Node x, int key) {
        if (x == null) return null;
        int cmp = key - x.key;
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    public int floor2(int key) {
        int x = floor2(root, key, 0);
        if (x == 0) throw new NoSuchElementException("argument to floor() is too small");
        else return x;

    }

    private int floor2(Node x, int key, int best) {
        if (x == null) return best;
        int cmp = key - x.key;
        if (cmp < 0) return floor2(x.left, key, best);
        else if (cmp > 0) return floor2(x.right, key, x.key);
        else return x.key;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     *
     * @param key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int ceiling(int key) {
        if (key == 0) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to ceiling() is too large");
        else return x.key;
    }

    private Node ceiling(Node x, int key) {
        if (x == null) return null;
        int cmp = key - x.key;
        if (cmp == 0) return x;
        if (cmp < 0) {
            Node t = ceiling(x.left, key);
            if (t != null) return t;
            else return x;
        }
        return ceiling(x.right, key);
    }

    /**
     * Return the key in the symbol table of a given {@code rank}.
     * This key has the property that there are {@code rank} keys in
     * the symbol table that are smaller. In other words, this key is the
     * ({@code rank}+1)st smallest key in the symbol table.
     *
     * @param rank the order statistic
     * @return the key in the symbol table of given {@code rank}
     * @throws IllegalArgumentException unless {@code rank} is between 0 and
     *                                  <em>n</em>–1
     */
    public int select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    // Return key in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private int select(Node x, int rank) {
        if (x == null) return 0;
        int leftSize = size(x.left);
        if (leftSize > rank) return select(x.left, rank);
        else if (leftSize < rank) return select(x.right, rank - leftSize - 1);
        else return x.key;
    }

    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(int key) {
        if (key == 0) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    // Number of keys in the subtree less than key.
    private int rank(int key, Node x) {
        if (x == null) return 0;
        int cmp = key - x.key;
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    /**
     * Returns all keys in the symbol table in ascending order,
     * as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (int key : st.keys())}.
     *
     * @return all keys in the symbol table in ascending order
     */
    public Iterable<Integer> keys() {
        if (isEmpty()) return new Queue<Integer>();
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range
     * in ascending order, as an {@code Iterable}.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return all keys in the symbol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive) in ascending order
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public Iterable<Integer> keys(int lo, int hi) {
        if (lo == 0) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == 0) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Integer> queue = new Queue<Integer>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Integer> queue, int lo, int hi) {
        if (x == null) return;
        int cmplo = lo-(x.key);
        int cmphi = hi-(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in the symbol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public int size(int lo, int hi) {
        if (lo == 0) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == 0) throw new IllegalArgumentException("second argument to size() is null");

        if (lo-(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    /**
     * Returns the height of the BST (for debugging).
     *
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * Returns the keys in the BST in level order (for debugging).
     *
     * @return the keys in the BST in level order traversal
     */
    public Iterable<Integer> levelOrder() {
        Queue<Integer> keys = new Queue<Integer>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    /*************************************************************************
     *  Check integrity of BST data structure.
     ***************************************************************************/
    private boolean check() {
        if (!isBST()) StdOut.println("Not in symmetric order");
        if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, 0, 0);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, int min, int max) {
        if (x == null) return true;
        if (min != 0 && x.key-(min) <= 0) return false;
        if (max != 0 && x.key-(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (int key : keys())
            if (key-(select(rank(key))) != 0) return false;
        return true;
    }
}





class LongJump {
    BST lj = new BST();

    public LongJump(int[] playerList){
        for(int i =0 ; i < playerList.length; i++){
            lj.put(playerList[i],playerList[i]);
        }

    }

    // Add new player in the competition with different distance
    public void addPlayer(int distance) {
        lj.put(distance,distance);

    }
    // return the winners total distance in range[from, to]
    public int winnerDistances(int from, int to) {
        int sum = lj.getSum(from, to);
        return sum;



    }
    public static void main(String[] args) {

//        test t = new test(args);
        LongJump solution = new LongJump(new int[]{5,4,3,2});
//        solution.addPlayer(8);
//        solution.addPlayer(9);
//        solution.addPlayer(91);
//        solution.addPlayer(1);
//        solution.addPlayer(3);
//        solution.addPlayer(97);
//        solution.addPlayer(47);
//        solution.addPlayer(6);
//        solution.addPlayer(65);
//        solution.addPlayer(76);
        System.out.println(solution.winnerDistances(2,5));
    }
}

//class test{
//    public test(String[] args) {
//        LongJump g;
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(args[0])) {
//            JSONArray all = (JSONArray) jsonParser.parse(reader);
//            int count = 0;
//            for (Object CaseInList : all) {
//                count++;
//                JSONArray a = (JSONArray) CaseInList;
//                int testSize = 0;
//                int waSize = 0;
//                System.out.print("Case ");
//                System.out.println(count);
//                //Board Setup
//                JSONObject argsSetting = (JSONObject) a.get(0);
//                a.remove(0);
//
//                JSONArray argSettingArr = (JSONArray) argsSetting.get("args");
//
//                int[] arr=new int[argSettingArr.size()];
//                for(int k=0;k<argSettingArr.size();k++) {
//                    arr[k] = (int)(long) argSettingArr.get(k);
//                }
//                g = new LongJump(arr);
//
//                for (Object o : a) {
//                    JSONObject person = (JSONObject) o;
//
//                    String func = person.get("func").toString();
//                    JSONArray arg = (JSONArray) person.get("args");
//
//                    switch (func) {
//                        case "addPlayer" -> g.addPlayer(Integer.parseInt(arg.get(0).toString()));
//                        case "winnerDistances" -> {
//                            testSize++;
//                            Integer t_ans = (int)(long)person.get("answer");
//                            Integer r_ans = g.winnerDistances(Integer.parseInt(arg.get(0).toString()),
//                                    Integer.parseInt(arg.get(1).toString()));
//                            if (t_ans.equals(r_ans)) {
//                                System.out.println("winnerDistances : AC");
//                            } else {
//                                waSize++;
//                                System.out.println("winnerDistances : WA");
//                                System.out.println("Your answer : "+r_ans);
//                                System.out.println("True answer : "+t_ans);
//                            }
//                        }
//                    }
//
//                }
//                System.out.println("Score: " + (testSize - waSize) + " / " + testSize + " ");
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//}