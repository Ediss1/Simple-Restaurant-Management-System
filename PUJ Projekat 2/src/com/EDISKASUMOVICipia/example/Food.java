package com.EDISKASUMOVICipia.example;

import com.EDISKASUMOVICipia.example.Data.DataBase;

import java.util.ArrayList;

public class Food {
    public String foodName;
    public String foodPrice;

    public Food(){
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    public Food(String foodName, String foodPrice) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    @Override
    public String toString(){
        return "Naziv hrane: " + foodName + ", Cijena hrane: " + foodPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

}
