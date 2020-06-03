package com.example.trivia_project.myquizproject;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class serverConnection extends AsyncTask<Void, Void, String> {
private String selection,playerName,playerScore;

public serverConnection(String selection){
    this.selection = selection;
}

public  serverConnection(String selection ,String playerName,String playerScore ){
    this.selection = selection;
    this.playerScore = playerScore ;
    this.playerName = playerName;
}
    @Override
    protected String doInBackground(Void... params) {

        Socket clientSocket = null;
        //create socket connection to DB
        try {
            clientSocket = new Socket("192.168.43.194", 2222);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String outputFromServer = null;

        ObjectOutputStream objectOutput_server =null;

        BufferedReader inFromServer;

        String Gson_HighScoreArray = null;

        try {
            // Get from server
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            objectOutput_server = new ObjectOutputStream(clientSocket.getOutputStream());

            //getOutputStream() returns an output stream for writing bytes to this socket.
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(this.selection + "\n");
            switch (selection)
            {
                //get questions from DB
                case "1":
                    outputFromServer = inFromServer.readLine();
                    break;
                //update player name and score in DB
                case "2":
                    ArrayList<Players> high = new ArrayList<Players>();
                    Players p = new Players(this.playerName,this.playerScore);
                    high.add(p);
                    Gson json1 = new Gson();
                    String high_score_array = json1.toJson(high);
                    outToServer.writeBytes(high_score_array + "\n");
                    //objectOutput_server.writeObject(new Players(this.playerName,this.playerScore));
                    break;
                //get high score from DB
                case "3":
                    outputFromServer = inFromServer.readLine();
                    break;
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFromServer;
    }

    protected void onPostExecute(String Gson_questionArray) {
        super.onPostExecute(Gson_questionArray);

    }

}