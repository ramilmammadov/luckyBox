package org.luckyBox;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SlotMachine {
    private static final int REELS_NUMBER = 3;
    private static final int COLUMNS_NUMBER = 5;
    private static final int SYMBOLS_NUMBER = 16;
    private static final int SCATTER_SYMBOL = 14;
    private static final int JACKPOT_SYMBOL = 15;

    private static final double[] SYMBOL_WIN_RATES = {
            0.01, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.1, 0.1,
            0.1, 0.1, 0.1, 0.1, 0.2, 0.05, 0.01
    };

    public static void main(String[] args) {
        int[][] reels = new int[REELS_NUMBER][COLUMNS_NUMBER];
        Random random = new Random();

        for (int i = 0; i < reels.length; i++) {
            for (int j = 0; j < reels[i].length; j++) {
                reels[i][j] = random.nextInt(SYMBOLS_NUMBER);
            }
        }

        int win = 0;
        int jackpot = 100000;
        int bet = 1;

        for (int i = 0; i < 1000000; i++) {
            int[] symbols = getSymbols(reels);

            if (isJackpot(symbols)) {
                win += jackpot * bet;
            } else if (isScatter(symbols)) {
                int scatterCount = getScatterCount(symbols);
                win += bet * scatterCount * 10;
            } else {
                win += bet * getSymbolWin(symbols);
            }

            if (win > 0) {
                System.out.println("Won $" + win);
                win = 0;
            }

        }

        System.out.println("Total win: $" + win);
        System.out.println("Total spins: " + 1000000);
        System.out.println("RTP: " + (win / 1000000) * 100 + "%");
    }

    private static int[] getSymbols(int[][] reels) {
        int[] symbols = new int[REELS_NUMBER];

        for (int i = 0; i < symbols.length; i++) {
            symbols[i] = reels[i][ThreadLocalRandom.current().nextInt(COLUMNS_NUMBER)];
        }

        return symbols;
    }


    private static boolean isJackpot(int[] symbols) {
        return symbols[0] == JACKPOT_SYMBOL && symbols[1] == JACKPOT_SYMBOL
                && symbols[2] == JACKPOT_SYMBOL;
    }

    private static boolean isScatter(int[] symbols) {
        return symbols[0] == SCATTER_SYMBOL && symbols[1] == SCATTER_SYMBOL
                || symbols[1] == SCATTER_SYMBOL && symbols[2] == SCATTER_SYMBOL
                || symbols[2] == SCATTER_SYMBOL && symbols[0] == SCATTER_SYMBOL;
    }

    private static int getScatterCount(int[] symbols) {
        int scatterCount = 0;

        for (int symbol : symbols) {
            if (symbol == SCATTER_SYMBOL) {
                scatterCount++;
            }
        }

        return scatterCount;
    }

    private static int getSymbolWin(int[] symbols) {
        int win = 0;
        int symbolCount = 0;
        int bet = 1;

        for (int symbol : symbols) {
            for (int j = 0; j < SYMBOLS_NUMBER; j++) {
                if (symbol == j) {
                    win += SYMBOL_WIN_RATES[j] * bet;
                    symbolCount++;
                }
            }
        }

        if (symbolCount >= 3) {
            win *= symbolCount;
        }

        return win;
    }
}