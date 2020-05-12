package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private MainAdapter mainAdapter;

    private MainAdapter.ItemClickListener itemClickListener = new MainAdapter.ItemClickListener() {
        @Override
        public void onItemClick(PostItem postItem) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("postName", postItem.getPostName());
            bundle.putString("description", postItem.getDescription());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<PostItem> posts = new ArrayList<>();
        String text = "Нагато, известный прежде всего под псевдонимом Боли, является вымышленным персонажем из манги и аниме-сериала Наруто, созданного Масаси Кишимото. Нагато выступает в роли действующего лидера террористической организации по имени Акацуки, которая хочет поймать девять зверей, запечатанных в ниндзя со всего мира.";
        for (int i = 1; i < 100; i++) {
            posts.add(new PostItem("Post" + i ,text));
        }

        mainAdapter = new MainAdapter(posts, itemClickListener);
        recyclerView.setAdapter(mainAdapter);


    }
}