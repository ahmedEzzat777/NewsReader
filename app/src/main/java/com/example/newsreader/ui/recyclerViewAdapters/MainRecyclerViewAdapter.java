package com.example.newsreader.ui.recyclerViewAdapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsreader.model.Feed;
import com.example.newsreader.model.Record;
import com.example.newsreader.R;
import com.example.newsreader.ui.activities.WebViewActivity;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
    private MutableLiveData<Feed> feed;
    private Activity parentActivity;
    public MainRecyclerViewAdapter(Activity activity, MutableLiveData<Feed> feed){
        this.feed = feed;
        parentActivity = activity;
        this.feed.observe((LifecycleOwner) parentActivity, new Observer<Feed>() {
            @Override
            public void onChanged(Feed feed) {
                notifyDataSetChanged();
            }
        });
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_holder,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Record rec = feed.getValue().getRecord(position);
        holder.Title.setText(rec.get_title());
        holder.Author.setText(rec.get_author());
        holder.ParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to website with site url
                if(rec.get_site() == null)
                    return;
                Intent intent = new Intent(parentActivity, WebViewActivity.class);
                intent.putExtra("url",rec.get_site());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                parentActivity.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return feed.getValue().getFeedSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public TextView Author;
        public ConstraintLayout ParentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.title);
            Author = itemView.findViewById(R.id.author);
            ParentLayout = itemView.findViewById(R.id.mainParentLayout);
        }
    }
}
