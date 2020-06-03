package com.example.trivia_project.myquizproject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerListAdapter extends ArrayAdapter<Players> {

    private Context mContext;
    int mResource;

    public PlayerListAdapter(Context context, int resource, ArrayList<Players> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        String name = getItem(position).getPlayerName();
        String score = getItem(position).getScore();

        Players player = new Players(name,score);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvScore = (TextView) convertView.findViewById(R.id.textView2);

        tvName.setText((name));
        tvScore.setText(score);

        return convertView;
    }
}
