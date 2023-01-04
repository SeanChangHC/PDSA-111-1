
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import edu.princeton.cs.algs4.*;
import java.util.Arrays;
class warrior{
    int Strength;
    int Range;
    int Index;
    warrior(int str,int rng, int i){
        Strength=str;
        Range=rng;
        Index=i;
    }
}


class Warriors {
    public int[] warriors(int[] strength, int[] range){
        // Given the attributes of each warrior and output the minimal and maximum
        // index of warrior can be attacked by each warrior.
        int[] rtn = new int[strength.length * 2];
        //marginal condition
        rtn[0] = 0;
        rtn[2*(strength.length-1) + 1] = strength.length-1;
        Stack<warrior> stk = new Stack<warrior>();

        //loop over every element
        for(int i = 0 ; i < strength.length; i++){

            //declare warrior obj
            int idx = i;
            int stn = strength[i];
            int rng = range[i];
            warrior now = new warrior(stn, rng, idx);

            //not empty
            if(!stk.isEmpty()){
                int nowleftmost = 2*now.Index;
                int nowrightmost = 2*now.Index+1;
                int stacklastleftmost = 2 * stk.peek().Index ;
                int stacklastrightmost = 2 * stk.peek().Index+1;

                //stack last one > now
                if(stk.peek().Strength > now.Strength){
                    int gap = now.Index - stk.peek().Index;
                    //if in range set leftmost to peek + 1
                    if(now.Range != 0 && gap <= now.Range)
                        rtn[nowleftmost] = stk.peek().Index+1;
                    if(now.Range != 0 && gap > now.Range)
                        rtn[nowleftmost] = now.Index - now.Range;

                    if(now.Range == 0){
                        rtn[nowleftmost] = now.Index;
                        rtn[nowrightmost] = now.Index;
                    }
                    if(stk.peek().Range == 0){
                        rtn[stacklastleftmost] = stk.peek().Index;
                        rtn[stacklastrightmost] = stk.peek().Index;
                    }

                }

                if(stk.peek().Strength <= now.Strength) {
                    //when peek < now
                    while (!stk.isEmpty() && (stk.peek().Strength < now.Strength)) {
                        warrior temp = stk.pop();
                        int templeftmost = 2 * temp.Index;
                        int temprightmost = 2 * temp.Index + 1;
                        int gaptemp = now.Index - temp.Index;
                        if (temp.Range != 0) {
                            if (temp.Range >= gaptemp)
                                rtn[temprightmost] = now.Index - 1;
                            if (temp.Range < gaptemp)
                                rtn[temprightmost] = temp.Index + temp.Range;
                        }
                        if (now.Range != 0) {
                            if (!stk.isEmpty()) {
                                if (now.Range >= gaptemp)
                                    rtn[nowleftmost] = temp.Index;
                                if (now.Range < gaptemp)
                                    rtn[nowleftmost] = now.Index - now.Range;
                            } else {
                                if (now.Range >= now.Index)
                                    rtn[nowleftmost] = 0;
                                if (now.Range < now.Index)
                                    rtn[nowleftmost] = now.Index - now.Range;

                            }
                        }

                        if (now.Range == 0) {
                            rtn[nowleftmost] = now.Index;
                            rtn[nowrightmost] = now.Index;
                        }

                        if (temp.Range == 0) {
                            rtn[templeftmost] = temp.Index;
                            rtn[temprightmost] = temp.Index;
                        }


                    }

                    if (!stk.isEmpty() && stk.peek().Strength > now.Strength) {
                        if (now.Range != 0) {
                            if ((now.Index - stk.peek().Index) > now.Range)
                                rtn[nowleftmost] = now.Index - now.Range;
                            if ((now.Index - stk.peek().Index) <= now.Range)
                                rtn[nowleftmost] = stk.peek().Index + 1;
                        }
                        if (stk.peek().Range != 0) {
                            if ((now.Index - stk.peek().Index) >= stk.peek().Range)
                                rtn[2 * stk.peek().Index + 1] = stk.peek().Index + stk.peek().Range;
                            if ((now.Index - stk.peek().Index) < stk.peek().Range)
                                rtn[2 * stk.peek().Index + 1] = now.Index;
                        }
                        if (now.Range == 0) {
                            rtn[nowleftmost] = now.Index;
                            rtn[nowrightmost] = now.Index;
                        }

                        if (stk.peek().Range == 0) {
                            rtn[2 * stk.peek().Index + 1] = stk.peek().Index;
                            rtn[2 * stk.peek().Index ] = stk.peek().Index;
                        }
                    }

                    if (!stk.isEmpty() && stk.peek().Strength == now.Strength) {
                        warrior temp = stk.pop();
                        int templeftmost = 2 * temp.Index;
                        int temprightmost = 2 * temp.Index + 1;
                        int gaptemp = now.Index - temp.Index;
                        if (temp.Range != 0) {
                            if (temp.Range >= gaptemp)
                                rtn[temprightmost] = now.Index - 1;
                            if (temp.Range < gaptemp)
                                rtn[temprightmost] = temp.Index + temp.Range;
                        }
                        if (now.Range != 0) {
                            if (now.Range >= gaptemp)
                                rtn[nowleftmost] = temp.Index + 1;
                            if (now.Range < gaptemp)
                                rtn[nowleftmost] = now.Index - now.Range;
                        }
                        if(temp.Range==0){
                            rtn[templeftmost] = temp.Index;
                            rtn[temprightmost] = temp.Index;
                        }
                        if(now.Range==0){
                            rtn[nowleftmost] = now.Index;
                            rtn[nowrightmost] = now.Index;
                        }

                    }
                }

            }
            stk.push(now);


        }



        while(!stk.isEmpty()){
            warrior temp = stk.peek();
            int gapf = strength.length - 1 - temp.Index;
            int idx = strength.length - 1;
            if(temp.Index==idx && !stk.isEmpty()) {
                stk.pop();
                continue;
            }

            if(temp.Range != 0) {
                if (gapf <= temp.Range) {
                    rtn[2 * temp.Index + 1] = strength.length - 1;
                }
                else
                    rtn[2 * temp.Index + 1] = temp.Index + temp.Range;
            }else{
                rtn[2*temp.Index + 1] = temp.Index;
                rtn[2* temp.Index] = temp.Index;
            }
            stk.pop();

        }







        return rtn; // complete the code by returning an int[]
    }

    public static void main(String[] args) {
        Warriors sol = new Warriors();

//        System.out.println(Arrays.toString(
//                sol.warriors(new int[] {360, 289, 193, 974, 615, 560, 597, 124, 686, 807, 689, 701, 647, 926, 833, 701, 623, 432, 411, 562, 84, 739, 257, 947, 216, 711, 648, 895, 308, 424, 355, 442, 802, 393, 343, 176, 308, 462, 572, 408, 983, 824, 898, 185, 428, 590, 146, 906, 826, 369, 954, 569, 1, 749, 439, 722, 84, 755, 561, 959, 454, 733, 467, 101, 48, 98, 760, 61, 503, 559, 509, 137, 704, 93, 701, 362, 959, 476, 814, 182, 551, 459, 302, 306, 636, 298, 14, 68, 797, 394, 457, 424, 361, 80, 705, 953, 233, 343, 741, 418},
//                             new int[] {33, 41, 2, 28, 2, 18, 6, 18, 0, 11, 17, 11, 40, 19, 30, 7, 47, 20, 29, 43, 16, 30, 25, 44, 31, 0, 44, 42, 30, 0, 33, 47, 5, 31, 33, 0, 3, 47, 30, 12, 10, 0, 5, 44, 5, 3, 48, 35, 4, 22, 33, 49, 13, 43, 6, 7, 29, 25, 0, 46, 12, 3, 15, 1, 18, 23, 40, 21, 35, 37, 27, 45, 9, 19, 6, 45, 22, 37, 35, 5, 11, 27, 30, 36, 6, 3, 47, 18, 37, 26, 46, 22, 9, 27, 17, 9, 4, 0, 4, 40})));


//         Output: [0, 2, 1, 2, 2, 2, 0, 31, 4, 6, 5, 5, 5, 7, 7, 7, 8, 8, 4, 12, 10, 10, 10, 12, 12, 12, 4, 22, 14, 22, 15, 20, 16, 20, 17, 18, 18, 18, 17, 20, 20, 20, 15, 22, 22, 22, 4, 39, 24, 24, 25, 25, 26, 26, 24, 39, 28, 28, 29, 29, 30, 30, 28, 31, 28, 37, 33, 36, 34, 36, 35, 35, 35, 36, 33, 37, 33, 39, 39, 39, 30, 50, 41, 41, 41, 46, 43, 43, 43, 44, 43, 46, 46, 46, 41, 49, 48, 49, 49, 49, 41, 58, 51, 52, 52, 52, 51, 56, 54, 54, 54, 56, 56, 56, 51, 58, 58, 58, 41, 75, 60, 60, 60, 64, 62, 65, 63, 64, 64, 64, 64, 65, 60, 75, 67, 67, 67, 68, 67, 71, 70, 71, 71, 71, 67, 75, 73, 73, 73, 75, 75, 75, 60, 98, 77, 77, 77, 94, 79, 79, 79, 83, 81, 83, 82, 82, 82, 83, 79, 87, 85, 87, 86, 86, 86, 87, 79, 94, 89, 89, 89, 93, 91, 93, 92, 93, 93, 93, 89, 94, 86, 99, 96, 96, 97, 97, 96, 99, 99, 99]
//         Output: [0, 2, 1, 2, 2, 2, 0, 31, 4, 6, 5, 5, 5, 7, 7, 7, 8, 8, 4, 12, 10, 10, 10, 12, 12, 12, 4, 22, 14, 22, 15, 20, 16, 20, 17, 18, 18, 18, 17, 20, 20, 20, 15, 22, 22, 22, 4, 39, 24, 24, 25, 25, 26, 26, 24, 39, 28, 28, 29, 29, 30, 30, 28, 31, 28, 37, 33, 36, 34, 36, 35, 35, 35, 36, 33, 37, 33, 39, 39, 39, 30, 50, 41, 41, 41, 46, 43, 43, 43, 44, 43, 46, 46, 46, 41, 49, 48, 49, 49, 49, 41, 58, 51, 52, 52, 52, 51, 56, 54, 54, 54, 56, 56, 56, 51, 58, 58, 58, 41, 75, 60, 60, 60, 64, 62, 65, 63, 64, 64, 64, 64, 65, 60, 75, 67, 67, 67, 68, 67, 71, 70, 71, 71, 71, 67, 75, 73, 73, 73, 75, 75, 75, 60, 98, 77, 77, 77, 94, 79, 79, 79, 83, 81, 83, 82, 82, 82, 83, 79, 87, 85, 87, 86, 86, 86, 87, 79, 94, 89, 89, 89, 93, 91, 93, 92, 93, 93, 93, 89, 94, 86, 99, 96, 96, 97, 97, 96, 99, 99, 99]
        System.out.println(Arrays.toString(
                sol.warriors(new int[] {2,2,2},
                             new int[] {0,0,0})));

//        output:[0, 4, 1, 3, 2, 2, 3, 3, 2, 4]
//        output:[[0, 4, 1, 3, 2, 2, 3, 3, 2, 4]]
//        test t = new test(args);
    }
}




//class test{
//    public test(String[] args){
//        Warriors sol = new Warriors();
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
//                    JSONArray arg_ans = (JSONArray) person.get("answer");
//                    int STH[] = new int[arg_str.size()];
//                    int RNG[] = new int[arg_str.size()];
//                    int Answer[] = new int[arg_ans.size()];
//                    int Answer_W[] = new int[arg_ans.size()];
//                    for(int i=0;i<arg_ans.size();i++){
//                        Answer[i]=(Integer.parseInt(arg_ans.get(i).toString()));
//                        if(i<arg_str.size()){
//                            STH[i]=(Integer.parseInt(arg_str.get(i).toString()));
//                            RNG[i]=(Integer.parseInt(arg_rng.get(i).toString()));
//                        }
//                    }
//                    Answer_W = sol.warriors(STH,RNG);
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