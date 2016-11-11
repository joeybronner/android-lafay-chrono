package fr.joeybronner.chronolafayapp;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.io.IOException;

import fr.joeybronner.chronolafayapp.utils.Constants;
import fr.joeybronner.chronolafayapp.utils.Utils;

public class MainActivity extends AppCompatActivity {

    Button bt25s, bt60s, bt90s, bt120s, bt180s, bt240s, btStop;
    ProgressBar progress;
    TextView tvChrono;
    MediaPlayer mp = new MediaPlayer();
    CountDownTimer countDownTimer;
    long SLIDER_TIMER;
    int progressStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.overrideFonts(getApplicationContext(), getWindow().getDecorView().getRootView());

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

        bt25s = (Button) this.findViewById(R.id.button25s);
        bt25s.setTypeface(null, Typeface.BOLD);

        bt60s = (Button) this.findViewById(R.id.button60s);
        bt60s.setTypeface(null, Typeface.BOLD);

        bt90s = (Button) this.findViewById(R.id.button90s);
        bt90s.setTypeface(null, Typeface.BOLD);

        bt120s = (Button) this.findViewById(R.id.button120s);
        bt120s.setTypeface(null, Typeface.BOLD);

        bt180s = (Button) this.findViewById(R.id.button180s);
        bt180s.setTypeface(null, Typeface.BOLD);

        bt240s = (Button) this.findViewById(R.id.button240s);
        bt240s.setTypeface(null, Typeface.BOLD);

        progress = (ProgressBar) this.findViewById(R.id.progress);

        btStop = (Button) this.findViewById(R.id.btStop);
        btStop.setTypeface(null, Typeface.BOLD);

        tvChrono = (TextView) this.findViewById(R.id.tvChrono);
        tvChrono.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.ttf"));

        btStop.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                actionStop();
            }
        });

        bt25s.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                actionStop();
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
        try {
            countDownTimer.cancel();
            countDownTimer = null;
        } catch (Exception e) {
            /* Nothing */
        }
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

                if(seconds.equals("05") && !mp.isPlaying()) {
                    Utils.playCountdownSound(getAssets(), mp);
                }

                tvChrono.setText(minutes + ":" + seconds);
            }

            public void onFinish() {
                actionStop();
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_settings:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.action_info:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }
        return true;
    }
}
