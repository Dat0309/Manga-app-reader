package com.dinhtrongdat.mangareaderapp.model;

import java.io.Serializable;
import java.util.List;

public class Tag implements Serializable {
    String tag;

    public Tag(String tags) {
        this.tag = tags;
    }

    public String getTags() {
        return tag;
    }

    public void setTags(String tags) {
        this.tag = tags;
    }
}
