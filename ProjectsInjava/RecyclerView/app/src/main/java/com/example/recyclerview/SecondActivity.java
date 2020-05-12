package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;

public class SecondActivity extends AppCompatActivity {

    private TextView postNameView;
    private TextView descriptionView;
    private ImageView image;
    private ImageView profileImage;
    private Button share;

    private String postName;
    private String postDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        postNameView = findViewById(R.id.profName);
        descriptionView = findViewById(R.id.profDesc);
        image = findViewById(R.id.imageView2);
        profileImage = findViewById(R.id.imageView);
        share = findViewById(R.id.shaeBtn);

        final Bundle extras = getIntent().getExtras();
        postName= extras.getString("postName");
        postDesc= extras.getString("description");

        postNameView.setText(postName);
        descriptionView.setText(postDesc);
//        profileImage.setImageResource(R.drawable.pain);
//        image.setImageResource(R.drawable.naruto);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String shareBody = postName + "\n" + postDesc;
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(sharingIntent, "send"));

            }
        });
    }
}

