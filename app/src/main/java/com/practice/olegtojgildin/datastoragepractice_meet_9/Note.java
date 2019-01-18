package com.practice.olegtojgildin.datastoragepractice_meet_9;

/**
 * Created by olegtojgildin on 14/01/2019.
 */

public class Note {
    private String title;
    private int id;
    private String text_note;

    public Note() {

    }

    public Note(String title, String text_note) {
        this.title = title;
        this.text_note = text_note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText_note() {
        return text_note;
    }

    public void setText_note(String text_note) {
        this.text_note = text_note;
    }
}
