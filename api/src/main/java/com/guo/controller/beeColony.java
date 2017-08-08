package com.guo.controller;

/**
 * Created by jack on 2017/7/28.
 */
public class beeColony {

    int NP = 20;
    int FoodNumber = NP / 2;
    int limit = 100;
    int maxCycle = 2500;


    int D = 100;
    double lb = -5.12;
    double ub = 5.12;
    int runtime = 30;

    int dizi1[] = new int[10];
    double Foods[][] = new double[FoodNumber][D];
    double f[] = new double[FoodNumber];
    double fitness[] = new double[FoodNumber];
    double trial[] = new double[FoodNumber];
    double prob[] = new double[FoodNumber];
    double solution[] = new double[D];

    double ObjValSol;
    double FitnessSol;
    int neighbour, param2change;

    double GlobalMin;
    double GlobalParams[] = new double[D];
    double GlobalMins[] = new double[runtime];

    double r;

    double CalculateFitness(double fun) {
        double result = 0;
        if (fun >= 0) {
            result = 1 / (fun + 1);
        } else {

            result = 1 + Math.abs(fun);
        }
        return result;
    }
    void MemorizeBestSource() {
        int i, j;

        for (i = 0; i < FoodNumber; i++) {
            if (f[i] < GlobalMin) {
                GlobalMin = f[i];
                for (j = 0; j < D; j++)
                    GlobalParams[j] = Foods[i][j];
            }
        }
    }
    void init(int index) {
        int j;
        for (j = 0; j < D; j++) {
            r = ((double) Math.random() * 32767 / ((double) 32767 + (double) (1)));
            Foods[index][j] = r * (ub - lb) + lb;
            solution[j] = Foods[index][j];
        }
        f[index] = calculateFunction(solution);
        fitness[index] = CalculateFitness(f[index]);
        trial[index] = 0;
    }
    void initial() {
        int i;
        for (i = 0; i < FoodNumber; i++) {
            init(i);
        }
        GlobalMin = f[0];
        for (i = 0; i < D; i++)
            GlobalParams[i] = Foods[0][i];
    }

    void SendEmployedBees() {
        int i, j;

        for (i = 0; i < FoodNumber; i++) {

            r = ((double) Math.random() * 32767 / ((double) (32767) + (double) (1)));
            param2change = (int) (r * D);


            r = ((double) Math.random() * 32767 / ((double) (32767) + (double) (1)));
            neighbour = (int) (r * FoodNumber);

            for (j = 0; j < D; j++)
                solution[j] = Foods[i][j];
            r = ((double) Math.random() * 32767 / ((double) (32767) + (double) (1)));
            solution[param2change] = Foods[i][param2change] + (Foods[i][param2change] - Foods[neighbour][param2change]) * (r - 0.5) * 2;

            if (solution[param2change] < lb)
                solution[param2change] = lb;
            if (solution[param2change] > ub)
                solution[param2change] = ub;
            ObjValSol = calculateFunction(solution);
            FitnessSol = CalculateFitness(ObjValSol);

            if (FitnessSol > fitness[i]) {

                trial[i] = 0;
                for (j = 0; j < D; j++)
                    Foods[i][j] = solution[j];
                f[i] = ObjValSol;
                fitness[i] = FitnessSol;
            } else {
                trial[i] = trial[i] + 1;
            }

        }
    }
    void CalculateProbabilities() {
        int i;
        double maxfit;
        maxfit = fitness[0];
        for (i = 1; i < FoodNumber; i++) {
            if (fitness[i] > maxfit)
                maxfit = fitness[i];
        }

        for (i = 0; i < FoodNumber; i++) {
            prob[i] = (0.9 * (fitness[i] / maxfit)) + 0.1;
        }

    }

    void SendOnlookerBees() {

        int i, j, t;
        i = 0;
        t = 0;
	  /*onlooker Bee Phase*/
        while (t < FoodNumber) {

            r = ((double) Math.random() * 32767 / ((double) (32767) + (double) (1)));
            if (r < prob[i]) {
                t++;

                r = ((double) Math.random() * 32767 / ((double) (32767) + (double) (1)));
                param2change = (int) (r * D);


                r = ((double) Math.random() * 32767 / ((double) (32767) + (double) (1)));
                neighbour = (int) (r * FoodNumber);


                while (neighbour == i) {

                    r = ((double) Math.random() * 32767 / ((double) (32767) + (double) (1)));
                    neighbour = (int) (r * FoodNumber);
                }
                for (j = 0; j < D; j++)
                    solution[j] = Foods[i][j];

                r = ((double) Math.random() * 32767 / ((double) (32767) + (double) (1)));
                solution[param2change] = Foods[i][param2change] + (Foods[i][param2change] - Foods[neighbour][param2change]) * (r - 0.5) * 2;

                if (solution[param2change] < lb)
                    solution[param2change] = lb;
                if (solution[param2change] > ub)
                    solution[param2change] = ub;
                ObjValSol = calculateFunction(solution);
                FitnessSol = CalculateFitness(ObjValSol);

                if (FitnessSol > fitness[i]) {
	                 trial[i] = 0;
                    for (j = 0; j < D; j++)
                        Foods[i][j] = solution[j];
                    f[i] = ObjValSol;
                    fitness[i] = FitnessSol;
                } else {
                    trial[i] = trial[i] + 1;
                }
            } /*if */
            i++;
            if (i == FoodNumber - 1)
                i = 0;
        }/*while*/


    }

     void SendScoutBees() {
        int maxtrialindex, i;
        maxtrialindex = 0;
        for (i = 1; i < FoodNumber; i++) {
            if (trial[i] > trial[maxtrialindex])
                maxtrialindex = i;
        }
        if (trial[maxtrialindex] >= limit) {
            init(maxtrialindex);
        }
    }
    double calculateFunction(double sol[]) {
        return Rastrigin(sol);
    }

    double sphere(double sol[]) {
        int j;
        double top = 0;
        for (j = 0; j < D; j++) {
            top = top + sol[j] * sol[j];
        }
        return top;
    }
    double Rosenbrock(double sol[]) {
        int j;
        double top = 0;
        for (j = 0; j < D - 1; j++) {
            top = top + 100 * Math.pow((sol[j + 1] - Math.pow((sol[j]), (double) 2)), (double) 2) + Math.pow((sol[j] - 1), (double) 2);
        }
        return top;
    }
    double Griewank(double sol[]) {
        int j;
        double top1, top2, top;
        top = 0;
        top1 = 0;
        top2 = 1;
        for (j = 0; j < D; j++) {
            top1 = top1 + Math.pow((sol[j]), (double) 2);
            top2 = top2 * Math.cos((((sol[j]) / Math.sqrt((double) (j + 1))) * Math.PI) / 180);

        }
        top = (1 / (double) 4000) * top1 - top2 + 1;
        return top;
    }
    double Rastrigin(double sol[]) {
        int j;
        double top = 0;

        for (j = 0; j < D; j++) {
            top = top + (Math.pow(sol[j], (double) 2) - 10 * Math.cos(2 * Math.PI * sol[j]) + 10);
        }
        return top;
    }
}
