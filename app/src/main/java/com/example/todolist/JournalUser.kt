package com.example.todolist

import android.app.Application

class JournalUser : Application() {
    var username: String? = null
    var userId: String? = null

    companion object {

        var instance: JournalUser?=null

        get() {
            if (field == null) {
                synchronized(this){
                field = JournalUser()}
            }
            return field
        }
    }

}
