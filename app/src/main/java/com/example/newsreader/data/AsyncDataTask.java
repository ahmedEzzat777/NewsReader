package com.example.newsreader.data;

import android.os.AsyncTask;

public abstract class AsyncDataTask extends AsyncTask<Void,Void,Void> {

    public AsyncDataTask(){
        execute();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        doInBackground();
        return null;
    }

    protected abstract void doInBackground();

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onPostExecute();
    }

    protected abstract void onPostExecute();

}
