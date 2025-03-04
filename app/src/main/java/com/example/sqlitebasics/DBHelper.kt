package com.example.sqlitebasics

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import android.util.Log

class DBHelper(private val context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DbName, factory, DbVersion)
{
    override fun onCreate(db: SQLiteDatabase)
    {
        val query = ("CREATE TABLE " + DbTableName + " (Id INTEGER PRIMARY KEY," + DbTitleCol + " TEXT, " + DbDescCol + " TEXT)")

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

    fun getNote(): Cursor
    {
        val db = this.readableDatabase
        val r = db.rawQuery("SELECT * FROM " + DbTableName, null)
        return r
    }

    fun deleteAll()
    {
        val db = writableDatabase
        val x = db.delete(DbTableName, null, null) // resistant to SQL injection
        Toast.makeText(context, x.toString(), Toast.LENGTH_SHORT).show()

    }

    fun update(Id: Int, title: String, description: String)
    {
        val db = writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DbTitleCol, title)
        contentValues.put(DbDescCol, description)
        val selection = "Id = ?"


        db.update(DbTableName, contentValues, selection, arrayOf(Id.toString()))
    }

    fun deleteRow(Id: Int)
    {
        val dataBase = writableDatabase
        val whereClause = "Id = ?"


        dataBase.delete(DbTableName, whereClause, arrayOf(Id.toString()))
    }




    companion object
    {
        private val DbName = "Test"
        private val DbVersion = 1
        val DbTableName = "tabelaTest"
        val DbTitleCol = "Tytul"
        val DbDescCol = "Opis"
    }
}