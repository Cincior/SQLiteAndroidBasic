package com.example.sqlitebasics

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        val output = findViewById<RecyclerView>(R.id.recyclerView)
        buttonShow.setOnClickListener{
            output.layoutManager = LinearLayoutManager(this)
            val data = ArrayList<ItemsViewModel>()


            val dataBase = DBHelper(this, null)
            val cursor = dataBase.getNote()
            cursor!!.moveToFirst()
            val titleColIndex = cursor.getColumnIndex(DBHelper.DbTitleCol)
            val descColIndex = cursor.getColumnIndex("Opis")

            var adapter = CustomAdapter(data)
            if (titleColIndex < 0 || descColIndex < 0 || cursor.count <= 0)
            {
                adapter = CustomAdapter(data)
                data.add(ItemsViewModel("ERROR", "no data to display"))
                output.adapter = adapter
                return@setOnClickListener
            }

           data.add(ItemsViewModel(cursor.getString(titleColIndex), cursor.getString(descColIndex)))

            while(cursor.moveToNext())
            {
                data.add(ItemsViewModel(cursor.getString(titleColIndex), cursor.getString(descColIndex)))
            }


            output.adapter = adapter

        }

        //Onclick Delete_All button handler
        val buttonDel = findViewById<Button>(R.id.btnDelete)
        buttonDel.setOnClickListener{
            val dataBase = DBHelper(this, null)
            dataBase.deleteAll()


        }

    }

}