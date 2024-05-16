package com.example.todolist

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager.OnActivityResultListener
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.todolist.databinding.ActivityAddJournalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.Timestamp
import java.util.*

class AddJournalActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddJournalBinding
    var currentUserId:String=""
    var currentUserName:String=""
    lateinit var auth:FirebaseAuth
    lateinit var user:FirebaseUser
    private lateinit var imageView: ImageView
    var db:FirebaseFirestore=FirebaseFirestore.getInstance()
    lateinit var storageReference: StorageReference
    var collectionReference:CollectionReference=db.collection("Journal")
    lateinit var imageUri:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_journal)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_add_journal)
        storageReference=FirebaseStorage.getInstance().reference
        auth=FirebaseAuth.getInstance()
        binding.apply {
            postProgressBar.visibility=View.INVISIBLE
             if(JournalUser.instance!=null)
             {
                 currentUserId=auth.currentUser?.uid.toString()
                 currentUserName=auth.currentUser?.displayName.toString()
             }
            postSaveJournalButton.setOnClickListener {
                SaveJournal()
            }
             val mTakePhoto =
                registerForActivityResult(ActivityResultContracts.GetContent()){
                    postImageView.setImageURI(it)
                    if (it != null) {
                        imageUri=it
                    }
                }
            postImageView.setOnClickListener {
                mTakePhoto.launch("image/*")
            }
        }
    }


    private fun SaveJournal(){
        var title:String=binding.postTitleEt.text.toString().trim()
        var thoughts:String=binding.postDescriptionEt.text.toString().trim()
        binding.postProgressBar.visibility=View.VISIBLE
        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(thoughts) && imageUri!=null)
        {
            val filePath:StorageReference=storageReference.child("journal_images").child("my_image_"+com.google.firebase.Timestamp.now().seconds)
            filePath.putFile(imageUri).addOnSuccessListener(){
                filePath.downloadUrl.addOnSuccessListener {
                    var imageUri:String=it.toString()

                    var journal:Journal=Journal(title,thoughts,imageUri,currentUserId,Timestamp(Date()),currentUserName)
                    collectionReference.add(journal)
                        .addOnSuccessListener {
               val tv=findViewById<TextView>(R.id.textView)
                            tv.text="Journal"
                            var i= Intent(this,Journal_fragment::class.java)

                            startActivity(i)
                            finish()
                        }
                }
            }.addOnFailureListener(){
                Toast.makeText(this,"not uploaded",Toast.LENGTH_SHORT).show()
                binding.postProgressBar.visibility=View.INVISIBLE
            }


        }
        else{
            binding.postProgressBar.visibility=View.INVISIBLE
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode== RESULT_OK)
        {
            if(data!=null)
            {
                imageUri=data.data!!
                binding.postImageView.setImageURI(imageUri)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        user=auth.currentUser!!
    }

    override fun onStop() {
        super.onStop()

    }

}