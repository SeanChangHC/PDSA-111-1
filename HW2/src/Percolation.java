
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.*;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
class Percolation {

    private boolean[][] opened;
    private int top;
    private int bottom;
    private int size;
    private WeightedQuickUnionUF qf;
    private WeightedQuickUnionUF pc;
    private WeightedQuickUnionUF tr;
    public  int r;
    public  int thej;
    public  int listlength = 1;

    public Node[] firstroot;

    public Node[] lastroot;


    private static class Node {
        private Point2D site;
        private Node next;
    }

    /**
     * Creates N-by-N grid, with all sites blocked.
     */
    public Percolation(int N) {
        size = N;
        top = size * size;
        bottom = size * size + 1;
        qf = new WeightedQuickUnionUF(size * size + 2);
        tr = new WeightedQuickUnionUF(size * size + 2);
        pc = new WeightedQuickUnionUF(size * size + 2);
        opened = new boolean[size][size];
        firstroot = new Node[size * size];
        lastroot = new Node[size * size];

    }

    /**
     * Opens site (row i, column j) if it is not already.
     */
    public void open(int i, int j) {
        opened[i][j] = true;
        Node nd = new Node();
        Node first = nd;
        Node last = nd;
        firstroot[pc.find(getQFIndex(i,j))] = first;
        lastroot[pc.find(getQFIndex(i,j))] = last;
        nd.site = new Point2D(i,j);
        nd.next = null;        


        if(!percolates()){
            if (i == 0) {
                qf.union(getQFIndex(i, j), top);
                tr.union(getQFIndex(i, j), top);

            }
            if (i == (size-1)) {
                qf.union(getQFIndex(i, j), bottom);
            }

            //left
            if (j > 0 && isOpen(i, j - 1) && pc.find(getQFIndex(i,j-1))!=pc.find(getQFIndex(i,j))) {
                Node first1, first2;
                Node last1 , last2;

                first1 = firstroot[pc.find(getQFIndex(i,j -1))];

                firstroot[pc.find(getQFIndex(i,j -1))] = null;

                last1 =  lastroot[pc.find(getQFIndex(i,j -1))];

                lastroot[pc.find(getQFIndex(i,j -1))] = null;


                first2 = firstroot[pc.find(getQFIndex(i,j))];

                firstroot[pc.find(getQFIndex(i,j))] = null;


                last2 = lastroot[pc.find(getQFIndex(i, j))];

                lastroot[pc.find(getQFIndex(i, j))] = null;


                qf.union(getQFIndex(i, j), getQFIndex(i, j - 1));

                tr.union(getQFIndex(i, j), getQFIndex(i, j - 1));

                pc.union(getQFIndex(i, j), getQFIndex(i, j - 1));

                last1.next = first2;
                last1 = last2;
                firstroot[pc.find(getQFIndex(i,j))] = first1;
                lastroot[pc.find(getQFIndex(i,j))] = last1;
                last1.next = null;
                listlength ++;
            }
            //right
            if (j < (size-1) && isOpen(i, j + 1) && pc.find(getQFIndex(i,j+1))!=pc.find(getQFIndex(i,j))) {
                Node first1 , first2;
                Node last1 , last2;

                first1 = firstroot[pc.find(getQFIndex(i,j ))];
                firstroot[pc.find(getQFIndex(i,j ))] = null;


                first2 = firstroot[pc.find(getQFIndex(i, j+1))];
                firstroot[pc.find(getQFIndex(i, j+1))] = null;


                last1 = lastroot[pc.find(getQFIndex(i,j ))];
                lastroot[pc.find(getQFIndex(i,j ))] = null;


                last2 = lastroot[pc.find(getQFIndex(i,j+1))];
                lastroot[pc.find(getQFIndex(i,j+1))] = null;




                qf.union(getQFIndex(i, j), getQFIndex(i, j + 1));
                tr.union(getQFIndex(i, j), getQFIndex(i, j + 1));
                pc.union(getQFIndex(i, j), getQFIndex(i, j + 1));


                last1.next = first2;
                last1 = last2;
                firstroot[pc.find(getQFIndex(i,j))] = first1;
                lastroot[pc.find(getQFIndex(i,j))] = last1;
                last1.next = null;
                listlength ++;
            }

            //up
            if (i > 0 && isOpen(i - 1, j)&& pc.find(getQFIndex(i-1,j))!=pc.find(getQFIndex(i,j))) {
                Node first1 , first2;
                Node last1 , last2;

                first1 = firstroot[pc.find(getQFIndex(i-1,j ))];
                firstroot[pc.find(getQFIndex(i-1,j ))] = null;


                first2 = firstroot[pc.find(getQFIndex(i, j))];
                firstroot[pc.find(getQFIndex(i, j))] = null;


                last1 = lastroot[pc.find(getQFIndex(i-1,j ))];
                lastroot[pc.find(getQFIndex(i-1,j ))] = null;


                last2 = lastroot[pc.find(getQFIndex(i,j))];
                lastroot[pc.find(getQFIndex(i,j))] = null;




                qf.union(getQFIndex(i, j), getQFIndex(i - 1, j));
                tr.union(getQFIndex(i, j), getQFIndex(i - 1, j));
                pc.union(getQFIndex(i, j), getQFIndex(i - 1, j));

                last1.next = first2;
                last1 = last2;
                firstroot[pc.find(getQFIndex(i,j))] = first1;
                lastroot[pc.find(getQFIndex(i,j))] = last1;
                last1.next = null;
                listlength ++;
            }
            //down
            if (i < (size-1) && isOpen(i + 1, j)&& pc.find(getQFIndex(i+1,j))!=pc.find(getQFIndex(i,j))) {
                Node first1 , first2;
                Node last1 , last2;

                first1 = firstroot[pc.find(getQFIndex(i,j ))];
                firstroot[pc.find(getQFIndex(i,j ))] = null;


                first2 = firstroot[pc.find(getQFIndex(i+1, j))];
                firstroot[pc.find(getQFIndex(i+1, j))] = null;


                last1 = lastroot[pc.find(getQFIndex(i,j ))];
                lastroot[pc.find(getQFIndex(i,j ))] = null;


                last2 = lastroot[pc.find(getQFIndex(i+1,j))];
                lastroot[pc.find(getQFIndex(i+1,j))] = null;

                qf.union(getQFIndex(i, j), getQFIndex(i + 1, j));
                tr.union(getQFIndex(i, j), getQFIndex(i + 1, j));
                pc.union(getQFIndex(i, j), getQFIndex(i + 1, j));

                last1.next = first2;
                last1 = last2;
                firstroot[pc.find(getQFIndex(i,j))] = first1;
                lastroot[pc.find(getQFIndex(i,j))] = last1;
                last1.next = null;
                listlength ++;
            }

            if(percolates()) {
                r = pc.find(getQFIndex(i,j));
            }

        }
        else {
            if (i == 0) {
                qf.union(getQFIndex(i, j), top);
                tr.union(getQFIndex(i, j), top);

            }


            //left
            if (j > 0 && isOpen(i, j - 1)) {
                qf.union(getQFIndex(i, j), getQFIndex(i, j - 1));
                tr.union(getQFIndex(i, j), getQFIndex(i, j - 1));
                pc.union(getQFIndex(i, j), getQFIndex(i, j - 1));
            }
            //right
            if (j < (size-1) && isOpen(i, j + 1)) {

                qf.union(getQFIndex(i, j), getQFIndex(i, j + 1));
                tr.union(getQFIndex(i, j), getQFIndex(i, j + 1));
                pc.union(getQFIndex(i, j), getQFIndex(i, j + 1));
            }

            //up
            if (i > 0 && isOpen(i - 1, j)) {
                qf.union(getQFIndex(i, j), getQFIndex(i - 1, j));
                tr.union(getQFIndex(i, j), getQFIndex(i - 1, j));
                pc.union(getQFIndex(i, j), getQFIndex(i - 1, j ));
            }
            //down
            if (i < (size-1) && isOpen(i + 1, j)) {
               qf.union(getQFIndex(i, j), getQFIndex(i + 1, j));
                tr.union(getQFIndex(i, j), getQFIndex(i + 1, j));
                pc.union(getQFIndex(i, j), getQFIndex(i + 1, j));
            }

        }

    }

    /**
     * Is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
        return opened[i][j];
    }

    /**
     * Is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        if (0 <= i && i < size && 0 <= j && j < size) {
            return tr.find(top) == tr.find(getQFIndex(i , j));
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return qf.find(top) == qf.find(bottom);
    }

    private int getQFIndex(int i, int j) {
        return size * (i) + j ;
    }


    public Point2D[] PercolatedRegion () {
        Point2D[] p2d = new Point2D[listlength];
        Node temp = firstroot[r];
        for(int i = 0 ;i < listlength; i++){
            if(temp == null) break;;
            p2d[i] = temp.site;
            temp = temp.next;
        }
        Merge.sort(p2d);
        return  p2d;
    }



//    public static void test(String[] args){
//        Percolation g;
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(args[0])){
//            JSONArray all = (JSONArray) jsonParser.parse(reader);
//            int count = 0;
//            for(Object CaseInList : all){
//                count++;
//                JSONArray a = (JSONArray) CaseInList;
//                int testSize = 0; int waSize = 0;
//                System.out.print("Case ");
//                System.out.println(count);
//                //Board Setup
//                JSONObject argsSeting = (JSONObject) a.get(0);
//                a.remove(0);
//
//                JSONArray argSettingArr = (JSONArray) argsSeting.get("args");
//                g = new Percolation(
//                        Integer.parseInt(argSettingArr.get(0).toString()));
//
//                for (Object o : a)
//                {
//                    JSONObject person = (JSONObject) o;
//
//                    String func =  person.get("func").toString();
//                    JSONArray arg = (JSONArray) person.get("args");
//
//                    switch(func){
//                        case "open":
//                            g.open(Integer.parseInt(arg.get(0).toString()),
//                                    Integer.parseInt(arg.get(1).toString()));
//                            break;
//                        case "isOpen":
//                            testSize++;
//                            String true_isop = (Boolean)person.get("answer")?"1":"0";
//                            String ans_isop = g.isOpen(Integer.parseInt(arg.get(0).toString()),
//                                    Integer.parseInt(arg.get(1).toString()))?"1":"0";
//                            if(true_isop.equals(ans_isop)){
//                                System.out.println("isOpen : AC");
//                            }else{
//                                waSize++;
//                                System.out.println("isOpen : WA");
//                            }
//                            break;
//                        case "isFull":
//                            testSize++;
//                            String true_isfu =  (Boolean)person.get("answer")?"1":"0";
//                            String ans_isfu = g.isFull(Integer.parseInt(arg.get(0).toString()),
//                                    Integer.parseInt(arg.get(1).toString()))?"1":"0";
//                            if(true_isfu.equals(ans_isfu)){
//                                System.out.println("isFull : AC");
//                            }else{
//                                waSize++;
//                                System.out.println("isFull : WA");
//                            }
//                            break;
//                        case "percolates":
//                            testSize++;
//                            String true_per = (Boolean)person.get("answer")?"1":"0";
//                            String ans_per = g.percolates()?"1":"0";
//                            if(true_per.equals(ans_per)){
//                                System.out.println("percolates : AC");
//                            }else{
//                                waSize++;
//                                System.out.println("percolates : WA");
//                            }
//                            break;
//                        case "PercolatedRegion":
//                            testSize++;
//                            String true_reg = person.get("args").toString();
//                            String reg = "[";
//                            Point2D[] pr = g.PercolatedRegion();
//                            for (int i = 0; i < pr.length; i++) {
//                                reg = reg + ((int)pr[i].x() + "," + (int)pr[i].y());
//                                if(i != pr.length - 1){
//                                    reg =reg + ",";
//                                }
//                            }
//                            reg = reg +"]";
//                            if(true_reg.equals(reg)){
//                                System.out.println("PercolatedRegion : AC");
//                            }else{
//                                waSize++;
//                                System.out.println("PercolatedRegion : WA");
//                            }
//                            break;
//                    }
//
//                }
//                System.out.println("Score: " + (testSize-waSize) + " / " + testSize + " ");
//            }
//        }catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }


    public static void main(String args[]){

//        Percolation s = new Percolation(5);
//
//        s.open(1,1);
//        s.open(1,0);
//        s.open(2,0);
//        s.open(0,1);
//
//        s.open(2,1);
//        s.open(2,3);
//        s.open(3,3);
//        s.open(4,3);
//        s.open(3,2);
//        s.open(2,2);
//        s.open(3,3);

//        s.open(4,2);
//        s.open(3,4);
//        s.open(4,0);






        //        s.open(3,4);
//        s.open(3,3);



//        System.out.println(s.isFull(2,2));



//        test(args);

//        Percolation s = new Percolation(3);
//        s.open(0,1);
////        System.out.println(s.isFull(1, 1));
////        System.out.println(s.percolates());
//        s.open(1,0);
//        s.open(2,0);
////        System.out.println(s.isFull(1, 1));
////        System.out.println(s.isFull(0, 1));
////        System.out.println(s.isFull(2, 0));
////        System.out.println(s.percolates());
//        s.open(1,1);
////        System.out.println(s.isFull(1, 1));
////        System.out.println(s.isFull(0, 1));
////        System.out.println(s.isFull(2, 0));
////        System.out.println(s.isFull(2, 1));
////        System.out.println(s.percolates());


//        Point2D[] pr = s.PercolatedRegion();
//        for (int i = 0; i < pr.length; i++) {
//            System.out.println("("+(int)pr[i].x() + "," + (int)pr[i].y()+")");
//        }



    }

}
