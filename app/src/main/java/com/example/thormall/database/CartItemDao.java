package com.example.thormall.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.thormall.model.GroceryItem;

import java.util.List;

@Dao
public interface CartItemDao {

    @Query("INSERT INTO cart_items (groceryItemId) VALUES (:id)")
    void insertData(int id);

    @Query("SELECT G.* FROM grocery_items G INNER JOIN cart_items C ON G.id = C.groceryItemId")
    List<GroceryItem> getAllCartItems();

    @Query("DELETE FROM cart_items WHERE groceryItemId = :groceryItemId")
    void removeCartItemById(int groceryItemId);

    @Query("DELETE FROM cart_items")
    void clearCart();

}
