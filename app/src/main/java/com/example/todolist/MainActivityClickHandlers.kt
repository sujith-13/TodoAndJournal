package com.example.todolist

import android.content.Context
import android.content.Intent
import android.view.View
import kotlin.coroutines.coroutineContext

class MainActivityClickHandlers {
lateinit var context: Context
    constructor(context: Context)
    {
        this.context=context
    }


    fun onFABClicked(view: View){
        val intent=Intent(view.context,AddTask::class.java)
        context.startActivity(intent)
    }
}