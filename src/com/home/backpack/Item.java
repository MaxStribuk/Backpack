package com.home.backpack;

public class Item {

    private static int count = 0;
    private int weight;
    private int cost;

    public Item() {
        count++;
    }

    public Item(int weight, int cost) {
        this.weight = weight;
        this.cost = cost;
        count++;
    }

    public static int getCount() {
        return count;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
