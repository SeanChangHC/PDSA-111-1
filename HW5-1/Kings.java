import edu.princeton.cs.algs4.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

class King  implements  Comparable<King>{
    // optional, for reference only
    int Strength;
    int Range;
    int Index;
    King(int str,int rng, int i){
        Strength=str;
        Range=rng;
        Index=i;
    }

    @Override
    public int compareTo(King o) {

        if(o.Strength == Strength)
            return  0;
        else if (Strength > o.Strength) {
            return  -1;
        }
        else
            return 1;
    }
}
//class idxCompare implements Comparator<King>{
//
//    @Override
//    public int compare(King o1, King o2) {
//        if(o1.Index > o2.Index)
//            return -1;
//        else if (o1.Index < o2.Index) {
//            return 1;
//        }
//        else
//            return 0;
//    }
//}
class Kings {


    MinPQ<King> pq = new MinPQ<>();
    King [] kgrtn;
//    Queue<King> qu = new Queue<>();
    public Kings(int[] strength, int[] range){
        int[] k  = new int[strength.length];
//        for(int i = 0 ; i < strength.length ; i++)
//            k[i] = 1;
        Stack<King> stk = new Stack<>();
        Stack<King> rmn = new Stack<>();

        for(int i = 0; i < strength.length; i++) {
            int str = strength[i];
            int rng = range[i];
            int idx = i;

            if(!stk.isEmpty()){
                //stk last can attack me
                if(stk.peek().Strength > str && stk.peek().Range >= idx - stk.peek().Index){
                    rmn.push(new King(str, rng, idx));

                }
                //stk last can't attack me
                else{
                    if(!rmn.isEmpty()){
                        //rmn last can attack me
                        if(rmn.peek().Strength > str && rmn.peek().Range >= (idx-rmn.peek().Index)){

                        }
                        //rmn last can't attack me
                        else{
                            while(!rmn.isEmpty() && !(rmn.peek().Strength > str && rmn.peek().Range >= (idx - stk.peek().Index))){
                                rmn.pop();

                            }
                            //can't be attacked
                            //check if I can attack others
                            while(!stk.isEmpty() && stk.peek().Strength < str && rng >= (idx - stk.peek().Index)){
                                stk.pop();
                            }
                            stk.push(new King(str, rng, idx));

                        }

                    }
                    else {
                        while(!stk.isEmpty() && stk.peek().Strength < str && rng >= (idx - stk.peek().Index)){
                            stk.pop();
                        }
                        stk.push(new King(str, rng, idx));
                    }
                }
            }
            else {
                stk.push(new King(str, rng, idx));

            }

        }

        kgrtn = new King[stk.size()];




        int l = stk.size()-1;
        while(!stk.isEmpty()){
            King tk = stk.pop();
            kgrtn[l] =tk;
//            System.out.println("l: " + l);
            l--;
//            System.out.println(" index: "+ tk.Index+" str: "+ tk.Strength);
//            pq.insert(stk.pop());
        }
//        System.out.println("------------------");
        MergeX.sort(kgrtn);
//        for(int i = 0 ; i < kgrtn.length; i++)
//            System.out.println("index: "+ kgrtn[i].Index+" str: "+ kgrtn[i].Strength);

//        while(!pq.isEmpty()){
//            King M = pq.delMin();
//            System.out.println("str: " + M.Strength+" idx: " + M.Index);
//        }




    }

    public int[] topKKings(int k) {

//        if(k >= pq.size())
//            k = pq.size();
        if(k >= kgrtn.length)
            k = kgrtn.length;
        int[] rtn = new int[k];
//
        for(int i = 0; i < k ; i++){
            rtn[i] = kgrtn[i].Index;
//            rtn[i] = pq.delMin().Index;
        }

        return rtn;
        // complete the code by returning an int[]
        // remember to return the array of indexes in the descending order of strength
    }

    public static void main(String[] args) {
        Kings sol = new Kings(new int[] {1,2,6,1}
                            , new int[] {1,5,0,1});
        System.out.println(Arrays.toString(sol.topKKings(10)));
//        // In this case, the kings are [0, 2, 4, 5, 6] (without sorting, only by the order of ascending indices)
//        // Output: [2, 5, 0]
//        test t = new test(args);
    }
}

//class test{
//    public test(String[] args){
//        Kings sol;
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(args[0])){
//            JSONArray all = (JSONArray) jsonParser.parse(reader);
//            for(Object CaseInList : all){
//                JSONArray a = (JSONArray) CaseInList;
//                int q_cnt = 0, wa = 0,ac = 0;
//                for (Object o : a) {
//                    q_cnt++;
//                    JSONObject person = (JSONObject) o;
//                    JSONArray arg_str = (JSONArray) person.get("strength");
//                    JSONArray arg_rng = (JSONArray) person.get("attack_range");
//                    Long arg_k = (Long) person.get("k");
//                    JSONArray arg_ans = (JSONArray) person.get("answer");
//                    int STH[] = new int[arg_str.size()];
//                    int RNG[] = new int[arg_str.size()];
//                    int k = Integer.parseInt(arg_k.toString());
//
//                    int Answer[] = new int[arg_ans.size()];
//                    int Answer_W[] = new int[arg_ans.size()];
//                    for(int i=0;i<arg_ans.size();i++){
//                        Answer[i]=(Integer.parseInt(arg_ans.get(i).toString()));
//                    }
//                    for(int i=0;i<arg_str.size();i++){
//                        STH[i]=(Integer.parseInt(arg_str.get(i).toString()));
//                        RNG[i]=(Integer.parseInt(arg_rng.get(i).toString()));
//                    }
//                    sol = new Kings(STH,RNG);
//                    Answer_W = sol.topKKings(k);
//                    for(int i=0;i<arg_ans.size();i++){
//                        if(Answer_W[i]==Answer[i]){
//                            if(i==arg_ans.size()-1){
//                                System.out.println(q_cnt+": AC");
//                            }
//                        }else {
//                            wa++;
//                            System.out.println(q_cnt+": WA");
//                            break;
//                        }
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
