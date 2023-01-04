import java.util.*;
import java.lang.Math;
import edu.princeton.cs.algs4.Point2D;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Arrays;

import  edu.princeton.cs.algs4.*;





class Cluster {

    private class Pair{
        Point2D either;
        Point2D other;
        Pair(Point2D a,Point2D b){
            this.either = a;
            this.other = b;
        }
    }

    public class Distance implements Comparable<Distance>{
        Point2D point1;
        Point2D point2;
        double d;
        int i;
        int j;

        public Distance(Point2D p1, Point2D p2){
            this.point1 = p1;
            this.point2 = p2;
            double x1 = p1.x();
            double x2 = p2.x();
            double y1 = p1.y();
            double y2 = p2.y();
            this.d = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
        }

        public void setI(int i){
            this.i = i;
        }

        public void setJ(int j){
            this.j = j;
        }

        @Override
        public int compareTo(Distance dis) {
            if(d > dis.d){
                return 1;
            }
            else if(d < dis.d)
                return -1;
            else {
                if(this.i > dis.i)
                    return 1;
                else if (this.i < dis.i) {
                    return -1;
                }
                else {
                    if(this.j > dis.j)
                        return 1;
                    else if (this.j < dis.j) {
                        return -1;
                    }
                    else
                        return 0;
                }
            }
        }
    }


    public List<double[]> cluster(List<int[]> pointlist, int cluster_num) {
//        HashMap<Integer,Point2D> clusters = new HashMap<>();
//        HashMap<Integer,Integer> dclusters = new HashMap<>();
//        for(int i = 0; i <  points.size(); i++) {
//            clusters.put(i, new Point2D(points.get(i)[0], points.get(i)[1]));
//        }
//
//
//        MinPQ<Distance> pq = new MinPQ<>();
//
////        first calculate all distance and insert into pq
//
//        for(int i = 0 ; i < points.size(); i++){
//            Point2D p1 = clusters.get(i);
//            for(int j = i + 1 ; j < points.size(); j++){
//                Point2D p2 = clusters.get(j);
//                Distance ds = new Distance(p1, p2);
//                ds.setI(i);
//                ds.setJ(j);
//                pq.insert(ds);
//
//            }
//
//        }
//
//        int idx = clusters.size();
//        double prevdis = 0;
//        while(!pq.isEmpty()) {
////            Distance ds = pq.delMin();
////            System.out.println("Distance: "+ ds.d +", i: "+ds.i + ", j: " + ds.j);
//            if(clusters.size() == cluster_num) break;
//            Distance ds = pq.delMin();
//            Point2D p1 = ds.point1;
//            Point2D p2 = ds.point2;
//            int i = ds.i;
//            int j = ds.j;
//
//
//            if (clusters.get(i) == null && clusters.get(j) == null) {
//                continue;
//            }
//            else if(clusters.get(i) != null && clusters.get(j) == null){
//                if(prevdis == ds.d){
//                    int cnt = dclusters.get(j);
//                    int previdx = idx-1;
//                    double x = clusters.get(i).x();
//                    double y = clusters.get(i).y();
//                    double newx = (clusters.get(previdx).x() * cnt + x)/(cnt+1);
//                    double newy = (clusters.get(previdx).y() * cnt + y)/(cnt+1);
//                    cnt++;
//                    dclusters.put(j, cnt);
//                    clusters.remove(previdx);
//                    clusters.remove(i);
//
//                    Point2D newpoint = new Point2D(newx, newy);
//                    // cal distance btw newpoint and all other points
//                    for (Integer key : clusters.keySet()) {
//                        Point2D oldpoint = new Point2D(clusters.get(key).x(), clusters.get(key).y());
//                        Distance newds = new Distance(newpoint, oldpoint);
//                        newds.setI(idx);
//                        newds.setJ(key);
//                        pq.insert(newds);
//                    }
//                    clusters.put(idx, newpoint);
//                    idx++;
//
//                }else{
//
//
//
//                }
//
//            } else if (clusters.get(i) == null && clusters.get(j) != null) {
//                if(prevdis == ds.d){
//                    int cnt = dclusters.get(i);
//                    int previdx = idx-1;
//                    double x = clusters.get(j).x();
//                    double y = clusters.get(j).y();
//                    double newx = (clusters.get(previdx).x() * cnt + x)/(cnt+1);
//                    double newy = (clusters.get(previdx).y() * cnt + y)/(cnt+1);
//                    cnt++;
//                    dclusters.put(i, cnt);
//                    clusters.remove(previdx);
//                    clusters.remove(j);
//
//                    Point2D newpoint = new Point2D(newx, newy);
//                    // cal distance btw newpoint and all other points
//                    for (Integer key : clusters.keySet()) {
//                        Point2D oldpoint = new Point2D(clusters.get(key).x(), clusters.get(key).y());
//                        Distance newds = new Distance(newpoint, oldpoint);
//                        newds.setI(idx);
//                        newds.setJ(key);
//                        pq.insert(newds);
//                    }
//                    clusters.put(idx, newpoint);
//                    idx++;
//
//                }else{
//
//                }
//
//
//            }
//            else {
//                //remove the related point from clusters
//                clusters.remove(i);
//                clusters.remove(j);
//                dclusters.put(i,2);
//                dclusters.put(j,2);
//
//                // calculate new point
//                double newx = (p1.x() + p2.x()) / 2;
//                double newy = (p1.y() + p2.y()) / 2;
//                Point2D newpoint = new Point2D(newx, newy);
//
//                // cal distance btw newpoint and all other points
//                for (Integer key : clusters.keySet()) {
//                    Point2D oldpoint = new Point2D(clusters.get(key).x(), clusters.get(key).y());
//                    Distance newds = new Distance(newpoint, oldpoint);
//                    newds.setI(idx);
//                    newds.setJ(key);
//                    pq.insert(newds);
//                }
//
//                // put new point into clusters
//                clusters.put(idx, newpoint);
//                idx++;
//            }
//
//            prevdis = ds.d;
//
//        }


        //read input Point2D array

        HashMap<Point2D,Integer> hash = new HashMap<>();
        Point2D[] points_original;
        List<Point2D> points;
        Stack<Pair> stack = new Stack<Pair>();
        Stack<Point2D> stackParents = new Stack<Point2D>();
        HashMap<Point2D,Point2D> finalPoints = new HashMap<>();


        points_original = new Point2D[pointlist.size()];
        for(int i = 0 ;i < pointlist.size(); i++ ){
            points_original[i] = new Point2D(pointlist.get(i)[0], pointlist.get(i)[1]);
        }
        //convert Point2D array into Point2D arraylist
        points = new ArrayList<Point2D>(Arrays.asList(points_original));
        //push points into hashmap
        for(Point2D a : points) {
            hash.put(a,1);
        }
        //when Point2D arraylist size > 1
        while(points.size() >1) {
            //another Point2D array
            Point2D[] pointsArray = points.toArray(new Point2D[points.size()]);
            //get closest pair
            ClosestPair pair = new ClosestPair(pointsArray);
            Point2D pointA = pair.either();
            Point2D pointB = pair.other();
            //compute centroid x, y
            Double xWeightedAverage = ((pointA.x()*hash.get(pointA))+(pointB.x()*hash.get(pointB)))/(hash.get(pointA)+hash.get(pointB));
            Double yWeightedAverage = ((pointA.y()*hash.get(pointA))+(pointB.y()*hash.get(pointB)))/(hash.get(pointA)+hash.get(pointB));
            //get Point2D centroid point
            Point2D centroid = new Point2D(xWeightedAverage,yWeightedAverage);
            //get new weight
            int newWeight = hash.get(pointA)+hash.get(pointB);
            //put centroid point into hashmap
            hash.put(centroid,newWeight);
            //add centroid point into points
            points.add(centroid);
            //remove closest pair pointA, B
            points.remove(pointA);
            points.remove(pointB);
            //new Pair
            Pair temp = new Pair(pointA,pointB);
            //push this temp into stack
            stack.push(temp);
            //push centroid point into stackParent
            stackParents.push(centroid);
            //if points.size == 1 -> final Points.add(centroid)
            if(points.size()==1) {
                finalPoints.put(centroid,centroid);
            }
        }


        //clone the stack
        Stack<Pair> stackClone = (Stack<Pair>) stack.clone();
        //clone the stackparent
        Stack<Point2D> stackParentsClone = (Stack<Point2D>) stackParents.clone();
        //clone the final points
//        List<Point2D> finalPointsClone = new LinkedList<Point2D>(finalPoints);
        for(int i =0;i < (cluster_num-1);i++) {
            Point2D tempPoint = stackParentsClone.pop();
            Pair tempPair = stackClone.pop();
            finalPoints.put(tempPair.other,tempPair.other);
            finalPoints.put(tempPair.either,tempPair.either);
            finalPoints.remove(tempPoint);
        }
//        if(finalPoints.size()!=cluster_num) {
//            return null;
//        }

        class doubleComparator implements Comparator<double[]>{
            @Override
            public int compare(double[] o1, double[] o2) {
                if(o1[0] > o2[0]){
                    return 1;
                } else if (o1[0] < o2[0]) {
                    return -1;
                }
                else {
                    if(o1[1] > o2[1])
                        return 1;
                    else if (o1[1] < o2[1]) {
                        return -1;
                    }
                    else {
                        return 0;
                    }
                }
            }
        }



        ArrayList<double[]> ans = new ArrayList<>();
        for(Point2D p : finalPoints.keySet()){
            ans.add(new double[]{p.x(), p.y()});
        }
        Collections.sort(ans,new doubleComparator());




        return ans;
    }









    public static void main(String[] args) {
//        List<double[]> out = new Cluster().cluster(new ArrayList<int[]>(){{
//            add(new int[]{99, 47});
//            add(new int[]{33, 78});
//            add(new int[]{75, 5});
//            add(new int[]{19, 79});
//            add(new int[]{44, 79});
//            add(new int[]{88, 30});
//            add(new int[]{7, 98});
//            add(new int[]{77, 26});
//            add(new int[]{97, 33});
//            add(new int[]{64, 34});
//        }}, 4);
//        List<double[]> out = new Cluster().cluster(new ArrayList<int[]>(){{
//            add(new int[]{0,0});
//            add(new int[]{1,0});
//            add(new int[]{0,1});
//        add(new int[]{3,0});

//        }}, 2);
//        for(double[] o: out)
//            System.out.println(Arrays.toString(o));

        test t = new test(args);

    }
}
class test{
    public test(String[] args){
        Cluster sol = new Cluster();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0])){
            JSONArray all = (JSONArray) jsonParser.parse(reader);
            for(Object CaseInList : all){
                JSONArray a = (JSONArray) CaseInList;
                int q_cnt = 0, wa = 0,ac = 0;
                for (Object o : a) {
                    q_cnt++;
                    JSONObject person = (JSONObject) o;
                    JSONArray point = (JSONArray) person.get("points");
                    Long clusterNumber = (Long) person.get("cluster_num");
                    JSONArray arg_ans = (JSONArray) person.get("answer");
                    int points_x[] = new int[point.size()];
                    int points_y[] = new int[point.size()];
                    double Answer_x[] = new double[arg_ans.size()];
                    double Answer_y[] = new double[arg_ans.size()];
                    List<double[]> ansClus = new ArrayList<double[]>();
                    ArrayList<int[]> pointList = new ArrayList<int[]>();
                    for(int i=0;i<clusterNumber;i++){
                        String ansStr = arg_ans.get(i).toString();
                        ansStr = ansStr.replace("[","");ansStr = ansStr.replace("]","");
                        String[] parts = ansStr.split(",");
                        Answer_x[i] = Double.parseDouble(parts[0]);
                        Answer_y[i] = Double.parseDouble(parts[1]);
                    }
                    for(int i=0;i< point.size();i++){
                        String ansStr = point.get(i).toString();
                        ansStr = ansStr.replace("[","");ansStr = ansStr.replace("]","");
                        String[] parts = ansStr.split(",");
                        pointList.add(new int[]{Integer.parseInt(parts[0]),Integer.parseInt(parts[1])});
                    }
                    ansClus = sol.cluster(pointList,Integer.parseInt(clusterNumber.toString()));
                    if(ansClus.size()!=clusterNumber){
                        wa++;
                        System.out.println(q_cnt+": WA");
                        break;
                    } else{
                        for(int i=0;i<clusterNumber;i++){
                            if(ansClus.get(i)[0]!=Answer_x[i] || ansClus.get(i)[1]!=Answer_y[i]){
                                wa++;
                                System.out.println(q_cnt+": WA");
                                break;
                            }
                        }
                        System.out.println(q_cnt+": AC");
                    }
                }
                System.out.println("Score: "+(q_cnt-wa)+"/"+q_cnt);

            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}