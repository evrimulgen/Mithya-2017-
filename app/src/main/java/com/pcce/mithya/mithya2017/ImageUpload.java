package com.pcce.mithya.mithya2017;

/**
 * Created by Sarvesh on 24-03-2017.
 */

public class ImageUpload  {

    String url;
    String caption;

    public ImageUpload(String url,String caption) {

        this.url = url;
        this.caption = caption;
    }

    public ImageUpload() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
