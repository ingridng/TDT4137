package MamdaniMethod;
import java.util.ArrayList;

public class MamdaniMethod {

    //fuzzy-sett med lingvistiske verdier:
    public static ArrayList<FuzzyVariable> fuzzyDistances;
    public static ArrayList<FuzzyVariable> fuzzyDeltas;
    public static ArrayList<FuzzyVariable> fuzzyActions;

    //regner ut "center of gravity":
    public static String cog(){
        ArrayList<Integer> interval= new ArrayList<Integer>();
        double teller = 0.0;
        double nevner = 0.0;
        double myMax = 0.0;
        ArrayList<Integer> FloorIt = new ArrayList<Integer>();
        ArrayList<Integer> SpeedUp = new ArrayList<Integer>();
        ArrayList<Integer> BreakHard = new ArrayList<Integer>();
        ArrayList<Integer> SlowDown = new ArrayList<Integer>();
        ArrayList<Integer> None = new ArrayList<Integer>();

        //Sortere listen etter μ-verdier:
        ArrayList<FuzzyVariable> sorted = new ArrayList<FuzzyVariable>();
        for (FuzzyVariable f: fuzzyActions){
            if(f.getMy()>myMax){sorted.add(0,f); myMax=f.getMy();}
            else {for(FuzzyVariable u: sorted){if(f.getMy()>=u.getMy()){sorted.add(sorted.indexOf(u),f);}
            }if(!sorted.contains(f)){sorted.add(f);}
            }
        }
        //For hver action representert, klipp ut og legg til i teller og nevner:
        for (FuzzyVariable e: sorted){
            if(e.getName().equalsIgnoreCase("BrakeHard")){
                for (int i = -10; i < Math.floor(((e.getMy()-1)/-0.3333)-8); i++) {
                    if(!interval.contains(i)){teller+=i*e.getMy(); nevner+=e.getMy();
                        interval.add(i); BreakHard.add(i);}
                }}
            if(e.getName().equalsIgnoreCase("SlowDown")){
                for (int i = (int) Math.ceil((e.getMy() / 0.3333) - 7); i < Math.floor(((e.getMy()-1)/-0.3333)-4)+1; i++) {
                    if (!interval.contains(i)) {
                        SlowDown.add(i);
                        teller += i * e.getMy();
                        nevner += e.getMy();
                        interval.add(i);
                    }
                }
            }
            if(e.getName().equalsIgnoreCase("None")){
                for (int i = (int) Math.ceil((e.getMy() / 0.3333) - 3); i < Math.floor(((e.getMy()-1)/-0.3333))+1; i++) {
                    if (!interval.contains(i)) {
                        None.add(i);
                        teller += i * e.getMy();
                        nevner += e.getMy();
                        interval.add(i);
                    }
                }
            }
            if(e.getName().equalsIgnoreCase("SpeedUp")){
                for (int i = (int) Math.ceil((e.getMy() / 0.3333) +1); i < Math.floor(((e.getMy()-1)/-0.3333)+4)+1; i++) {
                    if (!interval.contains(i)) {
                        SpeedUp.add(i);
                        teller += i * e.getMy();
                        nevner += e.getMy();
                        interval.add(i);
                    }
                }
            }
            if(e.getName().equalsIgnoreCase("FloorIt")){
                for (int i = (int) Math.ceil((e.getMy() / 0.3333) +5); i < 11; i++) {
                    if (!interval.contains(i)) {
                        FloorIt.add(i);
                        teller += i * e.getMy();
                        nevner += e.getMy();
                        interval.add(i);
                    }
                }
            }
        }
        System.out.println("Solution:");
        System.out.println("Z = " + teller/nevner);
        int solution = Integer.valueOf((int) (teller / nevner));

        //returnerer handlingen som tilsvarer μ-verdien:
        if(FloorIt.contains(solution)){return "FloorIt";}
        else if(SlowDown.contains(solution)){return "SlowDown";}
        else if(None.contains(solution)){return "None";}
        else if(SpeedUp.contains(solution)){return "SpeedUp";}
        else if(BreakHard.contains(solution)){return "BreakHard";}
        else{return null;}
    }

    //løkken gjennom delta- og distanse-verdiene som er representert med gitt x1 og y1:
    public static void rules(){
        double ngf = 0.0;
        double ng = 0.0;
        for(FuzzyVariable dis: fuzzyDistances){
            for (FuzzyVariable del: fuzzyDeltas){
                if(del.getName().equalsIgnoreCase("Growing")){ng = 1-del.getMy();}
                if(del.getName().equalsIgnoreCase("GrowingFast")){ngf = 1-del.getMy();}
                if(dis.getName().equalsIgnoreCase("Small") && del.getName().equalsIgnoreCase("Growing")){fuzzyActions.add(new FuzzyVariable("None",Math.min(dis.getMy(),del.getMy())));}
                if(dis.getName().equalsIgnoreCase("Small") && del.getName().equalsIgnoreCase("Stable")){fuzzyActions.add(new FuzzyVariable("SlowDown",Math.min(dis.getMy(),del.getMy())));}
                if(dis.getName().equalsIgnoreCase("Perfect") && del.getName().equalsIgnoreCase("Growing")){fuzzyActions.add(new FuzzyVariable("SpeedUp",Math.min(dis.getMy(),del.getMy())));}
                if(dis.getName().equalsIgnoreCase("VerySmall")){fuzzyActions.add(new FuzzyVariable("BreakHard",dis.getMy()));}
                if(dis.getName().equalsIgnoreCase("VeryBig")){fuzzyActions.add(new FuzzyVariable(("FLoorIt"),Math.min(ng,ngf)));}
            }
        }
    }

    public static void distance(double x1){
        //VerySmall:
        if(x1<=2.75){
            if(x1>=1.25){fuzzyDistances.add(new FuzzyVariable("VerySmall", (1 - (0.6667 * (x1 - 1.25)))));}
            else{fuzzyDistances.add(new FuzzyVariable("VerySmall", 1));}}
        //Small:
        if (x1>=1.75 && x1<=4.75){
            if(x1>=3.25){fuzzyDistances.add(new FuzzyVariable("Small",1-(0.6667*(x1-3.25))));}
            else{fuzzyDistances.add(new FuzzyVariable("Small", (x1 - 1.75) * (0.6667)));}}
        //Perfect:
        if (x1>=3.75 && x1<=6.75){
            if(x1>=5.25){fuzzyDistances.add(new FuzzyVariable("Perfect",1-(0.6667*(x1-5.25))));}
            else{fuzzyDistances.add(new FuzzyVariable("Perfect",0.6667*(x1-3.75)));}}
        //Big
        if (x1>=5.75 && x1<=8.75){
            if(x1>=7.25){fuzzyDistances.add(new FuzzyVariable("Big",1-(0.6667*(x1-7.25))));}
            else{fuzzyDistances.add(new FuzzyVariable("Big",0.6667*(x1-5.75)));}}
        //VeryBig
        if(x1>=7.75){
            if(x1<=9.25){fuzzyDistances.add(new FuzzyVariable("VeryBig", ((0.6667 * (x1 - 7.75)))));}
            else{fuzzyDistances.add(new FuzzyVariable("VeryBig", 1));}}
    }

    public static void delta(double y1){
        //ShrinkingFast:
        if(y1<=-2.25){
            if(y1>=-3.75){fuzzyDeltas.add(new FuzzyVariable("ShrinkingFast", (1 - (0.6667 * (y1 + 3.75)))));}
            else{fuzzyDistances.add(new FuzzyVariable("ShrinkingFast", 1));}}
        //Shrinking:
        if (y1>=-3.25 && y1<=-0.25){
            if(y1>=-1.75){fuzzyDeltas.add(new FuzzyVariable("Shrinking",1-(0.6667*(y1+1.75))));}
            else{fuzzyDeltas.add(new FuzzyVariable("Shrinking", (y1 + 3.25) * (0.6667)));}}
        //Stable:
        if (y1>=-1.25 && y1<=1.75){
            if(y1>=0.25){fuzzyDeltas.add(new FuzzyVariable("Stable",1-(0.6667*(y1-0.25))));}
            else{fuzzyDeltas.add(new FuzzyVariable("Stable", (y1 + 1.25) * (0.6667)));}}
        //Growing:
        if (y1>=0.75 && y1<=3.75){
            if(y1>=2.25){fuzzyDeltas.add(new FuzzyVariable("Growing",1-(0.6667*(y1-2.25))));}
            else{fuzzyDeltas.add(new FuzzyVariable("Growing", (y1 - 0.75) * (0.6667)));}}
        //GrowingFast:
        if (y1>=2.75){
            if(y1<=4.25){fuzzyDeltas.add(new FuzzyVariable("GrowingFast", ((0.6667 * (y1 - 2.75)))));}
            else{fuzzyDeltas.add(new FuzzyVariable("GrowingFast", 1));}}

    }

    public static void main(String[] args) {
        fuzzyDistances = new ArrayList<FuzzyVariable>();
        fuzzyDeltas =new ArrayList<FuzzyVariable>();
        fuzzyActions = new ArrayList<FuzzyVariable>();
        double x1 = 0.9;
        double y1 = 1.3;
        delta(y1);
        distance(x1);
        rules();
        System.out.println("Action: "+cog());
    }

    public static class FuzzyVariable{
        String FuzzyVariable;
        double my;
        public FuzzyVariable(String s,double my){
            this.FuzzyVariable=s;
            this.my=my;
        }
        public String getName(){
            return FuzzyVariable;
        }
        public double getMy(){
            return my;}
    }
}


