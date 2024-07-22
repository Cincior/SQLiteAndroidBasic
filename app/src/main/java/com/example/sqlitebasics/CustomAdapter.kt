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
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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
    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val noteView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)

        return ViewHolder(noteView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.textViewTitle.text = itemsViewModel.title
        holder.textViewDesc.text = itemsViewModel.description

        // Ustawienie koloru tła w zależności od wybranego elementu
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.btnAdd))
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        }

        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPosition) // Resetuj tło poprzedniego wybranego elementu
            selectedPosition = holder.adapterPosition // Zaktualizuj pozycję wybranego elementu
            notifyItemChanged(selectedPosition) // Ustaw nowe tło dla wybranego elementu
        }

        holder.btnUpdate.setOnClickListener {
            onUpdateButtonClicked(context, itemsViewModel)
        }

        holder.btnDel.setOnClickListener{
            showAlertDialog { answer ->
                if (answer)
                {
                    val db = DBHelper(context, null)
                    db.deleteRow(itemsViewModel.id)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }

    private fun onUpdateButtonClicked(context: Context, itemsViewModel: ItemsViewModel)
    {
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
            return
        }
    }

    // Showing Alert dialog and return true if user pressed 'yes'
    private fun showAlertDialog(onResult: (Boolean) -> Unit)
    {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Are you sure?")
        dialogBuilder.setMessage("This will permanently delete your data")

        dialogBuilder.setPositiveButton("Yes"){ dialog, which ->
            onResult(true)
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton("No"){ dialog, which ->
            onResult(false)
            dialog.dismiss()
        }

        dialogBuilder.create().show()
    }

}

