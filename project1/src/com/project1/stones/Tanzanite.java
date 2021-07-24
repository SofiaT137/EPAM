package com.project1.stones;

public class Tanzanite extends Stone{

    public Tanzanite(float weight, int transparency) {
        super(weight, transparency);
        this.name = "Tanzanite";
        this.pricePerCarat = 300;
    }

    @Override
    public int getPrice() {
        return (int) (pricePerCarat * weight * transparency / 10);
    }
}
