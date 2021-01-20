package com.example.hi_library.cache

import androidx.room.*

/***
 * @see Room Dao注解
 * @author 栾桂明
 * @date 2020年 1月20日
 * @desc  对缓存的数据进行具体操作的类
 */
@Dao
interface CacheDao {
    /***
     * 插入缓存数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCache(cache: Cache): Long

    /***
     * 查询缓存数据
     */
    @Query("select * from cache where `key` =:key")
    fun getSave(key: String): Cache?

    /***
     * 删除缓存数据
     */
    @Delete
    fun deleteSave(cache: Cache)
}