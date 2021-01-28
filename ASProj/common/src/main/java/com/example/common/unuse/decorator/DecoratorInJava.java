package com.example.common.unuse.decorator;

public class DecoratorInJava {
    public static void main(String[] args) {
        Panda panda = new Panda();
        BambooFood bambooFood = new BambooFood(panda);
        CarrotFood carrotFood = new CarrotFood(bambooFood);
        carrotFood.eat();
    }
}
