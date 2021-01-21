package com.example.hi_library.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hi_library.utils.AppGlobals

/****
 * @author 栾桂明
 * @see  @Database
 * @date 2020年1月20日
 * @desc  创建数据库的具体的操作
 */
@Database(entities = [Cache::class], version = 1,exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {
    //操作数据的dao对象
    abstract val cacheDao: CacheDao
    /***
     * 通过一个伴生对象初始化数据库对象
     */
    companion object {
        private var database: CacheDatabase
        fun get(): CacheDatabase {
            return database
        }
        init {
            val context = AppGlobals.get()!!.applicationContext
            database =
                Room.databaseBuilder(context, CacheDatabase::class.java, "howow_cache")
                    .build()

        }
    }
}