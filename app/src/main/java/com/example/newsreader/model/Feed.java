package com.example.newsreader.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Feed{
    private ArrayList<Record> m_feed;
    public Feed(){
        m_feed = new ArrayList<>();
    }

    public void addRecord(String title,String author,String site){
        Record rec = new Record(title,author,site);
        m_feed.add(rec);
    }
    public Feed addRecords(List<Record> records){
        m_feed.addAll(records);
        return  this;
    }

    public Record getRecord(UUID id){
         for(Record rec : m_feed){
             if(rec.get_id() == id)
                 return rec;
         }
         return null;
    }
    public List<Record> getRecords(){
        return m_feed;
    }


    public Record getRecord(int idx){
        return m_feed.get(idx);
    }

    public int getFeedSize(){
        return m_feed.size();
    }
}
