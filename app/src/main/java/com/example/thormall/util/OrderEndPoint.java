package com.example.thormall.util;

import com.example.thormall.model.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderEndPoint {
    @POST("posts")
    Call<Order> newOrder(@Body Order order);
}
