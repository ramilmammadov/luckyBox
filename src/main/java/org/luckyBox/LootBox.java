package org.luckyBox;

import java.util.Random;

public class LootBox {
    public static void main(String[] args) {
        int[] gifts = new int[100000];
        Random random = new Random();

        for (int i = 0; i < gifts.length; i++) {
            gifts[i] = random.nextInt(10000) % 10;
        }

        int[] winCounts = new int[10];
        for (int gift : gifts) {
            winCounts[gift]++;
        }

        for (int i = 0; i < winCounts.length; i++) {
            System.out.println("Gift " + i + " won " + winCounts[i] + " times");
        }
    }
}
