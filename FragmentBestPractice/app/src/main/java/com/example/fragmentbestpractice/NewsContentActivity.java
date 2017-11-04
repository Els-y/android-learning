package com.example.fragmentbestpractice;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NewsContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);

        Intent intent = getIntent();
        String newsTitle = intent.getStringExtra("news_title");
        String newsContent = intent.getStringExtra("news_content");

        FragmentManager fragmentManager = getSupportFragmentManager();
        NewsContentFragment newsContentFragment= (NewsContentFragment) fragmentManager.findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle, newsContent);
    }

    public static void actionStart(Context context, String title, String content) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title", title);
        intent.putExtra("news_content", content);
        context.startActivity(intent);
    }
}
