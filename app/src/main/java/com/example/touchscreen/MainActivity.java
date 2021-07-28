package com.example.touchscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.net.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.screen);



        View.OnTouchListener handleTouch = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                String action = "";
                String coordinates = " ";

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        action = "cancel";
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        action = "cancel";
                        break;
                }

                coordinates = String.valueOf(x) + "," + String.valueOf(y) + "," + action;


                try{

                    new MyTask(coordinates).execute();

                }catch(Exception ex){
                    System.out.println(ex);
                }




                return true;
            }
        };
        view.setOnTouchListener(handleTouch);

    }


}

class MyTask extends AsyncTask<Void, Void, Void> {
    String coordinates = "";

    public MyTask(String coordinates){
        this.coordinates = coordinates;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            System.out.println(this.coordinates);
            Socket s = new Socket("192.168.0.29", 8888);
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println(this.coordinates);
            pr.flush();

        } catch (Exception ex){

        }
        return null;
    }

}
