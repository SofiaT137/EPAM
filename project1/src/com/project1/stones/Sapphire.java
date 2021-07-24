package com.project1.stones;

public class Sapphire extends Stone {

    private int tax = 14;

    public Sapphire(float weight, int transparency) {
        super(weight, transparency);
        this.name = "Sapphire";
        this.pricePerCarat = 865;
    }

    @Override
    public int getPrice() {
        return (int) (tax * pricePerCarat * weight * transparency / 10);
    }
}
