package com.project1.stones;

public class LapisLazuli extends Stone {

    private float discount = 0.15f;

    public LapisLazuli(float weight, int transparency) {
        super(weight, transparency);
        this.name = "Lapis lazuli";
        this.pricePerCarat = 144;
    }

    @Override
    public int getPrice() {
        return (int) (discount * pricePerCarat * weight);
    }
}
