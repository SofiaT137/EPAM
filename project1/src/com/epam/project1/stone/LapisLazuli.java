package com.project1.stone;

public class LapisLazuli extends Stone {

    private static final float DISCOUNT = 0.15f;

    public LapisLazuli(float weight, int transparency) {
        super(weight, transparency);
        this.name = "Lapis lazuli";
        this.pricePerCarat = 144;
    }

    @Override
    public int getPrice() {
        return (int) (DISCOUNT * pricePerCarat * weight);
    }
}
