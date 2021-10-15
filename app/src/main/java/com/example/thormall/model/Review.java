package com.example.thormall.model;

public class Review {

    private int groceryItemId;
    private String userName;
    private String text;
    private float rating;
    private String date;

    public Review(int groceryItemId, String userName, String text, float rating, String date) {
        this.groceryItemId = groceryItemId;
        this.userName = userName;
        this.text = text;
        this.rating = rating;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "groceryItemId=" + groceryItemId +
                ", userName='" + userName + '\'' +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                ", date='" + date + '\'' +
                '}';
    }

    public int getGroceryItemId() {
        return groceryItemId;
    }

    public void setGroceryItemId(int groceryItemId) {
        this.groceryItemId = groceryItemId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
