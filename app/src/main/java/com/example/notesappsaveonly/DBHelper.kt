package com.example.notesappsaveonly

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.core.database.getIntOrNull

class DBHelper(ctx: Context, private val mainActivity: MainActivity) : SQLiteOpenHelper(ctx, "notes.db", null, 2) {

    private val sqLite: SQLiteDatabase = writableDatabase
    private val sqLiteRead: SQLiteDatabase = readableDatabase


    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table notesV2 (pk Integer not null primary key autoincrement, note Text)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table notes")
        onCreate(p0)
    }

    fun addNotes(note: String): Long {
        val cv = ContentValues()
        cv.put("note", note)
        return sqLite.insert("notesV2", null, cv)
    }


    fun retrieveNotes(): MutableList<NoteData> {
        val res = mutableListOf<NoteData>()

        val c: Cursor = sqLiteRead.rawQuery("select * from notesV2", null)

        if(c.moveToFirst()){
            res.add(NoteData(c.getInt(0), c.getString(1).toString()))
            while (c.moveToNext()) {
                res.add(NoteData(c.getInt(0), c.getString(1).toString()))
            }
        }
        return res
    }


    fun deleteNote(p: Int){
        val res = sqLite.delete("notesV2", "pk = ?", arrayOf(p.toString()))
        mainActivity.notifyRecycler()
    }

    fun update(pk:Int, newNote: String){
        val cv = ContentValues()
        cv.put("note", newNote)
        val res = sqLite.update("notesV2", cv, "pk = ?", arrayOf(pk.toString()))
    }
}