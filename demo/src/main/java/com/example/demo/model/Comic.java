package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Comic {
    @Id
    private String code; // CM00000, CM00001, ...

    private String animeName;
    private String comicName;
    private String imageUrl;

    public Comic() {}
    public Comic(String code, String animeName, String comicName, String imageUrl) {
        this.code = code;
        this.animeName = animeName;
        this.comicName = comicName;
        this.imageUrl = imageUrl;
    }

    // getters and setters...
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getAnimeName() {
        return animeName;
    }
    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }
    public String getComicName() {
        return comicName;
    }
    public void setComicName(String comicName) {
        this.comicName = comicName;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
