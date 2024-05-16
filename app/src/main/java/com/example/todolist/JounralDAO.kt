package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JounralDAO {
    @Query("SELECT * FROM journal")
    fun getJournal(): LiveData<List<Journal>>
    @Insert
    suspend fun insertJournal(journal: Journal)
    @Delete
    suspend fun deleteJournal(journal: Journal)
}