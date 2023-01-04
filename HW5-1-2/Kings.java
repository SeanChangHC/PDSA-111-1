import edu.princeton.cs.algs4.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
        if(Strength > o.Strength)
            return -1;
        else if (Strength < o.Strength) {
            return 1;
        }
        else
            return 0;
    }
}
class Kings {
    int[] rtn;
    King[] kg;
    int ksize = 0;
    ArrayList<King> Lst;
    public Kings(int[] strength, int[] range){

        // Given the attributes of each King and output the minimal and maximum
        // index of King can be attacked by each King.
        int[] rtn = new int[strength.length];
        King[] wrs = new King[strength.length];
        ArrayList<King> rmn = new ArrayList<>() ;
        //marginal condition
        for(int r : rtn) {
            r = 0;
        }
        MinPQ<King> pq = new MinPQ<King>();
        Lst = new ArrayList<King>();
//        ArrayList<King> kg = new ArrayList<King>();

        //loop over every element
        for(int i = 0 ; i < strength.length; i++){

            //declare King obj
            int idx = i;
            int stn = strength[i];
            int rng = range[i];
            King now = new King(stn, rng, idx);
            wrs[i] = now;

            //not empty
            if(!Lst.isEmpty()){

                //stack last one > now
                if(Lst.get(Lst.size()-1).Strength > now.Strength){
                    int gap = now.Index - Lst.get(Lst.size()-1).Index;

                    //if in range set leftmost to peek + 1
//                    if(now.Range != 0 && gap <= now.Range)
//
//                    if(now.Range != 0 && gap > now.Range)
                    if(Lst.get(Lst.size()-1).Range == 0 || gap > Lst.get(Lst.size()-1).Range) {
                        if(rtn[i] == 0 ) ksize+=1;
                        rtn[i] = 1;
                    }
                    if(gap <= Lst.get(Lst.size()-1).Range) {
                        rtn[i] = 2;
                    }
                }

                if(Lst.get(Lst.size()-1).Strength <= now.Strength) {
                    //when peek < now
                    while (!Lst.isEmpty() && (Lst.get(Lst.size()-1).Strength < now.Strength)) {
                        King temp = Lst.get(Lst.size()-1);

                        Lst.remove(Lst.size()-1);


                        int gaptemp = now.Index - temp.Index;

                        if (!Lst.isEmpty()) {
                            if((rtn[temp.Index] != 2 && (now.Range < gaptemp || now.Range == 0 ))) {
                                if(rtn[temp.Index] == 0 ) ksize+=1;
                                rtn[temp.Index] = 1;
                            }
                            if(now.Range >= gaptemp) {
                                if(rtn[temp.Index] == 1 ) ksize-=1;
                                rtn[temp.Index] = 0;
                            }
                        }
                        if(Lst.isEmpty() ){
                            if(rtn[i] == 0 ) ksize+=1;
                            rtn[i] = 1;
                            if(rtn[temp.Index] != 2 && (now.Range < gaptemp || now.Range == 0)) {
                                if(rtn[temp.Index] == 0 ) ksize+=1;
                                rtn[temp.Index] = 1;
                            }
                            if(now.Range >= gaptemp) {
                                if(rtn[temp.Index] == 1 ) ksize-=1;
                                rtn[temp.Index] = 0;
                            }

                        }

//                        for(int n = 0 ; n < rmn.size() ; n++){
//                            int gap = temp.Index-rmn.get(n).Index;
//                            if((rmn.get(n).Strength > temp.Strength && gap <= rmn.get(n).Range)) {
//                                if (rtn[temp.Index] == 1) ksize -= 1;
//                                rtn[temp.Index] = 0;
//                                break;
//                            }
//                            else {
//                                if (rtn[temp.Index] == 0) ksize += 1;
//                                rtn[temp.Index] = 1;
//
//                            }
//                        }
//                        rmn.add(temp);


                    }

                    if (!Lst.isEmpty() && Lst.get(Lst.size()-1).Strength > now.Strength) {
                        King temp = Lst.get(Lst.size()-1);
                        int gap = now.Index - temp.Index;
                        if (Lst.get(Lst.size()-1).Range == 0 || gap > Lst.get(Lst.size()-1).Range ) {
                            if(rtn[i] == 0 ) ksize+=1;
                            rtn[i] = 1;
                        }
                        if(gap <= Lst.get(Lst.size()-1).Range) {
                            if(rtn[i] == 1 ) ksize-=1;
                            rtn[i] = 2;
                        }

                    }

                    if (!Lst.isEmpty() && Lst.get(Lst.size()-1).Strength == now.Strength) {
                        King last = Lst.remove(Lst.size()-1);
                        if(!Lst.isEmpty()) {
                            King temp = Lst.get(Lst.size() - 1);
                            int gaptemp = now.Index - temp.Index;
                            if (temp.Range != 0) {
                                if (temp.Range >= gaptemp) {
                                    if (rtn[i] == 1) ksize -= 1;
                                    rtn[i] = 0;
                                }
                                if (temp.Range < gaptemp) {
                                    if (rtn[i] == 0) ksize += 1;
                                    rtn[i] = 1;
                                }

                            }
                            if (temp.Range == 0) {
                                if (rtn[i] == 0) ksize += 1;
                                rtn[i] = 1;
                            }
                        }
                        else {
                            if (rtn[i] == 0) ksize += 1;
                            rtn[i] = 1;
                        }

//                        for(int n = 0 ; n < rmn.size() ; n++){
//                            int gap = last.Index-rmn.get(n).Index;
//                            if((rmn.get(n).Strength > last.Strength && gap <= rmn.get(n).Range)) {
//                                if (rtn[last.Index] == 1) ksize -= 1;
//                                rtn[last.Index] = 0;
//                                break;
//                            }
//                            else {
//                                if (rtn[last.Index] == 0) ksize += 1;
//                                rtn[last.Index] = 1;
//                            }
//                        }
//                        rmn.add(last);

                    }
                }

            }
            if(i== 0 && Lst.isEmpty()){
                ksize += 1;
                rtn[0] = 1;
            }
            Lst.add(now);


        }


        if(!Lst.isEmpty()) {
            for (int i = Lst.size() - 1; i > 0; i--) {
                for (int j = i - 1; j >= 0; j--) {
                    int gap = Lst.get(i).Index - Lst.get(j).Index;
                    if (gap <= Lst.get(j).Range) {
                        if (rtn[Lst.get(i).Index] == 1) ksize -= 1;
                        rtn[Lst.get(i).Index] = 0;
                        break;
                    }
                    if (j == 0 && gap > Lst.get(0).Range) {
                        if (rtn[Lst.get(i).Index] == 0) ksize += 1;
                        rtn[Lst.get(i).Index] = 1;
                    }
                }

            }
        }
//        System.out.println("ksize: "+ksize);
//        for(int i = 0 ; i < rtn.length; i++)
//            System.out.println(rtn[i]);
        kg = new King[ksize];
        int k = 0;
        for(int i = 0 ; i < rtn.length; i++){
            if(rtn[i] == 1){
                kg[k] = wrs[i];
                k++;
            }
        }



//        for(int i = 0 ; i < kg.length; i++)
//            System.out.println(kg[i].Index);
//        System.out.println("-------------");
        MergeX.sort(kg);
//        for(int i = 0 ; i < kg.length; i++)
//            System.out.println(kg[i].Index);


        for(int i = 0 ; i < rtn.length; i++)
            System.out.println(rtn[i]);




//
//        while(!Lst.isEmpty()){
//            King temp = Lst.get(Lst.size()-1);
//            int gapf = strength.length - 1 - temp.Index;
//            int idx = strength.length - 1;
//            if(temp.Index==idx && !Lst.isEmpty()) {
//                Lst.get(Lst.size()-1);
//                Lst.remove(Lst.size()-1);
//                continue;
//            }
//
//            if(temp.Range != 0) {
//                if (gapf <= temp.Range) {
//                    rtn[2 * temp.Index + 1] = strength.length - 1;
//                }
//                else
//                    rtn[2 * temp.Index + 1] = temp.Index + temp.Range;
//            }else{
//                rtn[2*temp.Index + 1] = temp.Index;
//                rtn[2* temp.Index] = temp.Index;
//            }
//            Lst.get(Lst.size()-1);
//        Lst.remove(Lst.size()-1);
//
//        }
//        for(int r : rtn)
//            if(r==1)
//                pq.insert();




    }
    public int[] topKKings(int k) {
        if(k > ksize)
            k = ksize;
        int[] rn = new int[k];

        for(int i = 0 ;i < k ; i++){
            rn[i] = kg[i].Index;
        }


        return rn;
        // complete the code by returning an int[]
        // remember to return the array of indexes in the descending order of strength
    }

    public static void main(String[] args) {
        Kings sol = new Kings(new int[] {90174, 15155, 59151,31717, 89935, 50327, 65126,31246,61081,32777,20283,55336,60056,89796,51831,100000}
                            , new int[] {419521,312855,270348,32563,231412,335780,70565,269696,313360,103831,174784,484293,59941,496455,409412,0});
//        Kings sol = new Kings(new int[] {8236,63248,44441,68653,5701,78759,94682,42636,24690,59397,31532,28521,8784,59080,97617,40427,78296,61882,58605,27089,100000,40221,26880,34558,25428,12703,59601,48585,94978,15367,100000,71882,39832,14928,92025,78704,8809,31830,82786,47108,100000,11380,33620,63824,53839,59996,93602,43171,100000,96123,70127,70485,1177,82587,86006,11747,88907,23917,59359,2252,18560,25030,24859,57966,87482,53274,95268,73727,12600,21709,18140,56382,29554,81528,17403,77987,4566,30298,25984,78970,17425,38166,90174,15155,59151,31717,89935,50327,65126,31246,61081,32777,20283,55336,60056,89796,51831,100000,67416,38643}
//                , new int[] {391278,375036,322556,32888,51903,495551,314997,190601,352469,277837,1637,493313,254075,239450,388067,222107,123504,393624,127132,402477,0,483717,427024,416208,169287,418620,119169,18652,127441,92126,0,329159,300936,261121,489046,31585,408595,410146,201627,76222,0,59535,377867,48318,374873,232438,261272,411271,0,135468,453412,402078,225944,108823,495961,471464,392317,307337,21394,259348,132979,403875,122768,429848,277827,157273,309676,216557,76318,108813,326048,421467,330951,229629,118943,118407,267473,211984,125932,144853,28432,323473,419521,312855,270348,32563,231412,335780,70565,269696,313360,103831,174784,484293,59941,496455,409412,0,360550,399567});
//        System.out.println(Arrays.toString(sol.topKKings(5)));
        //100000, 96123,70127,70485,1177,82587,86006,11747,88907,23917,59359,2252,18560,25030,24859,57966,87482,53274,95268,
        //0, 135468,453412,402078,225944,108823,495961,471464,392317,307337,21394,259348,132979,403875,122768,429848,277827,157273,309676,

        // 1, 2, 4, 5, 7, 10
        // In this case, the kings are [0, 2, 4, 5, 6] (without sorting, only by the order of ascending indices)
        // Output: [2, 5, 0]
        //10101110
//        test t = new test(args);
    }
}

class test{
    public test(String[] args){
        Kings sol;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0])){
            JSONArray all = (JSONArray) jsonParser.parse(reader);
            for(Object CaseInList : all){
                JSONArray a = (JSONArray) CaseInList;
                int q_cnt = 0, wa = 0,ac = 0;
                for (Object o : a) {
                    q_cnt++;
                    JSONObject person = (JSONObject) o;
                    JSONArray arg_str = (JSONArray) person.get("strength");
                    JSONArray arg_rng = (JSONArray) person.get("attack_range");
                    Long arg_k = (Long) person.get("k");
                    JSONArray arg_ans = (JSONArray) person.get("answer");
                    int STH[] = new int[arg_str.size()];
                    int RNG[] = new int[arg_str.size()];
                    int k = Integer.parseInt(arg_k.toString());

                    int Answer[] = new int[arg_ans.size()];
                    int Answer_W[] = new int[arg_ans.size()];
                    for(int i=0;i<arg_ans.size();i++){
                        Answer[i]=(Integer.parseInt(arg_ans.get(i).toString()));
                    }
                    for(int i=0;i<arg_str.size();i++){
                        STH[i]=(Integer.parseInt(arg_str.get(i).toString()));
                        RNG[i]=(Integer.parseInt(arg_rng.get(i).toString()));
                    }
                    sol = new Kings(STH,RNG);
                    Answer_W = sol.topKKings(k);
                    for(int i=0;i<arg_ans.size();i++){
                        if(Answer_W[i]==Answer[i]){
                            if(i==arg_ans.size()-1){
                                System.out.println(q_cnt+": AC");
                            }
                        }else {
                            wa++;
                            System.out.println(q_cnt+": WA");
                            break;
                        }
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
