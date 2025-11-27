package com.example.myapplication.data.network

import retrofit2.http.GET

interface ApiService {

    @GET("profile")
    suspend fun getProfile(): ProfileDto

    @GET("followers")
    suspend fun getFollowers(): List<FollowerDto>
}

data class ProfileDto(
    val name: String,
    val bio: String
)

data class FollowerDto(
    val id: Int,
    val name: String
)
