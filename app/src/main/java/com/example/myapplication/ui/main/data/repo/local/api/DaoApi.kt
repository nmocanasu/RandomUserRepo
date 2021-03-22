package com.example.myapplication.ui.main.data.repo.local

import androidx.room.*
import com.example.myapplication.ui.main.data.repo.local.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE page LIKE :page")
    fun loadAllByPage(page: Int): List<User>

    @Query("SELECT * FROM user WHERE remoteUUID LIKE :uuid LIMIT 1")
    fun findUser(uuid: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: User)

    @Delete
    fun delete(user: User)
}