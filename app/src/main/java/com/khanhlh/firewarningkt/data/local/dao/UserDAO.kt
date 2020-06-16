package com.khanhlh.firewarningkt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khanhlh.firewarningkt.data.local.model.User
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Query("SELECT * FROM Users WHERE userid= :id")
    fun getArticleById(id:Int): Flowable<User>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

}