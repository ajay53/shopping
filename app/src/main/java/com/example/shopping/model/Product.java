package com.example.shopping.model;

import android.app.Activity;
import android.util.Log;
import android.widget.Button;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.shopping.utility.Util;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "product")
public class Product implements Serializable {

    @PrimaryKey
    private int id;
    private String title;
    private double price;
    private String description;
    @SerializedName("image")
    private String url;
    private transient boolean isPurchased;
    private transient boolean isFavorite;

    public Product(int id, String title, double price, String description, String url) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
