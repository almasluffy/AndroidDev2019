package com.example.todokotlinfile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type
import java.util.*

class MainActivity : AppCompatActivity(), ToDoAdapter.OnItemClickListener {
    private var recyclerView: RecyclerView? = null
    private var toDoAdapter: ToDoAdapter? = null
    val FILENAME = "myfile.txt"
    private var btnAdd: Button? = null
    private var btnShow: Button? = null
    private var todoTitle: EditText? = null
    private var todoDesc: EditText? = null
    private var toDoList: MutableList<ToDo?>? = null
    private var doneList: MutableList<ToDo>? = null
    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        initAdapter()
        LoadData()
        toDoAdapter!!.addAll(toDoList)
        //ClearAll();
        btnAdd = findViewById(R.id.addBtn)
        btnShow = findViewById(R.id.showComp)
        todoTitle = findViewById(R.id.todoTitle)
        doneList = ArrayList()
        todoDesc = findViewById(R.id.todoDesc)
        btnAdd!!.setOnClickListener(View.OnClickListener {
            val goalName = todoTitle!!.getText().toString()
            val goalDesc = todoDesc!!.getText().toString()
            if ((goalName == "") || (goalDesc == "")) {
                val toast = Toast.makeText(
                    applicationContext,
                    "Не все поля введены!!!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                val mytodo = ToDo(goalName, goalDesc)
                toDoList!!.add(0, mytodo)
                toDoAdapter!!.addAll(toDoList)
                SaveData()
                //toDoAdapter.addItem(mytodo);
                //System.out.println(goalName);
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
        if (resultCode == Activity.RESULT_OK) { //TODO check item for null
            val toDo = data!!.getSerializableExtra("todo") as ToDo
            val position = data.getIntExtra("position", -1)
            val status = data.getStringExtra("status")
            if (status == "toSave") {
                toDoList!![position] = toDo
            }
            if (status == "toDelete") {
                toDoList!!.removeAt(position)
            }
            if (status == "toDone") {
                doneList!!.add(toDo)
                toDoList!!.removeAt(position)
                val sharedPreferences =
                    getSharedPreferences("donePref", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val gson = Gson()
                val json: String = gson.toJson(doneList)
                editor.putString("todo done", json)
                editor.apply()
            }
            toDoAdapter!!.addAll(toDoList)
            SaveData()
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
        ClearAll()
    }

    override fun onItemClick(position: Int, item: ToDo?) {
        val intent = Intent(this, UpdateToDo::class.java)
        intent.putExtra("todo", item)
        intent.putExtra("position", position)
        startActivityForResult(intent, 101)
    }

    fun SaveData() {
        val sharedPreferences =
            getSharedPreferences("mypref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(toDoList)
        editor.putString("todo tasks", json)
        editor.apply()
    }

    fun LoadData() {
        val sharedPreferences =
            getSharedPreferences("mypref", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("todo tasks", null)
        val type: Type =
            object : TypeToken<ArrayList<ToDo?>?>() {}.getType()
        toDoList = gson.fromJson(json, type)
        //toDoAdapter.setToDoList(toDoList);
        if (toDoList == null) {
            toDoList = ArrayList()
        }
    }

    fun ClearAll() {
        val sharedPreferences =
            getSharedPreferences("mypref", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().commit() //// for Clear all date
    }

    private val isExternalStorageWritable: Boolean
        private get() = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            Log.i("State", "Yes. it is writable!")
            true
        } else {
            false
        }

    fun writeToFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isExternalStorageWritable && checkPermissionWrite()) {
                val textFile =
                    File(Environment.getExternalStorageDirectory(), FILENAME)
                try {
                    val fos = FileOutputStream(textFile)
                    val gson = Gson()
                    val json: String = gson.toJson(toDoList)
                    fos.write(json.toByteArray())
                    fos.close()
                    Toast.makeText(this, "File saved!", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    100
                )
            }
        }
    }

    fun readFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isExternalStorageReadable && checkPermissionRead()) {
                val textFile =
                    File(Environment.getExternalStorageDirectory(), FILENAME)
                var myToDoJson: String? = ""
                try {
                    val fis: FileInputStream = FileInputStream(textFile)
                    if (fis != null) {
                        val isr = InputStreamReader(fis)
                        val br = BufferedReader(isr)
                        var line: String? = null
                        while (br.readLine().also { line = it } != null) {
                            myToDoJson = line
                        }
                        val gson = Gson()
                        val type: Type =
                            object : TypeToken<ArrayList<ToDo?>?>() {}.getType()
                        toDoList = gson.fromJson(myToDoJson, type)
                        fis.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    101
                )
            }
        }
    }

    fun checkPermissionWrite(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionRead(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private val isExternalStorageReadable: Boolean
        private get() {
            return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()) {
                Log.i("State", "Yes. it is readable!")
                true
            } else {
                false
            }
        }
}
