package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val name=findViewById<EditText>(R.id.name)
        val email=findViewById<EditText>(R.id.mail)
        val password=findViewById<EditText>(R.id.pass)
        val btn=findViewById<Button>(R.id.register)
        val login=findViewById<TextView>(R.id.loginlink)
        login.setOnClickListener {
            startActivity(Intent(this,SigninActivity::class.java))

        }

        var auth= FirebaseAuth.getInstance()
        val database=FirebaseDatabase.getInstance()


        btn.setOnClickListener{
            val sname=name.text.toString()
            val mail=email.text.toString()
            Toast.makeText(this, mail, Toast.LENGTH_SHORT).show()
            val pass=password.text.toString()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+"
           if (pass.length<6) {
               password.setError("Password length should be more than 6")
           }
            auth.createUserWithEmailAndPassword(mail.trim(),pass.trim()).addOnCompleteListener {

                if (it.isSuccessful) {
                    Toast.makeText(this,"sucess", Toast.LENGTH_SHORT).show()
                    val id = it.result?.user?.uid
                    val reference =
                        id?.let { it1 -> database.getReference().child("user").child(it1) };
                    val users = id?.let { it1 -> User(it1,sname,mail.trim(),pass.trim()) }
                    if (reference != null) {
                        reference.setValue(users).addOnCompleteListener {
                            if (it.isSuccessful) {
                                startActivity(Intent(this, SigninActivity::class.java))
                                finish()
                            }
                        }
                    }
                }
                else
                {
                    Toast.makeText(this,"fail", Toast.LENGTH_SHORT).show()
                    Log.e("Firebase", "Failed to create user", it.exception)
                }
            }
        }
    }
}