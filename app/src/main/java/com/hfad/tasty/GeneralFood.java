package com.hfad.tasty;

import com.google.gson.annotations.SerializedName;

public class GeneralFood {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("price")
    private double price;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;
    @SerializedName("rating")
    private float rating;
    @SerializedName("calorie")
    private int calorie;
    @SerializedName("fat")
    private int fat;
    @SerializedName("sodium")
    private int sodium;
    @SerializedName("protein")
    private int protein;
    @SerializedName("quantity")
    private int quantity;


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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getCalory() {
        return calorie;
    }

    public void setCalory(int calorie) {
        this.calorie = calorie;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getQuantity() {
        return quantity ;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}