package com.codelovely.thecooksnook.network;
import com.codelovely.thecooksnook.models.restmodels.BrandedFoodItem;
import com.codelovely.thecooksnook.models.restmodels.FoundationFoodItem;
import com.codelovely.thecooksnook.models.restmodels.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitNetworkInterface {
    @GET("v1/food/{fdcId}")
    Call<BrandedFoodItem> getBrandedFoodItemById(@Path("fdcId") int fdcId, @Query("nutrients") int[] nutrients, @Query("api_key") String apiKey);

    @GET("v1/food/{fdcId}")
    Call<FoundationFoodItem> getFoundationFoodItemById(@Path("fdcId") int fdcId, @Query("nutrients") int[] nutrients, @Query("api_key") String apiKey);

    @GET("v1/foods/search")
    Call<SearchResult> searchFoods(@Query("api_key") String apiKey, @Query("query") String query, @Query("dataType") String[] dataType, @Query("sortBy") String sortBy, @Query("sortOrder") String sortOrder);
}
