package com.example.todolist

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val taskList = ArrayList<Task>()
    private lateinit var taskAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val recyclerView=findViewById<RecyclerView>(R.id.recyclerview)
//        val add=findViewById<FloatingActionButton>(R.id.floatingActionButton)
//        add.setOnClickListener{
//            val intent= Intent(this,AddTask::class.java)
//            startActivity(intent)
//        }
        val auth=FirebaseAuth.getInstance()
        val logout=findViewById<ImageButton>(R.id.logout)
        logout.setOnClickListener{
            val builder= AlertDialog.Builder(this)
            builder.setTitle("LogOut")
            builder.setMessage("Do You Really Want To Logout")
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(this,"You have selected Yes", Toast.LENGTH_SHORT).show()
                auth.signOut()
                val intent= Intent(this,SigninActivity::class.java)
                startActivity(intent)
                finish()

            })
            builder.setNegativeButton("NO", DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(this,"You have Selected No", Toast.LENGTH_SHORT).show()
                //answers.set(0, arrayOf("No"))

            })
            builder.show()
        }
        changeFragment(Todo_fragment())
        val tv=findViewById<TextView>(R.id.textView)
        tv.setOnClickListener {
            val popupMenu=PopupMenu(this,tv)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener{
                when (it.itemId) {
                    R.id.to_do -> {
                        tv.text="Tasks"
                        // Replace this with your actual navigation logic (see Approach 1 or 2 below)
                       changeFragment(Todo_fragment())
                        true

                    }
                    R.id.journal->{
                        tv.text="Journals"
                        changeFragment(Journal_fragment())
                          true}
                    else -> false
                }
            }
            popupMenu.show()
        }

//        val taskViewModel=TaskViewModel(application)
////        CoroutineScope(Dispatchers.IO).launch {
////            val task=Task(0,"task","desc")
////            taskViewModel.insertTask(task)
////        }
//        taskViewModel.getTasks().observe(this, Observer { tasks ->
//            taskList.clear()
//            taskList.addAll(tasks)
//            // Notify the adapter of the data change
//            taskAdapter.notifyDataSetChanged()
//            // Update UI or perform any operation with the list of tasks
//        })
//        recyclerView.layoutManager=LinearLayoutManager(this)
//        taskAdapter=MyAdapter(taskList,this)
//        recyclerView.adapter=taskAdapter
//        val itemTouchHelper=ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    val task=taskList.get(viewHolder.adapterPosition)
//                    taskViewModel.deleteTask(task)
//                }
//            }
//
//        }).attachToRecyclerView(recyclerView)

    }
    public fun changeFragment(fragment: Fragment)
    {
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        // Assuming your fragment container has ID 'fragment_container'
        fragmentTransaction.replace(R.id.framelayout, fragment)
        fragmentTransaction.addToBackStack(null) // Optional: Add to back stack for navigation history
        fragmentTransaction.commit()
        true
    }
}