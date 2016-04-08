package Øving3;

import java.util.ArrayList;

public class PerceptronLearning {
    public static ArrayList<Double> weights = new ArrayList<Double>();
    public static double theta;
    public static double alpha;
    public static ArrayList<Double> answers = new ArrayList<Double>();
    public static void main(String[] args) {

        //AND-eksempel fra s.172:
        theta = 0.2;
        alpha = 0.1;
        weights.add(0,-0.3); weights.add(1,0.4);
        answers.add(0,0.0);answers.add(1,0.0);answers.add(2,0.0);answers.add(3,1.0);
        ArrayList<ArrayList<Double>> epoch_inputs = new ArrayList<ArrayList<Double>>();

        ArrayList<Double> iteration1 = new ArrayList<Double>(); iteration1.add(0,0.0);iteration1.add(1,0.0);
        ArrayList<Double> iteration2 = new ArrayList<Double>(); iteration2.add(0,0.0);iteration2.add(1,1.0);
        ArrayList<Double> iteration3 = new ArrayList<Double>(); iteration3.add(0,1.0);iteration3.add(1,0.0);
        ArrayList<Double> iteration4 = new ArrayList<Double>(); iteration4.add(0,1.0);iteration4.add(1,1.0);

        epoch_inputs.add(0,iteration1);
        epoch_inputs.add(1,iteration2);
        epoch_inputs.add(2,iteration3);
        epoch_inputs.add(3,iteration4);

        //start:
        PerceptronLearning(epoch_inputs,answers);
    }

    public static void PerceptronLearning(ArrayList<ArrayList<Double>> epoch_inputs, ArrayList<Double> Ydi){
        int n = 0;
        ArrayList<ArrayList<Double>> current_weights = new ArrayList<ArrayList<Double>>();
        while (!convergence(n,current_weights)){ //Hvis ikke konvergens, kjør ny epoke
            System.out.println("Epoch " + (n+1) +":");
            for (ArrayList<Double> iteration: epoch_inputs){ //for alle iterasjonene i epoken
                current_weights.add(n, weights);
                run(iteration,weights,answers.get(epoch_inputs.indexOf(iteration)),theta,alpha); //kjør!
                System.out.println(weights);

            }n++;
            System.out.println("");
        }
    }

    public static void run(ArrayList<Double> inputs, ArrayList<Double> weights2, double Yd, double theta, double alpha){
            double Y;
            double variabelnavn = 0;
            for (int i = 0; i < weights2.size(); i++) {
                variabelnavn += (inputs.get(i) * weights2.get(i));
            }
            if((variabelnavn - theta) >= 0) {
                Y = 1;
            } else {
                Y = 0;
            }
            double e = error(Yd,Y);
            for (int p = 0; p < weights2.size() ; p++) {
                //Oppdater vekter:
                double dw = (alpha*inputs.get(p)*e);
                weights.set(p, (Math.round((weights.get(p) + dw) * 100.0) / 100.0));
            }
        }
    public static boolean convergence(int n, ArrayList<ArrayList<Double>> cw) { //skjekker om vektene konvergerer
        if (n < 5) {
            return false;
        } else {
            for (int i = 0; i < n; i++) {
                if (cw.get(n - i).equals(weights)) {
                    return true;
                } else {
                    return false;
                }
            }
    }return false;}

    public static double error(double Yd, double Y){ //regner ut feilen
        return Yd-Y;
    }

}


