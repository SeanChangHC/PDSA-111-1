import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import edu.princeton.cs.algs4.*;


class Airport {

    // Output smallest average distance with optimal selection of airport location.
    public double airport(List<int[]> houses) {
        double avgDistance;


        return avgDistance;
    }

    public static void main(String[] args) {
//        test t = new test(args);
        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
            add(new int[]{0,0});
            add(new int[]{1,0});
            add(new int[]{0,1});
        }}));
    }
}

class test{
    public test(String[] args){
        Airport sol = new Airport();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0])){
            JSONArray all = (JSONArray) jsonParser.parse(reader);
            for(Object CaseInList : all){
                JSONArray a = (JSONArray) CaseInList;
                int q_cnt = 0, wa = 0,ac = 0;
                for (Object o : a) {
                    q_cnt++;
                    JSONObject person = (JSONObject) o;
                    JSONArray arg_hou = (JSONArray) person.get("houses");
                    double Answer = (double) person.get("answer");
                    ArrayList<int[]> HOU = new ArrayList<int[]>();
                    double Answer_W = 0;
                    for(int i=0;i<arg_hou.size();i++){
                        String spl = arg_hou.get(i).toString();
                        String fir = "";
                        String sec = "";
                        String[] two = new String[2];
                        two = spl.split(",");
                        fir = two[0].replace("[","");
                        sec = two[1].replace("]","");
                        int[] hou = new int[2];
                        hou[0] = Integer.parseInt(fir);
                        hou[1] = Integer.parseInt(sec);
                        HOU.add(hou);
                    }
                    Answer_W = sol.airport(HOU);
                    if(Math.abs(Answer_W-Answer)<1e-4){
                        System.out.println(q_cnt+": AC");
                    }
                    else {
                        wa++;
                        System.out.println(q_cnt+": WA");
                        System.out.println("your answer : "+Answer_W);
                        System.out.println("true answer : "+Answer);
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
