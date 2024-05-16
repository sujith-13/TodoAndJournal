package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val ref= FirebaseAuth.getInstance()
        Handler(Looper.getMainLooper()).postDelayed({
            if(FirebaseAuth.getInstance().uid==null){
                startActivity(Intent(this,SigninActivity::class.java))
                finish()}
            else{
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        },3000)
    }
}