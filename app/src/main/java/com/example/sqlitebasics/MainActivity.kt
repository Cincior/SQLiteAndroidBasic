package com.example.sqlitebasics

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Onclick Save button handler
        val buttonSave = findViewById<Button>(R.id.btnSave)
        buttonSave.setOnClickListener{
            val dataBase = DBHelper(this, null)
            val title = findViewById<TextView>(R.id.titleInput)
            val desc = findViewById<TextView>(R.id.descriptionInput)

            dataBase.addNote(title.text.toString(), desc.text.toString())
            title.text = ""
            desc.text = ""
        }

        //Onclick Show_saved_data button handler
        val buttonShow: Button = findViewById(R.id.btnShow)
        val output = findViewById<TextView>(R.id.DbDataTextView)
        buttonShow.setOnClickListener{
            var index = 1
            val dataBase = DBHelper(this, null)
            val cursor = dataBase.getNote()
            cursor!!.moveToFirst()
            val titleColIndex = cursor.getColumnIndex(DBHelper.DbTitleCol)
            val descColIndex = cursor.getColumnIndex("Opis")

            if (titleColIndex < 0 || descColIndex < 0 || cursor.count <= 0)
            {
                output.text = "ERROR"
                return@setOnClickListener
            }

            output.text = ""
            output.append(index.toString() + ". ")
            index++
            output.append(cursor.getString(titleColIndex))
            output.append(" ")
            output.append(cursor.getString(descColIndex))
            output.append("\n")

            while(cursor.moveToNext())
            {
                output.append(index.toString() + ". ")
                output.append(cursor.getString(titleColIndex))
                output.append(" ")
                output.append(cursor.getString(descColIndex))
                output.append("\n")
            }

        }

        //Onclick Delete_All button handler
        val buttonDel = findViewById<Button>(R.id.btnDelete)
        buttonDel.setOnClickListener{
            val dataBase = DBHelper(this, null)
            dataBase.deleteAll()


        }

    }

}