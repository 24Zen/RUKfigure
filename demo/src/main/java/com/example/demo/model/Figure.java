package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Figure {
    @Id
    private String code; // 00000, 00001, ...

    private String animeName;
    private String figureName;
    private String imageUrl;

    public Figure() {}
    public Figure(String code, String animeName, String figureName, String imageUrl) {
        this.code = code;
        this.animeName = animeName;
        this.figureName = figureName;
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
    public String getFigureName() {
        return figureName;
    }
    public void setFigureName(String figureName) {
        this.figureName = figureName;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
