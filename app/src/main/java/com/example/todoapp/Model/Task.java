package com.example.todoapp.Model;

public class Task {
    private String text;
    public Boolean imp;

    public Task(String text, Boolean imp) {
        this.imp = imp;
        this.text = text;
    }

    public String getText() {
        return text;
    }
    public Boolean getImp(){return imp;}
}