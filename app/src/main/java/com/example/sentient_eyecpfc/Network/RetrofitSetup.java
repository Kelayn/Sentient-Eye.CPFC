package com.example.sentient_eyecpfc.Network;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import com.example.sentient_eyecpfc.Data.Product;

public interface RetrofitSetup {
    @GET("products/{code}")
    Single<Product> getProductByCode(@Path("code") String code);
    @GET("products/{name}")
    Single<Product> getProductByName(@Path("name") String name);
    @POST("products")
    Single<Product> setProduct(@Body Object object);
    @PUT("products")
    Single<Product> changeProduct(@Body Object object);
}

