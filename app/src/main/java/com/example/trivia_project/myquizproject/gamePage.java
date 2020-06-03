package com.example.trivia_project.myquizproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class gamePage extends Activity {
    private ImageButton ReturnToMain;
    private TextView gameScore, questionCounter, questionToShow,finalScoreToShow;
    private Button ansButton1, ansButton2, ansButton3, ansButton4;
    private MediaPlayer correctAnsSound, WrongAnsSound;
    static int qlimit=3;
    private int questionAmount=15;
    public ArrayList<Questions> question_By_Level_Array = new ArrayList<Questions>();

    public void background_animation(){
        RelativeLayout constraintLayout1 = findViewById(R.id.layout2);
        AnimationDrawable AnimationD1 = (AnimationDrawable) constraintLayout1.getBackground();
        AnimationD1.setEnterFadeDuration(4000);
        AnimationD1.getNumberOfFrames();
        AnimationD1.setExitFadeDuration(4000);
        AnimationD1.start();
    }

    private ArrayList<Questions> getAndSortQuestions(){
        //receive questions and level input from main activity
        ArrayList<Questions> level_QandA = new ArrayList<Questions>();
        Gson gson = new Gson();
        Intent intent1 = getIntent();

        String questionsArray = intent1.getStringExtra("key_questions");
        String questionLevel = intent1.getStringExtra("key_level");
        level_QandA = gson.fromJson(questionsArray, new TypeToken<List<Questions>>(){}.getType());

        //sorting by level input
        for (Questions ques : level_QandA){
            if (ques.getLevel().equals(questionLevel)){
                question_By_Level_Array.add(ques);
            }
        }
        return question_By_Level_Array;
    }

    @Override
    public void onBackPressed(){
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        background_animation();

        question_By_Level_Array = getAndSortQuestions();

        gameScore = (TextView) findViewById(R.id.gameScore);
        questionCounter = (TextView) findViewById(R.id.questionQounter);
        questionToShow = (TextView) findViewById(R.id.questionWindow);
        ReturnToMain = (ImageButton) findViewById(R.id.ReturnToMain);

        ansButton1 = (Button) findViewById(R.id.Answer1);
        ansButton2 = (Button) findViewById(R.id.answer2);
        ansButton3 = (Button) findViewById(R.id.answer3);
        ansButton4 = (Button) findViewById(R.id.answer4);

        gameScore.setText("0");
        questionCounter.setText("1");


        //Return to main screen button setting
        ReturnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(gamePage.this);
                builder.setMessage("Want To Quit?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openMainPage();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        newQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("score",gameScore.getText().toString());
        outState.putString("questionCounter",questionCounter.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gameScore.setText(savedInstanceState.getString("score"));
        gameScore.setText(savedInstanceState.getString("score"));
    }

    //controll of pop up messages
    public class ViewDialog {

        void dialogStartStop(final Dialog dialogName){
            dialogName.show();
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    dialogName.dismiss(); // when the task active then close the dialog
                    timer.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                }
            }, 1000);

        }

        void finalQuestionDialog(Activity activity, String finalScore) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.popup_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            FrameLayout mDialogNo = dialog.findViewById(R.id.playAgain);
            mDialogNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMainPage();
                }
            });

            FrameLayout mDialogOk = dialog.findViewById(R.id.highScore);
            mDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    openHighScorePage();
                }
            });
            finalScoreToShow = (TextView) dialog.findViewById(R.id.finalScore);
            finalScoreToShow.setText(finalScore);
            dialog.show();
        }

        void correctQuestionDialog(Activity activity) {

            final Dialog correctDialog = new Dialog(activity);
            correctDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            correctDialog.setCancelable(false);
            correctDialog.setContentView(R.layout.correct_layout);
            correctDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            dialogStartStop(correctDialog);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    //do something
                }
            }, 10000);
        }

        void wrongQuestionDialog(Activity activity) {

            final Dialog wrongDialog = new Dialog(activity);
            wrongDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            wrongDialog.setCancelable(false);
            wrongDialog.setContentView(R.layout.wrong_layout);
            wrongDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            dialogStartStop(wrongDialog);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    //do something
                }
            }, 10000);

        }
    }

    public void newQuestion(){

        final int questionNumber = (int)(Math.random() * questionAmount);
        questionToShow.setText(question_By_Level_Array.get(questionNumber).getQuestion());
        ansButton1.setText(question_By_Level_Array.get(questionNumber).getAnswer1());
        ansButton2.setText(question_By_Level_Array.get(questionNumber).getAnswer2());
        ansButton3.setText(question_By_Level_Array.get(questionNumber).getAnswer3());
        ansButton4.setText(question_By_Level_Array.get(questionNumber).getAnswer4());

        correctAnsSound = MediaPlayer.create(gamePage.this, R.raw.correctanswer);
        WrongAnsSound = MediaPlayer.create(this, R.raw.wronganswer);

        final String correctAnswer = question_By_Level_Array.get(questionNumber).getCorrectAnswer();
        final String ans1ToCompre = question_By_Level_Array.get(questionNumber).getAnswer1();
        final String ans2ToCompre = question_By_Level_Array.get(questionNumber).getAnswer2();
        final String ans3ToCompre = question_By_Level_Array.get(questionNumber).getAnswer3();
        final String ans4ToCompre = question_By_Level_Array.get(questionNumber).getAnswer4();

        ansButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_By_Level_Array.remove(questionNumber);
                questionAmount--;
                compare(ans1ToCompre, correctAnswer);
            }
        });

        ansButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_By_Level_Array.remove(questionNumber);
                questionAmount--;
                compare(ans2ToCompre, correctAnswer);
            }
        });

        ansButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_By_Level_Array.remove(questionNumber);
                questionAmount--;
                compare(ans3ToCompre, correctAnswer);
            }
        });

        ansButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_By_Level_Array.remove(questionNumber);
                questionAmount--;
                compare(ans4ToCompre, correctAnswer);
            }
        });

    }

    //compare selected answer to correct answer
    public void compare(String buttonString, String correctString){
        ViewDialog alert = new ViewDialog();

        if(buttonString.equals(correctString)){
            correctAnsSound.start();
            alert.correctQuestionDialog(gamePage.this);
            scoreCounter();
        }
        else{
            WrongAnsSound.start();
            alert.wrongQuestionDialog(gamePage.this);
        }
        questHandler();
    }

    public void scoreCounter(){
        String score = gameScore.getText().toString();
        int grade = Integer.parseInt(score);
        grade=grade +10;
        String scoreStr = String.valueOf(grade);
        gameScore.setText(scoreStr);
    }

    public void questHandler(){
        String counter = questionCounter.getText().toString();
        int num2 = Integer.parseInt(counter);
        num2++;
        if(num2==16){
            sleep(1000);
            Intent intent = getIntent();
            String playerName = intent.getStringExtra("key_playerName");

            String playerScore = (String)(gameScore.getText().toString());
            String output=null;
            //update name and score of player in DB
            try {
                output = new serverConnection("2",playerName,playerScore).execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
// show game over popup
            ViewDialog alert = new ViewDialog();
            alert.finalQuestionDialog(gamePage.this,playerScore);
        }
        else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    newQuestion();
                }
            },1000);

            String counterStr = String.valueOf(num2);
            questionCounter.setText(counterStr);
        }
    }

    public static boolean sleep(int time){

        try {
            Thread.sleep(time);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  true;
    }

    public void openHighScorePage(){
        Intent gameOverIntent = new Intent(gamePage.this, HighScore.class);
        startActivity(gameOverIntent);
    }

    public void openMainPage(){
        Intent main_Page = new Intent(this, MainActivity.class);
        main_Page.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(main_Page);
    }
}
