package com.example.todokotlinroom

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ToDoAdapter.OnItemClickListener {
    private var recyclerView: RecyclerView? = null
    private var toDoAdapter: ToDoAdapter? = null
    val LOG_TAG = "myLogs"
    var dbHelper: DBHelper? = null
    var dbHelper2: DBHelper2? = null
    private var btnAdd: Button? = null
    private var btnShow: Button? = null
    private var todoTitle: EditText? = null
    private var todoDesc: EditText? = null
    private lateinit var toDoList: List<ToDo>
    private var doneList: List<ToDo>? = null
    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DBHelper(this)
        val db = dbHelper!!.writableDatabase
        dbHelper2 = DBHelper2(this)
        val db2 = dbHelper2!!.writableDatabase
        toDoList = arrayListOf()
        initRecyclerView()
        initAdapter()
        LoadData()
        toDoAdapter!!.addAll(toDoList)
        //toDoAdapter.addAll(toDoList);
//ClearAll();

        btnAdd = findViewById(R.id.addBtn)
        btnShow = findViewById(R.id.showComp)
        todoTitle = findViewById(R.id.todoTitle)
        doneList = ArrayList()
        todoDesc = findViewById(R.id.todoDesc)
        btnAdd!!.setOnClickListener(View.OnClickListener {
            val goalName = todoTitle!!.text.toString()
            val goalDesc = todoDesc!!.text.toString()
            if (goalName == "" || goalDesc == "") {
                val toast = Toast.makeText(
                    applicationContext,
                    "Не все поля введены!!!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                val cv = ContentValues()
                cv.put("name", goalName)
                cv.put("description", goalDesc)
                val rowID = db.insert("ToDoTable", null, cv)
                val mytodo = ToDo(rowID.toInt(), goalName, goalDesc)
                LoadData()
                //                    toDoList.add(0, mytodo);
                //                    toDoAdapter.addAll(toDoList);
                todoTitle!!.setText("")
                todoDesc!!.setText("")
            }
        })
        btnShow!!.setOnClickListener(View.OnClickListener {
            val i = Intent(baseContext, DoneToDo::class.java)
            startActivity(i)
        })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val cv = ContentValues()
            val db = dbHelper!!.writableDatabase
            //TODO check item for null
            val toDo = data!!.getSerializableExtra("todo") as ToDo
            val position = data.getIntExtra("position", -1)
            val status = data.getStringExtra("status")
            val id: Int = toDo.id
            if (status == "toSave") { //toDoList.set(position, toDo);
// подготовим значения для обновления
                cv.put("name", toDo.name)
                cv.put("description", toDo.description)
                // обновляем по id
                db.update("ToDoTable", cv, "rowid=$id", null)
                LoadData()
            }
            if (status == "toDelete") {
                val delCount = db.delete("ToDoTable", "id = $id", null)
                LoadData()
                //toDoList.remove(position);
            }
            if (status == "toDone") { //                doneList.add(toDo);
//                toDoList.remove(position);
                val cv2 = ContentValues()
                cv2.put("name", toDo.name)
                val db2 = dbHelper2!!.writableDatabase
                cv2.put("description", toDo.description)
                val rowID = db2.insert("DoneTable", null, cv2)
                val delCount = db.delete("ToDoTable", "id = $id", null)
                LoadData()
                //                    toDoList.add(0, mytodo);
//                    toDoAdapter.addAll(toDoList);
                todoTitle!!.setText("")
                todoDesc!!.setText("")
            }
            toDoAdapter!!.addAll(toDoList)
            //TODO check item for positive position
//toDoAdapter.updateItem(position, toDo);
        }
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

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onItemClick(position: Int, item: ToDo?) {
        val intent = Intent(this, UpdateToDo::class.java)
        intent.putExtra("todo", item)
        intent.putExtra("position", position)
        startActivityForResult(intent, 101)
    }

    fun LoadData() {
        val db = dbHelper!!.writableDatabase
        val c = db.query("ToDoTable", null, null, null, null, null, null)
        // ставим позицию курсора на первую строку выборки
// если в выборке нет строк, вернется false
        toDoList.clear()
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
                //toDoList.add(todo)
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(
                    LOG_TAG,
                    "ID = " + c.getInt(idColIndex) +
                            ", name = " + c.getString(nameColIndex) +
                            ", description = " + c.getString(descColIndex)
                )
                // переход на следующую строку
// а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext())
        } else c.close()
        Collections.reverse(toDoList)
        toDoAdapter!!.addAll(toDoList)
    }

    inner class DBHelper(context: Context?) :
        SQLiteOpenHelper(context, "myData", null, 1) {
        override fun onCreate(db: SQLiteDatabase) {
            Log.d(LOG_TAG, "--- onCreate database ---")
            // создаем таблицу с полями
            db.execSQL(
                "create table ToDoTable ("
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
