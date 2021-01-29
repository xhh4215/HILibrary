package com.example.hi_arch.arch.mvp.kotlin

import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure


open class BaseActivity<P : BasePresenter<BaseActivity<P>>> : ImvpView<P>, AppCompatActivity() {
     override val presenter: P


    init {
        presenter = createPresenter()
        presenter.view = this
    }

    private fun createPresenter(): P {
        sequence {
            var thisClass: KClass<*> = this@BaseActivity::class
            while (true) {
                //获取当前类的父类
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap {
            it.flatMap {
                it.arguments
            }.asSequence()
        }.first {
            it.type?.jvmErasure?.isSubclassOf(ImvpPresenter::class) ?: false
        }.let {
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }
    }



}