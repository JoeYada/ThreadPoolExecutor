package com.example.joe.threadpoolexecutor;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    private TextView textView1, textView2, textView3, textView4;
    private UIHanler uiHanler;
    private CustomThreadPoolManager customThreadPoolManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.tvThread1);
        textView2 = findViewById(R.id.tvThread2);
        textView3 = findViewById(R.id.tvThread3);
        textView4 = findViewById(R.id.tvThread4);
        progressBar1 = findViewById(R.id.progress1);
        progressBar2 = findViewById(R.id.progress2);
        progressBar3 = findViewById(R.id.progress3);
        progressBar4 = findViewById(R.id.progress4);

        uiHanler = new UIHanler(getMainLooper(),
                textView1, textView2, textView3, textView4,
                progressBar1,progressBar2,progressBar3, progressBar4);
        customThreadPoolManager = CustomThreadPoolManager.getINSTANCE();
        customThreadPoolManager.setHandler(uiHanler);
    }

    public void addCallabe(View view) {
        customThreadPoolManager.addRunnable();
    }

    public static class UIHanler extends Handler{
        private WeakReference<TextView> tvFirstThread, tvSecondThread, tvThirdthread, tvFourthThread;
        private WeakReference<ProgressBar> firstProgress, secondProgress, thridProgress, fourthProgress;

        public UIHanler(Looper looper,
                        TextView tvFirstThread,
                        TextView tvSecondThread,
                        TextView tvthirdthread,
                        TextView tvFourthThread,
                        ProgressBar firstProgress,
                        ProgressBar secondProgress,
                        ProgressBar thirdProgress,
                        ProgressBar fourthProgress){
            super(looper);
            this.tvFirstThread = new WeakReference<>(tvFirstThread);
            this.tvSecondThread = new WeakReference<>(tvSecondThread);
            this.tvThirdthread = new WeakReference<>(tvthirdthread);
            this.tvFourthThread = new WeakReference<>(tvFourthThread);
            this.firstProgress = new WeakReference<>(firstProgress);
            this.secondProgress = new WeakReference<>(secondProgress);
            this.thridProgress = new WeakReference<>(thirdProgress);
            this.fourthProgress = new WeakReference<>(fourthProgress);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    getProgress(msg, firstProgress.get(), tvFirstThread.get());
                    break;
                case 2:
                    getProgress(msg, secondProgress.get(), tvSecondThread.get());
                    break;
                case 3:
                    getProgress(msg, thridProgress.get(), tvThirdthread.get());
                    break;
                case 4:
                    getProgress(msg, fourthProgress.get(), tvFourthThread.get());
                    break;
            }
        }

        public void getProgress(Message msg, ProgressBar bar, TextView textView){
            bar.setProgress(msg.getData().getInt("int"));
            textView.setText(msg.getData().getString("message"));
        }
    }
}
