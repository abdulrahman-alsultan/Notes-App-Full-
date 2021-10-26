package com.example.notesappsaveonly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class UpdateNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        val pk = intent.getIntExtra("pk", 0)
        val note = intent.getStringExtra("note")

        val database = DBHelper(applicationContext, MainActivity())

        val btn = findViewById<Button>(R.id.btn_update)
        val et = findViewById<EditText>(R.id.et_update_note)
        et.hint = note

        btn.setOnClickListener {
            if(et.text.isNotEmpty())
                database.update(pk, et.text.toString())
        }

    }
}