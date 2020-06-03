package com.example.trivia_project.myquizproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HighScore extends AppCompatActivity {
    private Button main_page;

    public void highScoreAdapter(String Gson_HighScoreArray,ListView mListView){
        PlayerListAdapter adapter;
        Gson gson = new Gson();

        ArrayList<Players> highScoreArray = new ArrayList<Players>();
        highScoreArray = gson.fromJson(Gson_HighScoreArray, new TypeToken<List<Players>>(){}.getType());
        adapter = new PlayerListAdapter(this, R.layout.adapter_view_layout,highScoreArray);
        mListView.setAdapter(adapter);
    }

    public void open_main_page(){
        Intent main_page = new Intent(this,MainActivity.class);
        main_page.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(main_page);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String highScore_array=null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        ListView mListView = (ListView) findViewById(R.id.ListView_HighScore);
        main_page = (Button) findViewById(R.id.newGameButton);

        try {
            highScore_array = new serverConnection("3").execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        highScoreAdapter(highScore_array,mListView);

        main_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_main_page();
            }
        });
    }
}

