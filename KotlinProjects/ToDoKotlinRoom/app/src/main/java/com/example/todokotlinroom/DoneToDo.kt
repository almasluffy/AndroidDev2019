package com.example.todokotlinroom

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DoneToDo : AppCompatActivity(), ToDoAdapter.OnItemClickListener {
    private var recyclerView: RecyclerView? = null
    private var toDoAdapter: ToDoAdapter? = null
    val LOG_TAG = "myLogs"
    var dbHelper2: DBHelper2? = null
    private val doneList: List<ToDo> = ArrayList()
    private var btnClear: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done_to_do)
        dbHelper2 = DBHelper2(this)
        val db2 = dbHelper2!!.writableDatabase
        initRecyclerView()
        initAdapter()
        LoadData()
        toDoAdapter!!.addAll(doneList)
        btnClear = findViewById(R.id.clearBtn)
        btnClear!!.setOnClickListener(View.OnClickListener {
            val clearCount = db2.delete("DoneTable", null, null)
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
        recyclerView!!.layoutManager = layoutManager
    }

    private fun initAdapter() {
        toDoAdapter = ToDoAdapter(this)
        recyclerView!!.adapter = toDoAdapter
    }

    override fun onItemClick(position: Int, item: ToDo?) {}
    fun LoadData() {
        val db = dbHelper2!!.writableDatabase
        val c = db.query("DoneTable", null, null, null, null, null, null)
        //doneList.clear()
        // ставим позицию курсора на первую строку выборки
// если в выборке нет строк, вернется false
        if (c.moveToFirst()) { // определяем номера столбцов по имени в выборке
            val idColIndex = c.getColumnIndex("id")
            val nameColIndex = c.getColumnIndex("name")
            val descColIndex = c.getColumnIndex("description")
            do { //                        System.out.println(c.getInt(idColIndex));
                val todo = ToDo(
                    c.getInt(idColIndex),
                    c.getString(nameColIndex),
                    c.getString(descColIndex)
                )
                //doneList.add(todo)
                // получаем значения по номерам столбцов и пишем все в лог
//                        Log.d(LOG_TAG,
//                                "ID = " + c.getInt(idColIndex) +
//                                        ", name = " + c.getString(nameColIndex) +
//                                        ", description = " + c.getString(descColIndex));
// переход на следующую строку
// а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext())
        } else c.close()
        Collections.reverse(doneList)
        toDoAdapter!!.addAll(doneList)
    }

    inner class DBHelper2(context: Context?) :
        SQLiteOpenHelper(context, "myData2", null, 1) {
        override fun onCreate(db: SQLiteDatabase) {
            Log.d(LOG_TAG, "--- onCreate database ---")
            // создаем таблицу с полями
            db.execSQL(
                "create table DoneTable ("
                        + "id integer primary key autoincrement,"
                        + "name text,"
                        + "description text" + ");"
            )
        }

        override fun onUpgrade(
            db: SQLiteDatabase,
            oldVersion: Int,
            newVersion: Int
        ) {
        }
    }
}
