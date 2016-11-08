package fr.joeybronner.chronolafayapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import fr.joeybronner.chronolafayapp.utils.Constants;
import fr.joeybronner.chronolafayapp.utils.Utils;

public class MainActivity extends AppCompatActivity {

    Button bt25s, btStop;
    ProgressBar progress;
    TextView tvChrono;
    CountDownTimer countDownTimer;
    long SLIDER_TIMER;
    int progressStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt25s = (Button) this.findViewById(R.id.button25s);
        progress = (ProgressBar) this.findViewById(R.id.progress);
        btStop = (Button) this.findViewById(R.id.btStop);

        tvChrono = (TextView) this.findViewById(R.id.tvChrono);

        Utils.overrideFonts(getApplicationContext(), getWindow().getDecorView().getRootView());

        btStop.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                actionStop();
            }
        });

        bt25s.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                initTimer(25000);
                resetProgressBar();
                btStop.setVisibility(View.VISIBLE);
                startTimer();
            }
        });
    }

    private void actionStop() {
        resetProgressBar();
        resetTimer();
        btStop.setVisibility(View.INVISIBLE);
    }

    private void initTimer(long time) {
        SLIDER_TIMER = time;
    }

    private void resetTimer() {
        tvChrono.setText("train!!");
        countDownTimer.cancel();
        countDownTimer = null;
    }

    private void resetProgressBar() {
        progress.setMax(100);
        progress.setProgress(0);
        progress.refreshDrawableState();
    }

    private int getProgress(long millis) {
        long diff = SLIDER_TIMER - millis;
        return (int) ((diff*100)/SLIDER_TIMER);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(SLIDER_TIMER, 1000) {

            public void onTick(long millisUntilFinished) {
                progressStatus = getProgress(millisUntilFinished);
                progress.setProgress(progressStatus);
                progress.refreshDrawableState();

                String seconds = String.format("%02d", millisUntilFinished / 1000);
                String minutes = String.format("%02d", millisUntilFinished / 1000 / 60);

                tvChrono.setText(minutes + ":" + seconds);
            }

            public void onFinish() {
                actionStop();
            }

        }.start();
    }
}
