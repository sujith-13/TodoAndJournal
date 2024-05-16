package com.example.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Task::class], version = 1)
abstract class TaskDataBase: RoomDatabase() {
    abstract fun getTaskDAO():TaskDAO
    companion object{
        var INSTANCE:TaskDataBase?=null
          fun getDatabase(context:Context):TaskDataBase{
              if(INSTANCE==null)
              {
                  synchronized(this)
                  {
                      INSTANCE= Room.databaseBuilder(context,TaskDataBase::class.java,"task_database").build()

                  }
              }
              return INSTANCE!!

          }

    }
}