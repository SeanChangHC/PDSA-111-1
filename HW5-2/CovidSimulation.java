
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import java.util.HashMap;
import edu.princeton.cs.algs4.*;

class City {
    private  int people;
    private int Date = 0;
    private boolean isInfected =false;
    private int recoveryDate = this.Date;
    private int firstInfectedDay = 0;


    //constructor
    public City(int people){
        this.people = people;

    }
    public void virusAttack(int citydate) {
        if(this.recoveryDate <= citydate){
            recover(citydate);
        }
        this.Date = citydate;
        //city infected
        if(isInfected){

        }
        //city not infected
        else {
            this.firstInfectedDay = citydate;
            this.recoveryDate = this.Date + 4;
            this.isInfected = true;
        }
        

        
    }


    public void recover(int date){
        isInfected = false;
        this.Date = date;
        this.recoveryDate = 0;
        this.firstInfectedDay = 0;
    }
    public void travelerIn(int date,int rDate, int p, int isif){
        if(this.recoveryDate <= date){
            recover(date);
        }
        this.people += p;
        this.Date = date;

        //infected traveler
        if(isif == 1){
            //I'm infected
            if(this.isInfected){
                //rDate > this.recoveryDate
                if(rDate > this.recoveryDate){
                    this.recoveryDate = rDate;
                    //rDate - firstInfectedDay > 7
                    if((this.recoveryDate - this.firstInfectedDay )> 7){
                        this.recoveryDate = this.firstInfectedDay + 7;
                    }
                    //rDate - firstInfectedDay <= 7
//                    else{
//
//                    }
                }
                //rDate <= this.recoveryDate
                else{
                    this.recoveryDate = this.recoveryDate;
                }

            }
            //I'm not infected
            else {
                this.firstInfectedDay = date;
                this.recoveryDate = this.Date + 4;
                this.isInfected = true;

            }


        }
        //not infected traveler
//        else{
//
//        }



    }
    public void travelerOut(int p , int date){
        if(this.recoveryDate <= date){
            recover(date);
        }
        this.people += p;
        this.Date = date;
    }
    
//    public  void setRecoveryDate(int date){
//        this.recoveryDate = date;
//    }


    public boolean getisInfected(){
        return  isInfected;
    }
    
    public  int getPeople(){
        return  this.people;
    }

    public int getRecoveryDate(){
        return recoveryDate;
    }
    
//    public int getDate(){
//        return this.Date;
//    }





}
class  Event implements Comparable<Event>{
    public int people;
    public int tocity;
    public int date;
    public int fromcity;
    public int traveleridx;

    //virus attack
    public Event(int p, int a,  int dA){
        this.people = p;
        this.tocity = a;
        this.date = dA;
    }

    //traveler out
    public Event(int numberOfTraveller, int fromCity, int dateOfDeparture, int travelercnt) {
        this.people = numberOfTraveller;
        this.fromcity = fromCity;
        this.date = dateOfDeparture;
        this.traveleridx = travelercnt;

    }

    //traveler in
    public Event(int numberOfTraveller, int fromCity, int toCity, int dateOfArrival, int travelercnt) {
        this.people = numberOfTraveller;
        this.fromcity = fromCity;
        this.tocity = toCity;
        this.date = dateOfArrival;
        this.traveleridx = travelercnt;
    }



//    boolean isValid(){
//        return true;
//    }

    @Override
    public int compareTo(Event e) {

        if(this.date < e.date)
            return -1;
        else if(this.date > e.date)
            return 1;
        else{
            if(this.people == 0)
                return -1;
            else {
                if(this.people > e.people)
                    return -1;
                else if (this.people < e.people) {
                    return 1;
                }
            }

        }
            return 0;
    }
}
class CovidSimulation {
    MinPQ<Event> pq = new MinPQ<>();
    HashMap<Integer, Integer> recoverDateRecord = new HashMap<Integer, Integer>();
    City[] cs;
    int travelercnt = 0;


    public CovidSimulation(int[] Num_Of_Citizen) {
        cs = new City[Num_Of_Citizen.length];
        for(int i = 0 ; i < Num_Of_Citizen.length; i++){
            cs[i] = new City(Num_Of_Citizen[i]);
        }

    }
    public int CityWithTheMostPatient(int date){
        int thatCity = 0;
        int firstday = 1;


        while (!pq.isEmpty() && pq.min().date <= date){
            Event e = pq.delMin();
            int tocity = e.tocity;
            int fromcity = e.fromcity;
            int citydate = e.date;
            int people = e.people;
            int traveleridx = e.traveleridx;

            //update

            //virus attack
            if(people == 0) {
                cs[tocity].virusAttack(citydate);
            }
            else {
                if(people < 0) {
                    //traveller out
                    cs[fromcity].travelerOut(people,citydate);
                    recoverDateRecord.put(traveleridx,cs[fromcity].getRecoveryDate());
                }
                else {
                    //infected traveler in
                    if (recoverDateRecord.get(traveleridx) > citydate) {
                        cs[tocity].travelerIn(citydate,recoverDateRecord.get(traveleridx), people,1);
                    }
                    //not infected traveler in
                    else {
                        cs[tocity].travelerIn(citydate,recoverDateRecord.get(traveleridx), people,0);
                    }
                }

            }

        }


        //find the most patient city
        for(int i = 0; i < cs.length ; i++){

//            System.out.println("city: " + i + ", date: "+ cs[i].getDate()+", people: "+cs[i].getPeople() + ", recoverDate: "+cs[i].getRecoveryDate() + ", isinfected: "+ cs[i].getisInfected());
            //i is infected
            if(cs[i].getisInfected() && cs[i].getRecoveryDate() > date){
                // that city is infected
                if(cs[thatCity].getisInfected() && cs[thatCity].getRecoveryDate() > date){
                    if(cs[i].getPeople() > cs[thatCity].getPeople()){
                        thatCity = i;
                    }
                    else if(cs[i].getPeople() < cs[thatCity].getPeople()){
                        thatCity = thatCity;
                    }
                    else {
                        thatCity = Math.max(i,thatCity);
                    }
                }
                //that city isn't infected
                else {
                    thatCity = i;

                }
            }
            //i isn't infected
//            else{
//
//            }



        }
        if(cs[thatCity].getisInfected() && cs[thatCity].getRecoveryDate() > date)
            return  thatCity;
        else
            return -1;


    }
    public void virusAttackPlan(int city, int date){
        pq.insert(new Event(0,city,date));

    }
    public void TravelPlan(int NumberOfTraveller, int FromCity, int ToCity, int DateOfDeparture, int DateOfArrival){
        pq.insert(new Event(-NumberOfTraveller,FromCity, DateOfDeparture, travelercnt));
        pq.insert(new Event(NumberOfTraveller,FromCity, ToCity, DateOfArrival, travelercnt));
        travelercnt ++;

    }

    public static void main(String[] args){
//        CovidSimulation sol = new CovidSimulation(new int[] {10, 100, 15, 25, 10, 13});
//
//
//        sol.virusAttackPlan(0, 1);
//        sol.virusAttackPlan(4, 3);
//        sol.TravelPlan(3, 0, 3, 3, 4);
//        sol.TravelPlan(3, 4, 0, 3, 4);


//        System.out.println(sol.CityWithTheMostPatient(2));
////        // output = 0
////
//        sol.virusAttackPlan(5, 5);
//        sol.TravelPlan(1, 5, 0, 5, 6);
//
//        System.out.println(sol.CityWithTheMostPatient(4));
////        // output = 3
//        System.out.println(sol.CityWithTheMostPatient(8));
//         output = 5

//                test t = new test(args);

//-------------------------------------------------------------------------------------------
        //----virus and recovery test----
        //4 days recover for attack
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //2： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //3： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //4： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //5： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //pass
        //4 days recover for in coming infected traveller.
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
//        sol.TravelPlan(1, 1, 2, 2, 3);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //2： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //3： MostPatient: 2
        //0, 5, 7, 0, 0,  |  5, 4, 6, 5, 5,
        //
        //4： MostPatient: 2
        //0, 5, 7, 0, 0,  |  5, 4, 6, 5, 5,
        //
        //5： MostPatient: 2
        //0, 0, 7, 0, 0,  |  5, 4, 6, 5, 5,
        //
        //6： MostPatient: 2
        //0, 0, 7, 0, 0,  |  5, 4, 6, 5, 5,
        //
        //7： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 4, 6, 5, 5,
        //pass
        //infected city: in coming with more recent virus
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
//        sol.virusAttackPlan(2, 2);
//        sol.TravelPlan(1, 2, 1, 2, 3);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //2： MostPatient: 1
        //0, 5, 6, 0, 0,  |  5, 5, 4, 5, 5,
        //
        //3： MostPatient: 1
        //0, 6, 6, 0, 0,  |  5, 6, 4, 5, 5,
        //
        //4： MostPatient: 1
        //0, 6, 6, 0, 0,  |  5, 6, 4, 5, 5,
        //
        //5： MostPatient: 1
        //0, 6, 6, 0, 0,  |  5, 6, 4, 5, 5,
        //
        //6： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 6, 4, 5, 5,
        //pass
        //infected city: in coming less recent
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
//        sol.virusAttackPlan(2, 2);
//        sol.TravelPlan(1, 1, 2, 2, 3);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //2： MostPatient: 2
        //0, 5, 6, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //3： MostPatient: 2
        //0, 5, 6, 0, 0,  |  5, 4, 6, 5, 5,
        //
        //4： MostPatient: 2
        //0, 5, 6, 0, 0,  |  5, 4, 6, 5, 5,
        //
        //5： MostPatient: 2
        //0, 0, 6, 0, 0,  |  5, 4, 6, 5, 5,
        //
        //6： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 4, 6, 5, 5,
        //pass
        //infected city: in coming same recovery
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
//        sol.virusAttackPlan(2, 1);
//        sol.TravelPlan(1, 1, 2, 2, 3);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 2
        //0, 5, 5, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //2： MostPatient: 2
        //0, 5, 5, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //3： MostPatient: 2
        //0, 5, 5, 0, 0,  |  5, 4, 6, 5, 5,
        //
        //4： MostPatient: 2
        //0, 5, 5, 0, 0,  |  5, 4, 6, 5, 5,
        //
        //5： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 4, 6, 5, 5,
        //pass
        //infected city: 7day max recovery
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
//        sol.virusAttackPlan(2, 3);
//        sol.virusAttackPlan(3, 5);
//        sol.TravelPlan(1, 2, 1, 3, 4);
//        sol.TravelPlan(1, 3, 1, 5, 6);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //2： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //3： MostPatient: 1
        //0, 5, 7, 0, 0,  |  5, 5, 4, 5, 5,
        //
        //4： MostPatient: 1
        //0, 7, 7, 0, 0,  |  5, 6, 4, 5, 5,
        //
        //5： MostPatient: 1
        //0, 7, 7, 9, 0,  |  5, 6, 4, 4, 5,
        //
        //6： MostPatient: 1
        //0, 8, 7, 9, 0,  |  5, 7, 4, 4, 5,
        //
        //7： MostPatient: 1
        //0, 8, 0, 9, 0,  |  5, 7, 4, 4, 5,
        //
        //8： MostPatient: 3
        //0, 0, 0, 9, 0,  |  5, 7, 4, 4, 5,
        //
        //9： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 7, 4, 4, 5,
        //pass
        //infected city: 8th day infected (infected after recovery)
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
//        sol.virusAttackPlan(2, 3);
//        sol.virusAttackPlan(3, 5);
//        sol.virusAttackPlan(1, 8);
//        sol.TravelPlan(1, 2, 1, 3, 4);
//        sol.TravelPlan(1, 3, 1, 5, 6);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //2： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //3： MostPatient: 1
        //0, 5, 7, 0, 0,  |  5, 5, 4, 5, 5,
        //
        //4： MostPatient: 1
        //0, 7, 7, 0, 0,  |  5, 6, 4, 5, 5,
        //
        //5： MostPatient: 1
        //0, 7, 7, 9, 0,  |  5, 6, 4, 4, 5,
        //
        //6： MostPatient: 1
        //0, 8, 7, 9, 0,  |  5, 7, 4, 4, 5,
        //
        //7： MostPatient: 1
        //0, 8, 0, 9, 0,  |  5, 7, 4, 4, 5,
        //
        //8： MostPatient: 1
        //0, 12, 0, 9, 0,  |  5, 7, 4, 4, 5,
        //
        //9： MostPatient: 1
        //0, 12, 0, 0, 0,  |  5, 7, 4, 4, 5,
        //
        //10： MostPatient: 1
        //0, 12, 0, 0, 0,  |  5, 7, 4, 4, 5,
        //
        //11： MostPatient: 1
        //0, 12, 0, 0, 0,  |  5, 7, 4, 4, 5,
        //
        //12： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 7, 4, 4, 5,
        //pass.
        //virus attack infected city, no effect test.
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
//        for(int i=1;i<5;i++){
//            sol.virusAttackPlan(1, i);
//        }
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //2： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //3： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //4： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //5： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //pass

        //----travel test----
        //infected before departure.
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
//        sol.virusAttackPlan(2, 2);
//        sol.TravelPlan(1, 2, 1, 2, 3);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //2： MostPatient: 1
        //0, 5, 6, 0, 0,  |  5, 5, 4, 5, 5,
        //
        //3： MostPatient: 1
        //0, 6, 6, 0, 0,  |  5, 6, 4, 5, 5,
        //
        //4： MostPatient: 1
        //0, 6, 6, 0, 0,  |  5, 6, 4, 5, 5,
        //
        //5： MostPatient: 1
        //0, 6, 6, 0, 0,  |  5, 6, 4, 5, 5,
        //
        //6： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 6, 4, 5, 5,
        //pass
        //Recover before arrival
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
//        sol.TravelPlan(1, 1, 2, 1, 5);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //2： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //3： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //4： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //5： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 4, 6, 5, 5,
        //pass
        //traveller not part of any city
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
//        sol.TravelPlan(1, 1, 2, 1, 5);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //2： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //3： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //4： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //5： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 4, 6, 5, 5,
        //pass
        //travel after recover
//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(1, 1);
//        sol.TravelPlan(1, 1, 2, 5, 6);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //2： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //3： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //4： MostPatient: 1
        //0, 5, 0, 0, 0,  |  5, 5, 5, 5, 5,
        //
        //5： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 4, 5, 5, 5,
        //
        //6： MostPatient: -1
        //0, 0, 0, 0, 0,  |  5, 4, 6, 5, 5,
        //MostPatient:
//        CovidSimulation sol = new CovidSimulation(new int[] {11,9, 8, 20, 1});
//        sol.virusAttackPlan(1, 1);
//        sol.virusAttackPlan(2, 2);
//        sol.virusAttackPlan(3, 3);
//        sol.virusAttackPlan(4, 4);
//        sol.virusAttackPlan(0, 5);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 1
        //0, 5, 0, 0, 0,  |  11, 9, 8, 20, 1,
        //
        //2： MostPatient: 1
        //0, 5, 6, 0, 0,  |  11, 9, 8, 20, 1,
        //
        //3： MostPatient: 3
        //0, 5, 6, 7, 0,  |  11, 9, 8, 20, 1,
        //
        //4： MostPatient: 3
        //0, 5, 6, 7, 8,  |  11, 9, 8, 20, 1,
        //
        //5： MostPatient: 3
        //9, 0, 6, 7, 8,  |  11, 9, 8, 20, 1,
        //
        //6： MostPatient: 3
        //9, 0, 0, 7, 8,  |  11, 9, 8, 20, 1,
        //
        //7： MostPatient: 0
        //9, 0, 0, 0, 8,  |  11, 9, 8, 20, 1,
        //
        //8： MostPatient: 0
        //9, 0, 0, 0, 0,  |  11, 9, 8, 20, 1,
        //
        //9： MostPatient: -1
        //0, 0, 0, 0, 0,  |  11, 9, 8, 20, 1,
        //pass
        //MostPatient multiple same most patient:
//        CovidSimulation sol = new CovidSimulation(new int[] {11,11, 11, 11, 11});
//        sol.virusAttackPlan(0, 1);
//        sol.virusAttackPlan(1, 2);
//        sol.virusAttackPlan(2, 3);
//        sol.virusAttackPlan(3, 4);
//        sol.virusAttackPlan(4, 5);
        //MaxRecoveryDate     |     NumOfCitizen
        //1： MostPatient: 0
        //5, 0, 0, 0, 0,  |  11, 11, 11, 11, 11,
        //
        //2： MostPatient: 1
        //5, 6, 0, 0, 0,  |  11, 11, 11, 11, 11,
        //
        //3： MostPatient: 2
        //5, 6, 7, 0, 0,  |  11, 11, 11, 11, 11,
        //
        //4： MostPatient: 3
        //5, 6, 7, 8, 0,  |  11, 11, 11, 11, 11,
        //
        //5： MostPatient: 4
        //0, 6, 7, 8, 9,  |  11, 11, 11, 11, 11,
        //
        //6： MostPatient: 4
        //0, 0, 7, 8, 9,  |  11, 11, 11, 11, 11,
        //
        //7： MostPatient: 4
        //0, 0, 0, 8, 9,  |  11, 11, 11, 11, 11,
        //
        //8： MostPatient: 4
        //0, 0, 0, 0, 9,  |  11, 11, 11, 11, 11,
        //
        //9： MostPatient: -1
        //0, 0, 0, 0, 0,  |  11, 11, 11, 11, 11,
        //pass
//        System.out.println(sol.CityWithTheMostPatient(1));
//        System.out.println(sol.CityWithTheMostPatient(2));
//        System.out.println(sol.CityWithTheMostPatient(3));
//        System.out.println(sol.CityWithTheMostPatient(4));
//        System.out.println(sol.CityWithTheMostPatient(5));
//        System.out.println(sol.CityWithTheMostPatient(6));
//        System.out.println(sol.CityWithTheMostPatient(7));
//        System.out.println(sol.CityWithTheMostPatient(8));
//        System.out.println(sol.CityWithTheMostPatient(9));
//        System.out.println(sol.CityWithTheMostPatient(10));
//        System.out.println(sol.CityWithTheMostPatient(11));
//        System.out.println(sol.CityWithTheMostPatient(12));

    }
}
//class test {
//    public test(String[] args) {
//        CovidSimulation g;
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(args[0])){
//            JSONArray all = (JSONArray) jsonParser.parse(reader);
//            int waSize = 0;
//            int count = 0;
//            for(Object CaseInList : all){
//                JSONArray a = (JSONArray) CaseInList;
//                //Board Setup
//                JSONObject argsSetting = (JSONObject) a.get(0);
//                a.remove(0);
//
//                JSONArray argSettingArr = (JSONArray) argsSetting.get("args");
//                int citySetting[] = new int[argSettingArr.size()];
//                for(int i=0;i<argSettingArr.size();i++){
//                    citySetting[i]=(Integer.parseInt(argSettingArr.get(i).toString()));
//                }
//                g = new CovidSimulation(citySetting);
//
//                for (Object o : a)
//                {
//                    JSONObject person = (JSONObject) o;
//                    String func =  person.get("func").toString();
//                    JSONArray arg = (JSONArray) person.get("args");
//
//                    switch(func){
//                        case "virusPlan":
//                            g.virusAttackPlan(Integer.parseInt(arg.get(0).toString()),
//                                    Integer.parseInt(arg.get(1).toString()));
//                            break;
//                        case "TravelPlan":
//                            g.TravelPlan(Integer.parseInt(arg.get(0).toString()),Integer.parseInt(arg.get(1).toString()),Integer.parseInt(arg.get(2).toString()),
//                                    Integer.parseInt(arg.get(3).toString()),Integer.parseInt(arg.get(4).toString()));
//                            break;
//
//                        case "CityMax":
//                            count++;
//                            int ans_sol = g.CityWithTheMostPatient(Integer.parseInt(arg.get(0).toString()));
//                            Long answer = (Long) person.get("answer");
//                            int ans = Integer.parseInt(answer.toString());
//                            if(ans_sol==ans){
//                                System.out.println(count+": AC");
//                            }else{
//                                waSize++;
//                                System.out.println(count+": WA");
//                            }
//                    }
//
//                }
//            }
//            System.out.println("Score: " + (count-waSize) + " / " + count + " ");
//        }catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
