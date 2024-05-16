package com.example.todolist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Todo_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Todo_fragment : Fragment() {
    private lateinit var taskList: ArrayList<Task>
    private lateinit var taskAdapter: MyAdapter
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo_fragment, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val add = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        taskList = ArrayList()

        // Get reference to TaskViewModel using ViewModelProvider (recommended)
        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)

        // Observe changes to tasks using LiveData

        taskViewModel.getTasks().observe(viewLifecycleOwner, Observer { tasks ->
            taskList.clear()
            taskList.addAll(tasks)
            taskAdapter.notifyDataSetChanged()
        })

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        taskAdapter = MyAdapter(taskList, requireContext())
        recyclerView.adapter = taskAdapter

        // Setup swipe to delete functionality using ItemTouchHelper
        val itemTouchHelper=ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                CoroutineScope(Dispatchers.IO).launch {
                    val task=taskList.get(viewHolder.adapterPosition)
                    taskViewModel.deleteTask(task)
                }
            }

        }).attachToRecyclerView(recyclerView)
        add.setOnClickListener {
            val intent = Intent(requireContext(), AddTask::class.java)
            startActivity(intent)
        }

        return view
    }
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_todo_fragment, container, false)
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment Todo_fragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            Todo_fragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}