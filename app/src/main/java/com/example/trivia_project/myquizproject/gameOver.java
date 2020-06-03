package com.example.trivia_project.myquizproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class gameOver extends AppCompatActivity {
//    private ImageButton ReturnToMain;
    private Button goToScore;
    private TextView gameScore;

    @Override
    public void onBackPressed(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        String playerName = intent.getStringExtra("key_playerName");

        String playerScore = intent.getStringExtra("Key_score");
        String output=null;
        try {
            output = new serverConnection("2","BILBI",playerScore).execute().get();
           System.out.println("lkklkllkklklklklklklkl");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        ReturnToMain = (ImageButton) findViewById(R.id.ReturnToMain);
//        ReturnToMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openMain();
//            }
//        });

//        goToScore = (Button) findViewById(R.id.goToScore);
//        goToScore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openScore();
//            }
//        });


    }

//    public void openMain(){
//        Intent backToMainIntent = new Intent(this, MainActivity.class);
//        startActivity(backToMainIntent);
//    }

//    public void openScore(){
//        Intent goToScoreIntent = new Intent(this, results.class);
//        startActivity(goToScoreIntent);
//    }


}