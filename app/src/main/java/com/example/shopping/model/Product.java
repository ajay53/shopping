package com.example.shopping.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
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

    @Expose(serialize = false, deserialize = false)
    private boolean favorite;
    @Expose(serialize = false, deserialize = false)
    private boolean inCart;
    @Expose(serialize = false, deserialize = false)
    private boolean purchased;

    public Product(int id, String title, double price, String description, String url, boolean favorite, boolean inCart, boolean purchased) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.url = url;
        this.favorite = favorite;
        this.inCart = inCart;
        this.purchased = purchased;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @NonNull
    @Override
    public String toString() {
        return "ProductId: " + getId();
    }
}
