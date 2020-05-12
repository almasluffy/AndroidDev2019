package com.example.tododatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ToDoAdapter.OnItemClickListener{


    private RecyclerView recyclerView;
    private ToDoAdapter toDoAdapter;
    final String LOG_TAG = "myLogs";

    DBHelper dbHelper;
    DBHelper2 dbHelper2;


    private Button btnAdd;
    private Button btnShow;
    private EditText todoTitle;
    private EditText todoDesc;


    private List<ToDo> toDoList = new ArrayList<>();
    private List<ToDo> doneList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        dbHelper = new DBHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        dbHelper2 = new DBHelper2(this);
        final SQLiteDatabase db2 = dbHelper2.getWritableDatabase();

        initRecyclerView();
        initAdapter();
        LoadData();
        toDoAdapter.addAll(toDoList);
        //toDoAdapter.addAll(toDoList);
        //ClearAll();


        btnAdd = findViewById(R.id.addBtn);
        btnShow = findViewById(R.id.showComp);
        todoTitle = findViewById(R.id.todoTitle);
        doneList = new ArrayList<>();

        todoDesc = findViewById(R.id.todoDesc);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalName = todoTitle.getText().toString();
                String goalDesc = todoDesc.getText().toString();
                if(goalName.equals("") || goalDesc.equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(),"Не все поля введены!!!",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    ContentValues cv = new ContentValues();

                    cv.put("name", goalName);
                    cv.put("description", goalDesc);
                    long rowID = db.insert("ToDoTable", null, cv);
                    ToDo mytodo = new ToDo((int)rowID, goalName, goalDesc);
                    LoadData();

//                    toDoList.add(0, mytodo);
//                    toDoAdapter.addAll(toDoList);
                    todoTitle.setText("");
                    todoDesc.setText("");

                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), DoneToDo.class);
                startActivity(i);
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            ContentValues cv = new ContentValues();
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            //TODO check item for null
            ToDo toDo = (ToDo) data.getSerializableExtra("todo");
            int position = data.getIntExtra("position", -1);
            String status = data.getStringExtra("status");
            int id = toDo.getId();
            if(status.equals("toSave")){
                //toDoList.set(position, toDo);

                // подготовим значения для обновления
                cv.put("name", toDo.getName());
                cv.put("description", toDo.getDescription());
                // обновляем по id
                db.update("ToDoTable", cv, "rowid=" + id, null);
                LoadData();
            }
            if(status.equals("toDelete")){
                int delCount = db.delete("ToDoTable", "id = " + id, null);
                LoadData();
                //toDoList.remove(position);

            }
            if(status.equals("toDone")){
//                doneList.add(toDo);
//                toDoList.remove(position);
                ContentValues cv2 = new ContentValues();
                cv2.put("name", toDo.getName());
                SQLiteDatabase db2 = dbHelper2.getWritableDatabase();
                cv2.put("description", toDo.getDescription());
                long rowID = db2.insert("DoneTable", null, cv2);
                int delCount = db.delete("ToDoTable", "id = " + id, null);
                LoadData();

//                    toDoList.add(0, mytodo);
//                    toDoAdapter.addAll(toDoList);
                todoTitle.setText("");
                todoDesc.setText("");

            }
            toDoAdapter.addAll(toDoList);
            //TODO check item for positive position
            //toDoAdapter.updateItem(position, toDo);


        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initAdapter() {
        toDoAdapter = new ToDoAdapter(this);
        recyclerView.setAdapter(toDoAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(int position, ToDo item) {
        Intent intent = new Intent(this, UpdateToDo.class);
        intent.putExtra("todo", item);
        intent.putExtra("position", position);
        startActivityForResult(intent, 101);
    }

    public void LoadData(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query("ToDoTable", null, null, null, null, null, null);
        toDoList.clear();
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int descColIndex = c.getColumnIndex("description");



            do {
//                        System.out.println(c.getInt(idColIndex));
                ToDo todo = new ToDo(c.getInt((idColIndex)), c.getString(nameColIndex),c.getString(descColIndex));
                toDoList.add(todo);
                // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex) +
                                        ", description = " + c.getString(descColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            c.close();
        Collections.reverse(toDoList);
        toDoAdapter.addAll(toDoList);
    }


    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myData", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table ToDoTable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "description text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    class DBHelper2 extends SQLiteOpenHelper {

        public DBHelper2(Context context) {
            // конструктор суперкласса
            super(context, "myData2", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table DoneTable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "description text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }

}
