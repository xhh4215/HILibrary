package com.example.common.unuse.decorator;

//装饰器组件
public abstract class Food implements Animal {
    Animal animal;

    public Food(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void eat() {
        animal.eat();
    }
}
