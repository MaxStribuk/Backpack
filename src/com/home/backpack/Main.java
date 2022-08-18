package com.home.backpack;

import java.util.*;

/**
 * Задача о рюкзаке.
 * <p>
 * Входные данные:
 * первое число - количество предметов numberItems;
 * второе число - вместимость рюкзака weight;
 * далее следуют numberItems пар чисел -
 * вес предметов и их стоимость - weights[1], cost[1] ... weights[numberItems], cost[numberItems];
 * все входные параметры являются целыми числами.
 * <p>
 * Выходные данные:
 * максимальная стоимость предметов, которые можно унести в рюкзаке, и
 * номера предметов, помещенных в рюкзак.
 */

public class Main {

    static int itemsCount;
    static int weight;
    static Item[] items;
    static int[][] dinamicTable;

    public static void main(String[] args) {
        init();
        int maxWeight = searchMaxWeigth();
        List<Integer> numberItems = restoringResponse(maxWeight, itemsCount, weight);
        printAnswer(maxWeight, numberItems);
    }

    private static void init() {
        System.out.println(Constants.ITEMS_COUNT);
        itemsCount = handlingExceptionIncorrectInput();
        System.out.println(Constants.BACKPACK_CAPACITY);
        weight = handlingExceptionIncorrectInput();
        items = new Item[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
            Item item = new Item();
            System.out.println(Constants.ITEM_WEIGHT + Item.getCount());
            item.setWeight(handlingExceptionIncorrectInput());
            System.out.println(Constants.ITEM_COST + Item.getCount());
            item.setCost(handlingExceptionIncorrectInput());
            items[i] = item;
        }
        dinamicTable = new int[itemsCount + 1][weight + 1];
    }

    private static int handlingExceptionIncorrectInput() {
        Scanner in = new Scanner(System.in);
        int inputNumber = -1;
        try {
            inputNumber = in.nextInt();
            if (inputNumber < 0) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            System.out.println(Constants.INCORRECT_INPUT);
            inputNumber = handlingExceptionIncorrectInput();
        }
        return inputNumber;
    }

    private static int searchMaxWeigth() {
        for (int i = 1; i <= itemsCount; i++) {
            for (int j = 1; j <= weight; j++) {
                if (items[i - 1].getWeight() <= j) {
                    int costWithoutCurrentItem = dinamicTable[i - 1][j];
                    int costWithCurrentItem = items[i - 1].getCost()
                            + dinamicTable[i - 1][j - items[i - 1].getWeight()];
                    dinamicTable[i][j] = Math.max(costWithoutCurrentItem, costWithCurrentItem);
                } else {
                    dinamicTable[i][j] = dinamicTable[i - 1][j];
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
                maxWeight -= items[numberItem - 1].getCost();
                weight -= items[numberItem - 1].getWeight();
            }
            numberItem--;
        }
        return numberItems;
    }

    private static void printAnswer(int maxWeight, List<Integer> numberItems) {
        System.out.println(Constants.MAX_COST + maxWeight);
        Collections.sort(numberItems);
        if (numberItems.size() > 0) {
            System.out.println(Constants.NUMBER_ITEMS);
            for (int index : numberItems) {
                System.out.print(index + "   ");
            }
        } else {
            System.out.println(Constants.NUMBER_ITEMS_ZERO);
        }
    }

}
