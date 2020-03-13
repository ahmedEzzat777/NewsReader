package com.example.newsreader.model;

import java.util.UUID;

public class Record {
    private UUID m_id;
    private String m_title;
    private String m_author;
    private String m_site;
    public Record(){
        m_id = UUID.randomUUID();
    }
    public Record(String title,String author,String site){
        m_id = UUID.randomUUID();
        m_title = title;
        m_author =author;
        m_site=site;
    }
    public Record(String id,String title,String author,String site){
        m_id = UUID.fromString(id);
        m_title = title;
        m_author =author;
        m_site=site;
    }

    public UUID get_id() {
        return m_id;
    }

    public void set_id(UUID m_id) {
        this.m_id = m_id;
    }

    public String get_author() {
        return m_author;
    }

    public void set_author(String m_text) {
        this.m_author = m_text;
    }

    public String get_site() {
        return m_site;
    }

    public void set_site(String m_site) {
        this.m_site = m_site;
    }

    public String get_title() {
        return m_title;
    }

    public void set_title(String m_title) {
        this.m_title = m_title;
    }
}