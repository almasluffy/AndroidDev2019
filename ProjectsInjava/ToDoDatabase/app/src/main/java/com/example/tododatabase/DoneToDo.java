package com.example.tododatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoneToDo extends AppCompatActivity implements  ToDoAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private ToDoAdapter toDoAdapter;
    final String LOG_TAG = "myLogs";

    DBHelper2 dbHelper2;

    private List<ToDo> doneList = new ArrayList<>();

    private Button btnClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_to_do);
        dbHelper2 = new DBHelper2(this);
        final SQLiteDatabase db2 = dbHelper2.getWritableDatabase();
        initRecyclerView();
        initAdapter();
        LoadData();
        toDoAdapter.addAll(doneList);

        btnClear = findViewById(R.id.clearBtn);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clearCount = db2.delete("DoneTable", null, null);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "All goals was cleared", Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });

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
    public void onItemClick(int position, ToDo item) {

    }

    public void LoadData(){
        SQLiteDatabase db = dbHelper2.getWritableDatabase();
        Cursor c = db.query("DoneTable", null, null, null, null, null, null);
        doneList.clear();
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
                doneList.add(todo);
                // получаем значения по номерам столбцов и пишем все в лог
//                        Log.d(LOG_TAG,
//                                "ID = " + c.getInt(idColIndex) +
//                                        ", name = " + c.getString(nameColIndex) +
//                                        ", description = " + c.getString(descColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            c.close();
        Collections.reverse(doneList);
        toDoAdapter.addAll(doneList);
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
