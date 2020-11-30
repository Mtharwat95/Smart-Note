package com.example.smartnote.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Notes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "textNote")
    private String textNote;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] imageNote;

    @ColumnInfo(name = "noteType")
    @NonNull
    private String noteType;

    @ColumnInfo(name = "notePriority")
    @NonNull
    private String notePriority;

    public Notes(@NonNull String title, @NonNull String date, @NonNull String textNote, byte[] imageNote, @NonNull String noteType, @NonNull String notePriority) {
        this.title = title;
        this.date = date;
        this.textNote = textNote;
        this.imageNote = imageNote;
        this.noteType = noteType;
        this.notePriority = notePriority;
    }

    @Ignore
    public Notes() {
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public String getTextNote() {
        return textNote;
    }

    public byte[] getImageNote() {
        return imageNote;
    }


    @NonNull
    public String getNoteType() {
        return noteType;
    }

    @NonNull
    public String getNotePriority() {
        return notePriority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public void setTextNote(@NonNull String textNote) {
        this.textNote = textNote;
    }

    public void setImageNote(byte[] imageNote) {
        this.imageNote = imageNote;
    }


    public void setNoteType(@NonNull String noteType) {
        this.noteType = noteType;
    }

    public void setNotePriority(@NonNull String notePriority) {
        this.notePriority = notePriority;
    }


}
