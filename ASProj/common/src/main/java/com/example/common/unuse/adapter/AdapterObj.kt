package com.example.common.unuse.adapter

/****
 * 通过构造器的方式保存一个带适配的目标的对象 以获取原有的功能
 * 通过实现目标接口，对带适配的方法的重新实现
 * 对象适配器的缺点，对目标原有的功能的修改会比较麻烦，需要通过继承  子类的方式来实现
 */
class AdapterObj(private var adaptee: Adaptee) : Target {
    override fun request1() {
        adaptee.request1()
    }
    override fun request2() {
        println("AdapterObj:request2")
    }

}