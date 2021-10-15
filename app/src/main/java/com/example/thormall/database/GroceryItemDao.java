package com.example.thormall.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.thormall.model.GroceryItem;

import java.util.List;

@Dao
public interface GroceryItemDao {

    @Insert
    void insert(GroceryItem groceryItem);

    @Query("SELECT * FROM grocery_items")
    List<GroceryItem> getAllItems();

    @Query("SELECT * FROM grocery_items WHERE id =:id")
    GroceryItem getItemById(int id);

    @Query("UPDATE grocery_items SET reviews = :reviews WHERE id = :id")
    void updateReviews(int id, String reviews);

    @Query("SELECT * FROM grocery_items WHERE name LIKE :text")
    List<GroceryItem> searchForItem(String text);

    @Query("SELECT DISTINCT category FROM grocery_items")
    List<String> getCategories();

    @Query("SELECT * FROM grocery_items WHERE category = :category")
    List<GroceryItem> getItemsByCategory(String category);

    @Query("UPDATE grocery_items SET avgRating = :avgAll WHERE id = :id")
    void updateAvgRating(float avgAll, int id);

    @Query("SELECT avgRating FROM grocery_items WHERE id = :id")
    float getAvgRatingGroceryItem(int id);

    @Query("UPDATE grocery_items SET popularityPoint = :points WHERE id = :id")
    void updatePopularityPoint(int id, int points);

    @Query("UPDATE grocery_items SET userPoint = :points WHERE id = :id")
    void updateUserPoint(int id, int points);

}
