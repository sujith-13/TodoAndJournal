package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDAO {

    @Query("SELECT * FROM task")
       fun getTasks():LiveData<List<Task>>
       @Insert
       suspend fun insertTask(task: Task)
       @Delete
       suspend fun deleteTask(task: Task)
}