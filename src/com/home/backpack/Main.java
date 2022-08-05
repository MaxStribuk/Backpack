package com.home.backpack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Задача о рюкзаке.
 *
 * Входные данные:
 * первое число - количество предметов numberItems;
 * второе число - вместимость рюкзака weight;
 * далее следуют numberItems пар чисел -
 * вес предметов и их стоимость - weights[1], cost[1] ... weights[numberItems], cost[numberItems];
 * все входные параметры являются целыми числами.
 *
 * Выходные данные:
 * максимальная стоимость предметов, которые можно унести в рюкзаке, и
 * номера предметов, помещенных в рюкзак.
 */

public class Main {

    static int itemsCount;
    static int weight;
    static int[] weights;
    static int[] cost;
    static int[][] dinamicTable;
    static final String MAX_COST = "Максимальная стоимость предметов, которые можно поместить в рюкзак - ";
    static final String NUMBER_ITEMS = "В рюкзак нужно поместить предметы с номерами: ";


    public static void main(String[] args) {
        init();
        int maxWeight = searchMaxWeigth();
        List <Integer> numberItems = restoringResponse(maxWeight, itemsCount, weight);
        printAnswer(maxWeight, numberItems);
    }

    private static void init() {
        Scanner in = new Scanner(System.in);
        itemsCount = in.nextInt();
        weight = in.nextInt();
        weights = new int[itemsCount];
        cost = new int[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
            weights[i] = in.nextInt();
            cost[i] = in.nextInt();
        }
        in.close();
        dinamicTable = new int[itemsCount + 1][weight + 1];
    }

    private static int searchMaxWeigth() {
        for (int i = 1; i <= itemsCount; i++) {
            for (int j = 1; j <= weight; j++) {
                if (weights[i - 1] <= j ) {
                    int firstValue = dinamicTable[i - 1][j];
                    int secondValue = cost[i - 1] + dinamicTable[i - 1][j - weights[i - 1]];
                    dinamicTable[i][j] = Math.max(firstValue, secondValue);
                }
            }
        }
        return dinamicTable[itemsCount][weight];
    }

    private static List<Integer> restoringResponse(int maxWeight, int numberItem, int weight) {
        List<Integer> numberItems = new ArrayList<>();
        while (maxWeight != 0) {
            if (maxWeight != dinamicTable[numberItem - 1][weight]) {
                numberItems.add(numberItem);
                maxWeight -= cost[numberItem - 1];
                weight -= weights[numberItem - 1];
            }
            numberItem--;
        }
        return numberItems;
    }

    private static void printAnswer(int maxWeight, List<Integer> numberItems) {
        System.out.println(MAX_COST + maxWeight);
        Collections.sort(numberItems);
        System.out.println(NUMBER_ITEMS);
        for (int index: numberItems) {
            System.out.print(index + "   ");
        }
    }

}
