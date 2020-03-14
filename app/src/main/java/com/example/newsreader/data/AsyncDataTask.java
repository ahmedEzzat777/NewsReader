package com.example.newsreader.data;

import needle.Needle;
import needle.UiRelatedTask;

public abstract class AsyncDataTask{

    public AsyncDataTask(){
        Needle.onBackgroundThread().execute(new UiRelatedTask<Void>() {
            @Override
            protected Void doWork() {
                doInBackground();
                return null;
            }

            @Override
            protected void thenDoUiRelatedWork(Void v) {
                onPostExecute();
            }
        });
    }

    protected abstract void doInBackground();

    protected abstract void onPostExecute();

}
