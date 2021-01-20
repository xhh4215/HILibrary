package com.example.hi_library.cache

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/***
 * @see Room  Emtity注解
 * @author栾桂明
 * @date 2020年1月20日
 * @desc 缓存数据的对应的本地数据的数据表
 */
@Entity(tableName = "cache")
class Cache {
    /***
     * 标识一个主键
     * autoGenerate false  自己处理主键的增减  true  框架自动个处理主键的增加
     */
    @NonNull
    @PrimaryKey(autoGenerate = false)
    var key: String = ""
    /***
     * 缓存的具体的数据
     */
    var dara: ByteArray? = null
}