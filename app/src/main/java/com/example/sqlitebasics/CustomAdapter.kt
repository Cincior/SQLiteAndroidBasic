package com.example.sqlitebasics

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewTitle: TextView = itemView.findViewById(R.id.NoteTitle)
    val textViewDesc: TextView = itemView.findViewById(R.id.NoteDesc)
    val btnDel: ImageButton = itemView.findViewById(R.id.imgBtnDel)
    val btnUpdate: ImageButton = itemView.findViewById(R.id.imgBtnUpdate)
}

class CustomAdapter(private val mList: List<ItemsViewModel>, private val context: Context) :
    RecyclerView.Adapter<ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val noteView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)

        return ViewHolder(noteView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.textViewTitle.text = itemsViewModel.title
        holder.textViewDesc.text = itemsViewModel.description
        holder.btnUpdate.setOnClickListener{
            val intent = Intent(context, UpdateActivity::class.java)
            val noteId = itemsViewModel.id
            if(noteId > 0)
            {
                intent.putExtra("id", noteId)
                intent.putExtra("title", itemsViewModel.title)
                intent.putExtra("desc", itemsViewModel.description)
                context.startActivity(intent)
            }
            else
            {
                return@setOnClickListener
            }

        }



    }

    override fun getItemCount(): Int {
        return mList.size
    }

}

