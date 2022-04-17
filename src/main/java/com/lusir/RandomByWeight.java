package com.lusir;

import java.util.Arrays;
import java.util.Random;

public class RandomByWeight {

    private int[] preSums;

    public RandomByWeight(int[] weights) {
        preSums = new int[weights.length];
        for (int i = 0; i < weights.length; i++) {
            preSums[i] += weights[i];
        }
    }

    public int pickIndex() {
        Random random = new Random();
        int allSum = preSums[preSums.length - 1];
        int r = random.nextInt(allSum) + 1;
        return Arrays.binarySearch(preSums, r);
    }
}
