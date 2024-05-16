package com.example.todolist

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class JournalListAdapter(// Variables
    private val context: Context, private val journalList: List<Journal>
) :
    RecyclerView.Adapter<JournalListAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
         val title=itemView.findViewById<TextView>(R.id.journal_title_list)
        val thoughts=itemView.findViewById<TextView>(R.id.journal_thought_list)
        val timestamp=itemView.findViewById<TextView>(R.id.journal_timestamp_list)
          val img=itemView.findViewById<ImageView>(R.id.journal_image_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.journal_row,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return journalList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentJournal = journalList[position]
        holder.title.text=currentJournal.title
        holder.thoughts.text=currentJournal.thoughts
      //  holder.timestamp.text=currentJournal.timeAdded.toString()
        val timeAgo: String = DateUtils.getRelativeTimeSpanString(
            currentJournal.timeAdded!!.seconds * 1000
        ).toString()

        holder.timestamp.text = timeAgo

        Glide.with(context)
            .load(currentJournal.imageUrl)
            .fitCenter()
            .into(holder.img)
    }
//    lateinit var binding:JournalRowBinding
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        binding=JournalRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        return MyViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currentJournal = journalList[position]
//        holder.bind(currentJournal)
//         binding.journalImageList.setImageURI(currentJournal.imageUrl.toUri())
//    }
//
//    override fun getItemCount(): Int {
//        return journalList.size
//    }
//
//    // View Holder
//     class MyViewHolder(var binding: JournalRowBinding) : RecyclerView.ViewHolder(binding.root) {
//       fun bind(journal: Journal){
//           binding.journal=journal
//
//       }
//    }
}
