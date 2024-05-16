package com.example.todolist
//
//import androidx.room.Entity
//import java.nio.file.Path
//@Entity(tableName = "journal")
//data class Journal(
//
//    val id:Int,
//    val title:String,
//    val content:String,
//    val path:String
//)


import com.google.firebase.Timestamp

class Journal {
    var title: String = ""
    var thoughts: String = ""
    var imageUrl: String = ""
    var userId: String = ""
    var timeAdded: Timestamp? = null
    var userName: String = ""

    constructor() {}

    constructor(
        title: String,
        thoughts: String,
        imageUrl: String,
        userId: String,
        timeAdded: Timestamp,
        userName: String
    ) {
        this.title = title
        this.thoughts = thoughts
        this.imageUrl = imageUrl
        this.userId = userId
        this.timeAdded = timeAdded
        this.userName = userName
    }
}
