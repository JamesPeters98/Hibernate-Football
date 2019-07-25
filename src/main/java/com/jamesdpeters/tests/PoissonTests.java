package com.jamesdpeters.tests;

import com.jamesdpeters.utils.Utils;

public class PoissonTests {

    public static void main(String[] args) {
        double goals = 1.7;
        double minuteGoals = goals/90.0;

        int arrSize = 15;
        int[] goalsAmnt = new int[arrSize];
        int[] minuteGoalsAmnt = new int[arrSize];

        double LIMIT = 100000;
        for(int i = 0; i < LIMIT; i++){
            int goal = Utils.poisson(goals);
            goalsAmnt[goal]++;

            int minGoal = 0;
            for(int z = 0; z < 90; z++){
                minGoal += Utils.poisson(minuteGoals);
            }
            minuteGoalsAmnt[minGoal]++;
        }

        for(int m = 0; m < arrSize; m++){
            double ninety = goalsAmnt[m]/LIMIT;
            double minute = minuteGoalsAmnt[m]/LIMIT;
            System.out.println(m+" goals: 90mins: "+ninety+" 1min: "+minute);
        }
    }
}
