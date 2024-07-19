package com.example.sqlitebasics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewTitle: TextView = itemView.findViewById(R.id.NoteTitle)
    val textViewDesc: TextView = itemView.findViewById(R.id.NoteDesc)
}

class CustomAdapter(private val mList: List<ItemsViewModel>) :
    RecyclerView.Adapter<ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val noteView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)

        return ViewHolder(noteView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.textViewTitle.text = ItemsViewModel.title
        holder.textViewDesc.text = ItemsViewModel.description
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}

