package com.example.todokotlinroom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UpdateToDo : AppCompatActivity() {
    private var updText: EditText? = null
    private var updDesc: EditText? = null
    private var btnSave: Button? = null
    private var btnDone: Button? = null
    private var btnDelete: Button? = null
    private var todo: ToDo? = null
    private var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_to_do)
        updText = findViewById(R.id.updatedText)
        updDesc = findViewById(R.id.updatedDesc)
        btnSave = findViewById(R.id.saveBtn)
        btnDone = findViewById(R.id.doneBtn)
        btnDelete = findViewById(R.id.deleteBtn)
        setToDoData()
        updText!!.setText(todo!!.name)
        updDesc!!.setText(todo!!.description)
        btnSave!!.setOnClickListener(View.OnClickListener {
            if (updText!!.getText().toString() == "" || updDesc!!.getText().toString() == "") {
                val toast = Toast.makeText(
                    applicationContext,
                    "Не все поля введены!!!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                todo!!.name = updText!!.getText().toString()
                todo!!.description = updDesc!!.getText().toString()
                val intent = Intent()
                intent.putExtra("todo", todo)
                intent.putExtra("position", position)
                intent.putExtra("status", "toSave")
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        })
        btnDelete!!.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.putExtra("todo", todo)
            intent.putExtra("position", position)
            intent.putExtra("status", "toDelete")
            setResult(Activity.RESULT_OK, intent)
            finish()
        })
        btnDone!!.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.putExtra("todo", todo)
            intent.putExtra("position", position)
            intent.putExtra("status", "toDone")
            setResult(Activity.RESULT_OK, intent)
            finish()
        })
    }

    private fun setToDoData() {
        if (intent.hasExtra("todo")) { //Todo check ToDo entity for null
            todo = intent.getSerializableExtra("todo") as ToDo
            position = intent.getIntExtra("position", -1)
            println(position)
            System.out.println(todo!!.name)
            updText!!.setText(todo!!.name)
            updDesc!!.setText(todo!!.description)
        }
    }
}