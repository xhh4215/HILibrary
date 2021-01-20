package com.example.hi_library.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

/****
 * @author 栾桂明
 * @date  2020年1月20日
 * @desc  缓存数据的持久化的工具类
 */
object HiStorage {
    /***
     * 保存缓存的数据
     */
    fun <T> saveCache(key: String, body: T) {
        val cache = Cache()
        cache.key = key
        cache.dara = toByteArray(body)
        CacheDatabase.get().cacheDao.saveCache(cache)
    }

    /***
     * 获取缓存的数据
     */
    fun <T> getCache(key: String): T? {
        val cache = CacheDatabase.get().cacheDao.getSave(key)
        return (if (cache?.dara != null) {
            toObject(cache.dara)
        } else null) as? T
    }

    /***
     * 删除缓存中的数据
     */
    fun deleteCache(cache: Cache) {
        CacheDatabase.get().cacheDao.deleteSave(cache)
    }

    /***
     * 将对象转化为byte数组
     */
    private fun <T> toByteArray(body: T): ByteArray? {
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(body)
            oos.flush()
            return baos.toByteArray()

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            baos?.close()
            oos?.close()
        }
        return ByteArray(0)
    }

    /***
     * 将byte数组转化为对象
     */
    private fun toObject(data: ByteArray?): Any? {
        var bai: ByteArrayInputStream? = null
        var ooi: ObjectInputStream? = null
        try {
            bai = ByteArrayInputStream(data)
            ooi = ObjectInputStream(bai)
            return ooi.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bai?.close()
            ooi?.close()
        }
        return null
    }
}