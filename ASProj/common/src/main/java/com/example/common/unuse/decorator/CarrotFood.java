package com.example.common.unuse.decorator;
//具体的装饰器
public class CarrotFood extends Food {
    public CarrotFood(Animal animal) {
        super(animal);
    }

    @Override
    public void eat() {
        super.eat();
        System.out.println("可以吃胡萝卜");
    }
}
