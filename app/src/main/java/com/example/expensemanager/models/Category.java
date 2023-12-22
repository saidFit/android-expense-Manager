package com.example.expensemanager.models;

public class Category {
    String name;
    int Image;
    int backgroundColor;

    public Category(String name, int image, int backgroundColor) {
        this.name = name;
        Image = image;
        this.backgroundColor = backgroundColor;
    }


    public String getName() {
        return name;
    }

    public int getImage() {
        return Image;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
