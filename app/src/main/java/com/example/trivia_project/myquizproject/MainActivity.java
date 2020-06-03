package com.example.trivia_project.myquizproject;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private Button HighScore_page,game_page;
    boolean start_music_flag=false;
    int get_questions_flag =0;
    String QuestionArray = null;
    @Override
    public void onBackPressed(){
    }

    public void background_animation(){
        RelativeLayout constraintLayout1 = findViewById(R.id.layout1);
        AnimationDrawable AnimationD1 = (AnimationDrawable) constraintLayout1.getBackground();
        AnimationD1.setEnterFadeDuration(4000);
        AnimationD1.getNumberOfFrames();
        AnimationD1.setExitFadeDuration(4000);
        AnimationD1.start();
    }

    public void background_music(){
        final MediaPlayer mainMusic = MediaPlayer.create(this, R.raw.mainmusic);
        final Switch soundOnOff = (Switch) findViewById(R.id.muteSwitch);
        System.out.println(start_music_flag);
        mainMusic.start();
        soundOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) mainMusic.pause();
                else mainMusic.start();
            }
        });
    }

    public void open_highScore_page(){
        Intent highScore_activity = new Intent(this, HighScore.class);
        startActivity(highScore_activity);
    }

    public void open_game_page(String playerName, String QuestionArray,String CAT_selection){
        Intent gameActivity = new Intent(this, gamePage.class);
        gameActivity.putExtra("key_questions", QuestionArray);
        gameActivity.putExtra("key_level", CAT_selection);
        gameActivity.putExtra("key_playerName", playerName);

        startActivity(gameActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        background_animation();
        background_music();

        HighScore_page = (Button) findViewById(R.id.goToScore);
        game_page = (Button) findViewById(R.id.gameStart);
        final Spinner spinner = (Spinner) findViewById(R.id.categoriesSpinner);

        final EditText player_name = (EditText)findViewById(R.id.playerName);

        final String[] categories = new String[]{"Select Trivia level","Easy", "Medium", "Tough"};
        ArrayAdapter<String> Categories_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinner.setAdapter(Categories_adapter);

        game_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (get_questions_flag==0) {
                    try {
                        QuestionArray = new serverConnection("1").execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    get_questions_flag=1;
                }

                String playerName = player_name.getText().toString();
                String CAT_selection = spinner.getSelectedItem().toString();
                if((!playerName.equals(""))&&(categories[0]!=CAT_selection))
                    open_game_page(playerName,QuestionArray,CAT_selection);
                else
                    Toast.makeText(getApplicationContext(), "Please Select name & level !", Toast.LENGTH_SHORT).show();
            }
        });

        HighScore_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_highScore_page();
            }
        });

    }

}