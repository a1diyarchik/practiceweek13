package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Posts(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author: String,
    val text: String,
    val likes: Int,
    val comments: String
)
