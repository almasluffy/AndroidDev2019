package com.example.todokotlinfile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class DoneToDo : AppCompatActivity(), ToDoAdapter.OnItemClickListener {
    private var recyclerView: RecyclerView? = null
    private var toDoAdapter: ToDoAdapter? = null
    private var doneList: List<ToDo?>? = ArrayList()
    private var btnClear: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done_to_do)
        initRecyclerView()
        initAdapter()
        LoadData()
        toDoAdapter!!.addAll(doneList)
        btnClear = findViewById(R.id.clearBtn)
        btnClear!!.setOnClickListener(View.OnClickListener {
            val sharedPreferences =
                getSharedPreferences("donePref", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().commit()
            val toast = Toast.makeText(
                applicationContext,
                "All goals was cleared", Toast.LENGTH_SHORT
            )
            toast.show()
            val i = Intent(baseContext, MainActivity::class.java)
            startActivity(i)
        })
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView!!.setLayoutManager(layoutManager)
    }

    private fun initAdapter() {
        toDoAdapter = ToDoAdapter(this)
        recyclerView!!.adapter = toDoAdapter
    }

    override fun onItemClick(position: Int, item: ToDo?) {}
    fun LoadData() {
        val sharedPreferences =
            getSharedPreferences("donePref", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("todo done", null)
        val type: Type =
            object : TypeToken<ArrayList<ToDo?>?>() {}.getType()
        doneList = gson.fromJson(json, type)
        if (doneList == null) {
            doneList = ArrayList()
        }
    }
}
