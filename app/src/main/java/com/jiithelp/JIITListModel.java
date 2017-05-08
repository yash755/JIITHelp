package com.jiithelp;

/**
 * Created by yash on 8/4/17.
 */

public class JIITListModel {

    String title;
    String image;

    public JIITListModel(String title,String image) {

        this.title=title;
        this.image=image;
    }


    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
