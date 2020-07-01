package com.fleecyclouds.flipthetripover.sb.itemForClass.DB;

public class courseList {
    public String title;
    public String image;

    public courseList(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
