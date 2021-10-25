 package com.example.notesappsaveonly

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

 class DBHelper(ctx: Context): SQLiteOpenHelper(ctx, "notes.db", null, 1) {

    private val sqLite: SQLiteDatabase = writableDatabase
    private val sqLiteRead: SQLiteDatabase = readableDatabase


    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table notes (note Text)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun addNotes(note: String): Long{
        val cv = ContentValues()
        cv.put("note", note)
        return sqLite.insert("notes", null, cv)
    }

    fun retrieveNotes(): MutableList<String>{
        val res = mutableListOf<String>()

        val c: Cursor = sqLiteRead.rawQuery("select * from notes", null)
        c.moveToFirst()
        res.add(c.getString(0).toString())
        while (c.moveToNext()){
            res.add(c.getString(0))
        }

        return res
    }
}