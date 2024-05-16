package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        val userid=findViewById<EditText>(R.id.email)
        val pass=findViewById<EditText>(R.id.password)
        val tv=findViewById<TextView>(R.id.reg)
        tv.setOnClickListener {
            val intent=Intent(this,Register::class.java)
            startActivity(intent)
        }
        val auth= FirebaseAuth.getInstance()
        val btn=findViewById<Button>(R.id.signin)
        btn.setOnClickListener{
            val id=userid.text.toString().trim()
            val pas=pass.text.toString().trim()
            auth.signInWithEmailAndPassword(id,pas).addOnCompleteListener{
                if(it.isSuccessful)
                {
                    Toast.makeText(this,"Logged In", Toast.LENGTH_SHORT).show()
                    val journal:JournalUser=JournalUser.instance!!
                    journal.userId=auth.currentUser?.uid
                    journal.username=auth.currentUser?.displayName
                    val intent= Intent(this@SigninActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"Try Again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}