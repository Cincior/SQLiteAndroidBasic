package com.example.sqlitebasics

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(private val context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DbName, factory, DbVersion)
{
    override fun onCreate(db: SQLiteDatabase)
    {
        val query = ("CREATE TABLE " + DbTableName + " (Id INTEGER PRIMARY KEY," + DbTitleCol + " TEXT, Opis TEXT)")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int)
    {
        db.execSQL("DROP TABLE IF EXISTS tabelaTest")
    }

    fun addNote(title: String, desc: String)
    {
        val inputData = ContentValues()

        inputData.put("Tytul", title)
        inputData.put("Opis", desc)

        val db = this.writableDatabase
        db.insert(DbTableName, null, inputData)
        db.close()
    }

    fun getNote(): Cursor?
    {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + DbTableName, null)
    }

    fun deleteAll()
    {
        val db = writableDatabase
        val x = db.delete(DbTableName, null, null) // resistant to SQL injection
        Toast.makeText(context, x.toString(), Toast.LENGTH_SHORT).show()

    }




    companion object
    {
        private val DbName = "Test"
        private val DbVersion = 1
        val DbTableName = "tabelaTest"
        val DbTitleCol = "Tytul"
    }
}