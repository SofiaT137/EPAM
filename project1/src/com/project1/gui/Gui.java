package com.project1.gui;

import com.project1.necklace.Necklace;
import com.project1.stone.LapisLazuli;
import com.project1.stone.Sapphire;
import com.project1.stone.Stone;
import com.project1.stone.Tanzanite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Gui {

    public static void main(String[] args) throws IOException {
        Gui gui = new Gui();
        gui.show();
    }

    public void show() throws IOException {
        System.out.println("Hello, let's get together a lovely necklace for you...");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please, enter the number of stones");
        int amount = validateParseInt(reader);

        while (!checkAmount(amount)) {
            System.out.println("Enter any positive integer");
            amount = validateParseInt(reader);
        }

        List<Stone> stones = new ArrayList<>();

        for (int i = 0; i < amount; i++) {

            System.out.println("Enter the number,which corresponds to one of the proposed: Sapphire(1),Tanzanite(2),Lapis Lazuli(3)");
            int name = validateParseInt(reader);
            while (!(checkName(name))) {
                System.out.println("Enter the correct integer between 1 and 3");
                name = validateParseInt(reader);
            }

            System.out.println("Enter the stones weight in karate");
            float weight = validateParseFloat(reader);
            while (!checkWeight(weight)) {
                System.out.println("Enter any positive integer");
                weight = validateParseFloat(reader);
            }

            System.out.println("Enter the desirable degree of transparency (from 1 to 10)");
            int transparency = getTransparency(reader);

            switch (name) {
                case 1:
                    stones.add(new Sapphire(weight, transparency));
                    break;
                case 2:
                    stones.add(new Tanzanite(weight, transparency));
                    break;
                case 3:
                    stones.add(new LapisLazuli(weight, transparency));
                    break;
            }
        }

        Necklace necklace = new Necklace(stones);
        System.out.println();
        System.out.println("You made a great necklace with a total amount " + necklace.getPriceOfNecklace() + " $.");
        System.out.println();
        System.out.println("Here is your necklace:");

        necklace.sortOfNeclaceStones();

        for (Stone stone :
                stones) {
            System.out.println("Name of the stone: " + stone.getClass().getSimpleName() + " value: " + stone.getPrice()+ " $.");
        }

        System.out.println("Do you want to find some special stone in your necklace: 'y' or 'n'?");
        String result = reader.readLine();

        if (!(result.equals("y"))){
            System.out.println("Ok,thank you, have a nice day!");
        }
        else{
            System.out.println("Enter the minimal value of transparency");
            int minTransparency = getTransparency(reader);

            System.out.println("Enter the maximal value of transparency");
            int maxTransparency = getTransparency(reader);

            findInNecklace(minTransparency,maxTransparency,stones);
        }
        reader.close();
    }


    private boolean checkAmount(int amount) {
        if (amount <= 0) {
            return false;
        }
        return true;
    }

    private boolean checkName(int name) {
        if (name < 1 || name > 3) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkWeight(float weight) {
        if (weight <= 0) {
            return false;
        }
        return true;
    }

    private int getTransparency(BufferedReader reader){
        int transparency = validateParseInt(reader);
        while (!(checkTransparency(transparency))){
            System.out.println("Enter the correct integer number between  from 1 to 10");
            transparency = validateParseInt(reader);
        }
        return transparency;
    }

    private boolean checkTransparency(int transparency) {
        if (transparency < 1 || transparency > 10) {
            return false;
        }
        return true;
    }

    private int validateParseInt(BufferedReader reader) {
        while (true) {
            try {
                int value = Integer.parseInt(reader.readLine());
                return value;
            } catch (Exception e) {
                System.out.println("It is mistake! Please, enter the correct integer");
            }
        }
    }

    private float validateParseFloat(BufferedReader reader) {
        while (true) {
            try {
                float value = Float.parseFloat(reader.readLine());
                return value;
            } catch (Exception e) {
                System.out.println("It is mistake! Please, enter the correct float");
            }
        }
    }
    private void findInNecklace(int min,int max,List<Stone> list){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTransparency() >= min && list.get(i).getTransparency() <= max){
                System.out.println(list.get(i));
            }
        }
    }
}
