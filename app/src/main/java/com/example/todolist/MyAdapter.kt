package com.example.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(val list:ArrayList<Task>,val context: Context):RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
          val checkbok=view.findViewById<CheckBox>(R.id.checkBox)
        val text=view.findViewById<TextView>(R.id.task)
        val datetime=view.findViewById<TextView>(R.id.dateandtime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task=list[position];
          holder.text.text=task.title
        holder.datetime.text=task.selectedDate+" "+task.selectedTime

    }


}