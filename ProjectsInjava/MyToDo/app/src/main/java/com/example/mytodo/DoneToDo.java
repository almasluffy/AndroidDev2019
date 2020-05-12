package com.example.mytodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DoneToDo extends AppCompatActivity implements  ToDoAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private ToDoAdapter toDoAdapter;

    private List<ToDo> doneList = new ArrayList<>();

    private Button btnClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_to_do);
        initRecyclerView();
        initAdapter();
        LoadData();
        toDoAdapter.addAll(doneList);

        btnClear = findViewById(R.id.clearBtn);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("donePref", MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
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
        SharedPreferences sharedPreferences = getSharedPreferences("donePref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("todo done", null);
        Type type = new TypeToken<ArrayList<ToDo>>(){}.getType();
        doneList = gson.fromJson(json,type );

        if(doneList == null){
            doneList = new ArrayList<>();
        }

    }

}
