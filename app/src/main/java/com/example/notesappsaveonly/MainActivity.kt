package com.example.notesappsaveonly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var btn: Button
    private lateinit var ed: EditText
    private lateinit var notes: MutableList<NoteData>
    private lateinit var rvMain: RecyclerView
    private lateinit var adapter: RecyclerViwAdapter
    private val databaseSQLite by lazy { DBHelper(applicationContext, this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn_submit)
        ed = findViewById(R.id.ed_note)
        notes = databaseSQLite.retrieveNotes()
        rvMain = findViewById(R.id.rvMain)
        adapter = RecyclerViwAdapter(notes, this)
        rvMain.adapter = adapter
        rvMain.layoutManager = LinearLayoutManager(this)

        btn.setOnClickListener {
            val n = ed.text.toString()
            if(n.isNotEmpty()) {
                val res = databaseSQLite.addNotes(n)
                if(res > 0){
                    Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG).show()
                    notes = databaseSQLite.retrieveNotes()
                    adapter = RecyclerViwAdapter(notes, this)
                    rvMain.adapter = adapter
                }else{
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    fun delete(pk: Int){
        databaseSQLite.deleteNote(pk)
    }

    fun notifyRecycler(){
        notes = databaseSQLite.retrieveNotes()
        adapter = RecyclerViwAdapter(notes, this)
        rvMain.adapter = adapter
    }


    override fun onResume() {
        notes = databaseSQLite.retrieveNotes()
        adapter = RecyclerViwAdapter(notes, this)
        rvMain.adapter = adapter
        super.onResume()
    }


}