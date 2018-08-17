package com.example.musa.twisent.ApiCall;

import com.example.musa.twisent.ApiResponse.Example;


import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiCallInterface {
    @POST("sentiment")
    Call<Example>mSentiments(@Query ("Sentiment")String sentiment , @Query("Number") String numberOfTweets);
}
