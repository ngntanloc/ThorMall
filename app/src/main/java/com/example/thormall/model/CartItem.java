package com.example.thormall.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int groceryItemId;

    public CartItem(int groceryItemId) {
        this.groceryItemId = groceryItemId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroceryItemId() {
        return groceryItemId;
    }

    public void setGroceryItemId(int groceryItemId) {
        this.groceryItemId = groceryItemId;
    }
}
