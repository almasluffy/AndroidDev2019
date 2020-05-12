package com.example.mytodo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateToDo extends AppCompatActivity {

    private EditText updText;
    private EditText updDesc;
    private Button btnSave;
    private Button btnDone;
    private Button btnDelete;

    private ToDo todo;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_to_do);

        updText = findViewById(R.id.updatedText);
        updDesc = findViewById(R.id.updatedDesc);
        btnSave = findViewById(R.id.saveBtn);
        btnDone = findViewById(R.id.doneBtn);
        btnDelete = findViewById(R.id.deleteBtn);

        setToDoData();

        updText.setText(todo.getName());
        updDesc.setText(todo.getDescription());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(updText.getText().toString().equals("") || updDesc.getText().toString().equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(),"Не все поля введены!!!",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    todo.setName(updText.getText().toString());
                    todo.setDescription(updDesc.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("todo", todo);
                    intent.putExtra("position", position);
                    intent.putExtra("status", "toSave");
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("todo", todo);
                intent.putExtra("position", position);
                intent.putExtra("status", "toDelete");
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("todo", todo);
                intent.putExtra("position", position);
                intent.putExtra("status", "toDone");
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setToDoData() {
        if (getIntent().hasExtra("todo")) {
            //Todo check ToDo entity for null
            todo = (ToDo)getIntent().getSerializableExtra("todo");
            position = (int) getIntent().getIntExtra("position", -1);
            System.out.println(position);
            System.out.println(todo.getName());
            updText.setText(todo.getName());
            updDesc.setText(todo.getDescription());
        }
    }
}
