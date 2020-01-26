package com.fathom.nfs;

// The data model that represent each Item of our list
public class ScreenItem {

    // init member variables
    int question;
    int description;
    int image;

    public ScreenItem(int image, int question, int description) {
        this.image = image;
        this.question = question;
        this.description = description;
    }

    public int getQuestion() {
        return question;
    }



    public int getDescription() {
        return description;
    }



    public int getImage() {
        return image;
    }

}
