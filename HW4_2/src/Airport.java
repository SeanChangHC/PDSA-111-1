//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import edu.princeton.cs.algs4.*;
class Airport {
    public class ABC{
        public double a, b, c;
        public ABC(double a, double b, double c){
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    // Output smallest average distance with optimal selection of airport location.
    public double airport(List<int[]> houses) {
        double avgDistance = 100;
        double avgDistance_final = 0;
        double xt = 0;
        double yt = 0;
        double xavg = 0;
        double yavg = 0;
        int size = 0;
        int smallidx = 0;
        Point2D[] points = new Point2D[houses.size()];
        Point2D [] conv = new Point2D[houses.size()];
        ArrayList<ABC> line = new ArrayList<ABC>();
        Double[] dtc;

        for(int i = 0 ;i < houses.size(); i++){
            int x = houses.get(i)[0];
            int y =  houses.get(i)[1];
            xt += x;
            yt += y;
            points[i] = new Point2D(x, y);
        }

        GrahamScan graham = new GrahamScan(points);

        for (Point2D p : graham.hull()) {
            double x1, x2, y1, y2;
            conv[size] = p;
            if(size>0) {
                x2 = p.x();
                y2 = p.y();
                x1 = conv[size - 1].x();
                y1 = conv[size - 1].y();
                ABC abc = new ABC(y2 - y1, x1 - x2, x2 * y1 - x1 * y2);
                line.add(abc);
            }
            size++;

        }
        double lx, ly, fx, fy;
        lx = conv[size-1].x();
        ly = conv[size-1].y();
        fx = conv[0].x();
        fy = conv[0].y();
        ABC lastone = new ABC(ly - fy, fx - lx, lx * fy - fx * ly);
        line.add(lastone);







        xavg = xt / houses.size();
        yavg = yt / houses.size();


        dtc = new Double[size];

        for(int i = 0 ; i < line.size() ; i++){
            //d = ( Ax0 + By0 + C ) / sqrt ( A*A + B*B )
            if(line.get(i)!= null && (line.get(i).a*line.get(i).a + line.get(i).b*line.get(i).b) != 0)
                dtc[i] = Math.abs(line.get(i).a*xavg + line.get(i).b*yavg + line.get(i).c)/Math.sqrt(line.get(i).a*line.get(i).a + line.get(i).b*line.get(i).b);

            if(dtc[i] <= dtc[smallidx])
                smallidx = i;
        }

//        for(int i = 0 ; i < dtc.length ; i++){
//            if(dtc[i] <= dtc[smallidx])
//                smallidx = i;
//        }


        for(int i = 0; i < houses.size() ; i++){
//            d = ( Ax0 + By0 + C ) / sqrt ( A*A + B*B );
            avgDistance_final += (Math.abs(line.get(smallidx).a*houses.get(i)[0] + line.get(smallidx).b*houses.get(i)[1] + line.get(smallidx).c)/Math.sqrt(line.get(smallidx).a*line.get(smallidx).a + line.get(smallidx).b*line.get(smallidx).b));
        }
        avgDistance =avgDistance_final/houses.size();

        return avgDistance;
    }

    public static void main(String[] args) {
//        test t = new test(args);
//        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
//            add(new int[]{0,0});
//            add(new int[]{1,0});
//        }}));
//        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
//            add(new int[]{0,0});
//            add(new int[]{1,0});
//            add(new int[]{0,1});
//        }}));
//        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
//            add(new int[]{0,0});
//            add(new int[]{2,0});
//            add(new int[]{0,2});
//            add(new int[]{1,1});
//            add(new int[]{2,2});
//        }}));
    }
}

//class test{
//    public test(String[] args){
//        Airport sol = new Airport();
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(args[0])){
//            JSONArray all = (JSONArray) jsonParser.parse(reader);
//            for(Object CaseInList : all){
//                JSONArray a = (JSONArray) CaseInList;
//                int q_cnt = 0, wa = 0,ac = 0;
//                for (Object o : a) {
//                    q_cnt++;
//                    JSONObject person = (JSONObject) o;
//                    JSONArray arg_hou = (JSONArray) person.get("houses");
//                    double Answer = (double) person.get("answer");
//                    ArrayList<int[]> HOU = new ArrayList<int[]>();
//                    double Answer_W = 0;
//                    for(int i=0;i<arg_hou.size();i++){
//                        String spl = arg_hou.get(i).toString();
//                        String fir = "";
//                        String sec = "";
//                        String[] two = new String[2];
//                        two = spl.split(",");
//                        fir = two[0].replace("[","");
//                        sec = two[1].replace("]","");
//                        int[] hou = new int[2];
//                        hou[0] = Integer.parseInt(fir);
//                        hou[1] = Integer.parseInt(sec);
//                        HOU.add(hou);
//                    }
//                    Answer_W = sol.airport(HOU);
//                    if(Math.abs(Answer_W-Answer)<1e-4){
//                        System.out.println(q_cnt+": AC");
//                    }
//                    else {
//                        wa++;
//                        System.out.println(q_cnt+": WA");
//                        System.out.println("your answer : "+Answer_W);
//                        System.out.println("true answer : "+Answer);
//                    }
//
//                }
//                System.out.println("Score: "+(q_cnt-wa)+"/"+q_cnt);
//
//            }
//        }catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//}