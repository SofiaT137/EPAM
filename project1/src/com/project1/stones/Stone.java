package com.project1.stones;

public abstract class Stone implements Comparable<Stone>{
    protected String name;
    protected int pricePerCarat;
    protected float weight;
    protected int transparency;

    Stone(float weight,int transparency){
        this.weight = weight;
        this.transparency = transparency;
    }

    public abstract int getPrice();

    public float getWeightInCarat(){
        return this.weight;
    }

    public int compareTo(Stone other) {
        return (int) (this.weight-other.weight);
    }
}
