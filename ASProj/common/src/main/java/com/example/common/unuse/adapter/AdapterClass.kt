package com.example.common.unuse.adapter

/***
 * 通过继承的方式 对被适配的类的原有的方法的保存  或者是复写
 * 通过实现目标接口，对带适配的方法的重新实现
 */
class AdapterClass : Adaptee(), Target {
    /***
     * 由于Adaptee没有Request2()
     * 因此适配器需要补充上这个方法
     */
    override fun request2() {
        println("AdapterClass:request2")
    }
}