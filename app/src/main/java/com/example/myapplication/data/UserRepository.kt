package com.example.myapplication.data

import com.example.myapplication.R
import com.example.myapplication.model.Follower
import com.example.myapplication.model.UserProfile
import javax.inject.Inject

class UserRepository @Inject constructor() {

    suspend fun getUserProfile(): UserProfile {
        return UserProfile(
            name = "Aldiyar Nassyrov",
            bio = "Hello! My name is Aldiyar. I am a game designer...",
            followers = listOf(
                Follower(1, "Alex", R.drawable.photo),
                Follower(2, "John", R.drawable.photo),
                Follower(3, "Aruzhan", R.drawable.photo),
                Follower(4, "Madi", R.drawable.photo),
            )
        )
    }
}
