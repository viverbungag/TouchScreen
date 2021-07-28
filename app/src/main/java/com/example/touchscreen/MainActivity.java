package com.example.touchscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.net.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {
    String IPadrress;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.screen);
        final Context context = this;


        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                IPadrress = userInput.getText().toString();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


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

                    new MyTask(coordinates, IPadrress).execute();

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
    String IPadrress = "";

    public MyTask(String coordinates, String IPadrress){
        this.coordinates = coordinates;
        this.IPadrress = IPadrress;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            System.out.println(this.coordinates);
            Socket s = new Socket(IPadrress, 8888);
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println(this.coordinates);
            pr.flush();

        } catch (Exception ex){

        }
        return null;
    }

}
