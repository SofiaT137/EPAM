package com.project1.stone;

public class Sapphire extends Stone {

    private static final int tax = 14;

    public Sapphire(float weight, int transparency) {
        super(weight, transparency);
        this.name = "Sapphire";
        this.pricePerCarat = 865;
    }

    @Override
    public int getPrice() {
        return (int) (tax * pricePerCarat * weight * transparency / transparencyMax);
    }
}
