package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {
    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;

    SimpleAsyncTask (TextView tv, ProgressBar progressBar) {
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(progressBar);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random r = new Random();
        int n = r.nextInt(11);
        int s = n * 200;
        for(int i = 0; i < s; i+= 100) {
            try {
                Thread.sleep(100);
                publishProgress((int) ((i + 100) / (float)s * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isCancelled()) break;
        }
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    protected void onPostExecute(String result){
        mTextView.get().setText(result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        mProgressBar.get().setProgress(values[0]);
    }
}
