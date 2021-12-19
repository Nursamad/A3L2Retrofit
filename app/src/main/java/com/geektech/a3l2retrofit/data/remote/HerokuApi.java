package com.geektech.a3l2retrofit.data.remote;

import com.geektech.a3l2retrofit.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface HerokuApi {

    @GET("/posts")
    Call<List<Post>> getPosts();

    @POST("/posts")
    Call<Post> createPost(@Body Post post);

    @DELETE("/posts/{id}")
    Call<Post> deletePost(@Path("id") int position);

    @PUT("/posts/{id}")
    Call<Post> updatePost(@Path("id") int position , @Body Post post);

}
