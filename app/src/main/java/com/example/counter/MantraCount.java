package com.example.counter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MantraCount extends AppCompatActivity {

    TextView mantraView;
    Button btn;
    private int count = 0;
    private String TAG;
    TextView timerText;
    Button stopStartButton;

    Timer timer;
    TimerTask timerTask;
    double time = 0.0;

    boolean timerStarted,countStarted = false;
   // SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
   // SharedPreferences.Editor editor = shrd.edit();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantra_count);
          mantraView = (TextView)findViewById(R.id.mantra);
          btn = (Button) findViewById(R.id.btnCount);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        LoadData();
        btn.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                      count++;
                    mantraView.setText(Integer.toString(count));
                   //   mantraView.setText(String.valueOf(count));
                //    String msg = mantraView.getText().toString();

                //    SharedPreferences shrd = getSharedPreferences("Count", MODE_PRIVATE);
                //    SharedPreferences.Editor editor = shrd.edit();

                  //  editor.putInt("str", count);
               //    editor.putString("str", msg);
               //    editor.apply();
               //     mantraView.setText(msg);


            }
        });

        //Get the value of shared prefrences back
   //    SharedPreferences getShared = getSharedPreferences("Count", MODE_PRIVATE);
    //   int value = getShared.getInt("str", count);
   //    String value = getShared.getString("str", "00");
    //   mantraView.setText(value);

        timerText = (TextView) findViewById(R.id.timerText);
        stopStartButton = (Button) findViewById(R.id.startStopButton);

        timer = new Timer();
    }

    public void resetCount(View view)
    {
        AlertDialog.Builder resetCountAlert = new AlertDialog.Builder(this);
        resetCountAlert.setTitle("Reset Count");
        resetCountAlert.setMessage("Are you sure you want to reset the Mantra Count?");
        resetCountAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(count!=0)
                {
     //               editor.clear();
                    //count.cancel();
                    //setButtonUI("Mantra", R.color.green);
                    count = 00;
                    countStarted = false;
                    mantraView.setText("00");

                }
            }
        });

        resetCountAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //do nothing
            }
        });

        resetCountAlert.show();

    }

        public void resetTapped(View view)
        {
            AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
            resetAlert.setTitle("Reset Timer");
            resetAlert.setMessage("Are you sure you want to reset the timer?");
            resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    if(timerTask != null)
                    {
                        timerTask.cancel();
                        setButtonUI("START", R.color.white);
                        time = 0.0;
                        timerStarted = false;
                        timerText.setText(formatTime(0,0,0));


                    }
                }
            });

            resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    //do nothing
                }
            });

            resetAlert.show();

        }

        public void startStopTapped(View view)
        {
            if(timerStarted == false)
            {
                timerStarted = true;
                setButtonUI("PAUSE", R.color.white);

                startTimer();
            }
            else
            {
                timerStarted = false;
                setButtonUI("START", R.color.white);

                timerTask.cancel();
            }
        }

        private void setButtonUI(String start, int color)
        {
            stopStartButton.setText(start);
            stopStartButton.setTextColor(ContextCompat.getColor(this, color));
        }

        private void startTimer()
        {
            timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            time++;
                            timerText.setText(getTimerText());
                        }
                    });
                }

            };
            timer.scheduleAtFixedRate(timerTask, 0 ,1000);
        }


        private String getTimerText()
        {
            int rounded = (int) Math.round(time);

            int seconds = ((rounded % 86400) % 3600) % 60;
            int minutes = ((rounded % 86400) % 3600) / 60;
            int hours = ((rounded % 86400) / 3600);

            return formatTime(seconds, minutes, hours);
        }

        private String formatTime(int seconds, int minutes, int hours)
        {
            return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
        }

        public void saveData()
        {
            SharedPreferences sharedPreferences = getSharedPreferences("savecount",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                     editor.putInt("countvalue", count);
                     editor.apply();
        }
        public void LoadData()
        {
            SharedPreferences sharedPreferences = getSharedPreferences("savecount",MODE_PRIVATE); ;
           count = sharedPreferences.getInt("countvalue",MODE_PRIVATE);
          //  Double.parseDouble(getSharedPreferences("saveTime", MODE_PRIVATE).getString("counttime", String.valueOf(MODE_PRIVATE)));

           //   mantraView.setText(String.valueOf(count));
            mantraView.setText(Integer.toString(count));
        }
        public void onPause()
        {
            super.onPause();
            saveData();
        }


}
