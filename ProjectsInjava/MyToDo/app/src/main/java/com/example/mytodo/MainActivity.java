package com.example.mytodo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ToDoAdapter.OnItemClickListener{


    private RecyclerView recyclerView;
    private ToDoAdapter toDoAdapter;

    final String FILENAME = "myfile.txt";

    private Button btnAdd;
    private Button btnShow;
    private EditText todoTitle;
    private EditText todoDesc;


    private List<ToDo> toDoList;
    private List<ToDo> doneList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        initAdapter();
        LoadData();
        toDoAdapter.addAll(toDoList);
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
                    ToDo mytodo = new ToDo(goalName, goalDesc);
                    toDoList.add(0, mytodo);
                    toDoAdapter.addAll(toDoList);
                    SaveData();
                    //toDoAdapter.addItem(mytodo);
                    //System.out.println(goalName);
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

                //TODO check item for null
                ToDo toDo = (ToDo) data.getSerializableExtra("todo");
                int position = data.getIntExtra("position", -1);
                String status = data.getStringExtra("status");
                if(status.equals("toSave")){
                    toDoList.set(position, toDo);
                }
                if(status.equals("toDelete")){
                    toDoList.remove(position);
                }
                if(status.equals("toDone")){
                    doneList.add(toDo);
                    toDoList.remove(position);
                    SharedPreferences sharedPreferences = getSharedPreferences("donePref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(doneList);
                    editor.putString("todo done", json);
                    editor.apply();
                }
                toDoAdapter.addAll(toDoList);
                SaveData();
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
        ClearAll();
    }

    @Override
    public void onItemClick(int position, ToDo item) {
        Intent intent = new Intent(this, UpdateToDo.class);
        intent.putExtra("todo", item);
        intent.putExtra("position", position);
        startActivityForResult(intent, 101);
    }

    public void SaveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(toDoList);
        editor.putString("todo tasks", json);
        editor.apply();
    }
    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("todo tasks", null);
        Type type = new TypeToken<ArrayList<ToDo>>(){}.getType();
        toDoList = gson.fromJson(json,type );
        //toDoAdapter.setToDoList(toDoList);

        if(toDoList == null){
            toDoList = new ArrayList<>();
        }

    }

    public void ClearAll(){
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit(); //// for Clear all date
    }
    private boolean isExternalStorageWritable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Log.i("State","Yes. it is writable!");
            return true;
        }else{
            return false;
        }
    }

    public void  writeToFile(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isExternalStorageWritable() && checkPermissionWrite()) {
                File textFile = new File(Environment.getExternalStorageDirectory(), FILENAME);

                try {
                    FileOutputStream fos = new FileOutputStream(textFile);
                    Gson gson = new Gson();
                    String json = gson.toJson(toDoList);
                    fos.write(json.getBytes());
                    fos.close();

                    Toast.makeText(this, "File saved!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }
    public void  readFile(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isExternalStorageReadable() && checkPermissionRead()) {
                File textFile = new File(Environment.getExternalStorageDirectory(), FILENAME);
                String myToDoJson = "";
                try {
                    FileInputStream fis = new FileInputStream(textFile);
                    if(fis!=null){
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);
                        String line  = null;
                        while((line = br.readLine())!=null){
                            myToDoJson  = line;
                        }
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ToDo>>(){}.getType();
                        toDoList = gson.fromJson(myToDoJson,type);
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
            }
        }
    }


    boolean checkPermissionWrite(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    boolean checkPermissionRead(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    private boolean isExternalStorageReadable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())){
            Log.i("State","Yes. it is readable!");
            return true;
        }else{
            return false;
        }
    }






}
