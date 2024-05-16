package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel:AndroidViewModel {
    private lateinit var taskRepository: TaskRepository
    constructor(application: Application) : super(application){
                     taskRepository= TaskRepository(application)
    }
    fun getTasks():LiveData<List<Task>>{
        return taskRepository.getTasks()
    }
    suspend fun insertTask(task:Task){
        viewModelScope.launch (Dispatchers.IO){
            taskRepository.insertTask(task)
        }
    }
   suspend fun deleteTask(task: Task){
       viewModelScope.launch (Dispatchers.IO){
           taskRepository.deleteTask(task)
       }
   }
}