package com.example.wsupevents.storage.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wsupevents.models.auth.User

@Dao
interface SessionDao {
    @Query("SELECT * FROM User LIMIT 1 ")
    fun getSession(): LiveData<User>

    @Query("SELECT * FROM User LIMIT 1")
    fun fetch(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSession(model: User?)

    @Update
    fun updateSession(model: User?)

    @Delete
    fun deleteSession(model: User?)

    @Query("DELETE FROM User")
    fun deleteSession()

}