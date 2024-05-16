package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName="task")
data class Task(
    @PrimaryKey(autoGenerate = true)
     val id:Int,
     val title:String,
    val description:String,
    val selectedDate: String,
    val selectedTime: String
)
