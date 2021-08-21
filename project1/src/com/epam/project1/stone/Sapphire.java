package com.epam.project1.stone;

import java.util.Objects;

public class Sapphire extends Stone {

    private static final int TAX = 4;
    private int surcharge;

    public Sapphire(float weight, int transparency, int countryOfOrigin) {
        super(weight, transparency);
        this.name = "Sapphire";
        this.pricePerCarat = 865;
        setTaxOfTheMiningSide(countryOfOrigin);
    }

    @Override
    public int getPrice() {
        return (int) (TAX* pricePerCarat * weight * transparency * surcharge / transparencyMax);
    }

    private void setTaxOfTheMiningSide(int countryOfOrigin) {
        switch (countryOfOrigin) {
            case 1 -> surcharge = 17;
            case 2 -> surcharge = 8;
            case 3 -> surcharge = 20;
            case 4 -> surcharge = 3;
            default -> System.out.println("Ups, something wrong!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sapphire)) return false;
        if (!super.equals(o)) return false;
        Sapphire sapphire = (Sapphire) o;
        return surcharge == sapphire.surcharge;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), surcharge);
    }

    @Override
    public String toString() {
        return "Sapphire{" +
                "surcharge=" + surcharge +
                ", name='" + name + '\'' +
                ", pricePerCarat=" + pricePerCarat +
                ", weight=" + weight +
                ", transparency=" + transparency +
                '}';
    }
}
