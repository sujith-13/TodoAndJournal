package com.example.todolist

import android.app.Application
import androidx.lifecycle.LiveData

class TaskRepository(val context:Application) {
    val taskDAO=TaskDataBase.getDatabase(context).getTaskDAO()
    fun getTasks():LiveData<List<Task>>{
        return taskDAO.getTasks()
    }

    suspend fun insertTask(task: Task){
         taskDAO.insertTask(task)
    }
    suspend fun deleteTask(task: Task)
    {
         taskDAO.deleteTask(task)
    }

}