package com.example.todoapp.Model;

public class Type {
    private String name;

    private int imageId;

    public Type(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
