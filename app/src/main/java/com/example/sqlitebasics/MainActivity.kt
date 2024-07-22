package com.example.sqlitebasics

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import res.layout.*


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CustomAdapter

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

        //Disabling keyboard when user clicks outside EditText
        val mainLayout: View = findViewById(R.id.main)
        mainLayout.setOnClickListener{
            disableKeyboard(mainLayout)
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


            //Disabling keyboard
            val currentView: View? = this.currentFocus
            disableKeyboard(currentView)
        }

        //Onclick Show_saved_data button handler
        val buttonShow: Button = findViewById(R.id.btnShow)
        val output = findViewById<RecyclerView>(R.id.recyclerView)
        buttonShow.setOnClickListener{
            output.layoutManager = LinearLayoutManager(this)

            val dataBase = DBHelper(this, null)
            val cursor = dataBase.getNote()

            val data = ArrayList<ItemsViewModel>()
            adapter = CustomAdapter(data, this)
            output.adapter = adapter



            if (cursor.count == 0)
            {
                data.add(ItemsViewModel(0, "ERROR", ""))
                return@setOnClickListener
            }
            else
            {
                val titleColIndex = cursor.getColumnIndex(DBHelper.DbTitleCol)
                val descColIndex = cursor.getColumnIndex("Opis")
                val idColIndex = cursor.getColumnIndex("Id")
                while(cursor.moveToNext())
                {
                    data.add(ItemsViewModel(cursor.getInt(idColIndex) ,cursor.getString(titleColIndex), cursor.getString(descColIndex)))
                }
            }

            dataBase.close()
            cursor.close()
        }

        //Onclick Delete_All button handler
        val buttonDel = findViewById<Button>(R.id.btnDelete)
        buttonDel.setOnClickListener{
            val dataBase = DBHelper(this, null)
            dataBase.deleteAll()


        }


    }

//    override fun onResume() {
//        super.onResume()
//        val mainLayout: View = findViewById(R.id.main)
//
//    }

    // Function for disabling keyboard
    private fun disableKeyboard(currentView: View?)
    {
        if (currentView != null) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

            // hiding keyboard.
            inputMethodManager.hideSoftInputFromWindow(currentView.windowToken, 0)
        }
    }

}