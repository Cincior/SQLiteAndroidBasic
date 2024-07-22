package com.example.sqlitebasics

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.concurrent.atomic.AtomicReference

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editingId = intent.getIntExtra("id", -1)
        val editingTitle = intent.getStringExtra("title")
        val editingDescription = intent.getStringExtra("desc")

        val textViewInfo: TextView = findViewById(R.id.editID)
        val editTextTitle: EditText = findViewById(R.id.inputTitleEdit)
        val editTextDescription: EditText = findViewById(R.id.inputDescEdit)

        textViewInfo.append(editingId.toString())
        editTextTitle.setText(editingTitle)
        editTextDescription.setText(editingDescription)


        //Save updated data
        val btnUpdate = findViewById<Button>(R.id.buttonUpdate)
        btnUpdate.setOnClickListener{
            val dataBase = DBHelper(this, null)

            showAlertDialog{ userConfirm ->
                if (userConfirm) {
                    dataBase.update(
                        editingId,
                        editTextTitle.text.toString(),
                        editTextDescription.text.toString()
                    )
                    dataBase.close()
                    finish()
                }
            }

        }
    }

    // Showing Alert dialog and return true if user pressed 'yes'
    private fun showAlertDialog(onResult: (Boolean) -> Unit)
    {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Are you sure?")
        dialogBuilder.setMessage("This will permanently update your data")

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