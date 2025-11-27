package com.example.myapplication.model

data class UserProfile(
    val name: String,
    val bio: String,
    val followers: List<Follower>
)
