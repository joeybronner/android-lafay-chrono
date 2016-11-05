package fr.joeybronner.chronolafayapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import fr.joeybronner.chronolafayapp.utils.Constants;
import fr.joeybronner.chronolafayapp.utils.Utils;

public class MainActivity extends AppCompatActivity {

    ImageView ivWindows, ivMac;
    Button bt25s;
    ProgressBar progress;
    HTextView hTextView;
    Thread progressBarThread;
    Button btStop;
    boolean updatePB;
    double SLIDER_TIMER, TMP_SLIDER_TIMER;
    int progressStatus;
    long millis;
    int invisible = View.INVISIBLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt25s = (Button) this.findViewById(R.id.button25s);
        progress = (ProgressBar) this.findViewById(R.id.progress);
        btStop = (Button) this.findViewById(R.id.btStop);

        /*hTextView = (HTextView) findViewById(R.id.text);
        hTextView.setAnimateType(HTextViewType.TYPER);
        hTextView.animateText(getResources().getString(R.string.app_product_name));*/

        Utils.overrideFonts(getApplicationContext(), getWindow().getDecorView().getRootView());

        bt25s.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                SLIDER_TIMER = 25000;
                updatePB = true;
                btStop.setVisibility(View.VISIBLE);
                progressBarThread = new Thread(new Runnable() {
                    public void run() {
                        millis = System.currentTimeMillis();
                        progress.setMax(100);
                        progress.setProgress(0);
                        TMP_SLIDER_TIMER = 0;
                        while(updatePB==true) {
                            try {
                                TMP_SLIDER_TIMER += 100;
                                progressStatus = doWork(millis);
                                progress.setProgress(progressStatus);
                                progress.refreshDrawableState();
                                if (progressStatus>100) {
                                    progress.setProgress(0);
                                    updatePB=false;
                                    btStop.setVisibility(invisible);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // TODO: Notify at the end of the progressbar to set the button to visibility false
                        //btStop.setVisibility(View.INVISIBLE);
                    }
                });
                progressBarThread.start();
            }
        });
    }

    private int doWork(long millis) throws InterruptedException {
        long diff = System.currentTimeMillis() - millis;
        return (int) ((diff*100)/SLIDER_TIMER);
    }
}
