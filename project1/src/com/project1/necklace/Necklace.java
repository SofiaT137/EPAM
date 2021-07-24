package com.project1.necklace;
import com.project1.stones.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Necklace {

    private List<Stone> stones = new ArrayList<>(5);
    private int weight;
    private int price;

    public Necklace(List<Stone> list) {
        this.stones = list;
    }

    public int getWeightOfNeclace(){
        weight = 0;
        for (Stone stone:
                stones) {
            weight += stone.getWeightInCarat();
        }
        return weight;
    }

    public int getPriceOfNecklace(){
        price = 0;
        for (Stone stone:
                stones) {
            price += stone.getPrice();
        }
        return price;
    }

    public List<Stone> sortOfNeclaceStones(){
        Collections.sort(stones);
        return stones;
    }
}
