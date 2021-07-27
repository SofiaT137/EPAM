package com.project1.stone;

import java.util.Objects;

public abstract class Stone implements Comparable<Stone>{
    protected String name;
    protected int pricePerCarat;
    protected float weight;
    protected int transparency;
    protected int transparencyMax = 10;

    Stone(float weight,int transparency){
        this.weight = weight;
        this.transparency = transparency;
    }

    public abstract int getPrice();

    public float getWeightInCarat(){
        return this.weight;
    }

    public int compareTo(Stone other) {
        return (int) (this.getPrice()-other.getPrice());
    }

    public int getTransparency(){
        return this.transparency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stone)) return false;
        Stone stone = (Stone) o;
        return pricePerCarat == stone.pricePerCarat &&
                Float.compare(stone.weight, weight) == 0 &&
                transparency == stone.transparency &&
                Objects.equals(name, stone.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pricePerCarat, weight, transparency);
    }

    @Override
    public String toString() {
        return "Stone{" +
                "name='" + name + '\'' +
                ", pricePerCarat=" + pricePerCarat +
                ", weight=" + weight +
                ", transparency=" + transparency +
                '}';
    }
}
