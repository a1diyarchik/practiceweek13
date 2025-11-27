package com.example.myapplication

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

data class Post(
    val id: Int,
    val author: String,
    val imageRes: Int,
    val likes: Int = 0,
    val isLiked: Boolean = false,
    val comments: List<String> = emptyList()
)


class PostsViewModel : ViewModel() {

    private var nextId = 4

    var posts by mutableStateOf(
        listOf(
            Post(1, "Alex", R.drawable.photo1, likes = 10),
            Post(2, "Aruzhan", R.drawable.photo4, likes = 3),
            Post(3, "Madi", R.drawable.photo3, likes = 1)
        )
    )
        private set

    fun toggleLike(id: Int) {
        posts = posts.map {
            if (it.id == id) {
                it.copy(
                    isLiked = !it.isLiked,
                    likes = if (!it.isLiked) it.likes + 1 else it.likes - 1
                )
            } else it
        }
    }

    fun addComment(id: Int, comment: String) {
        posts = posts.map {
            if (it.id == id) {
                it.copy(comments = (it.comments + comment).toMutableList())
            } else it
        }
    }
    fun addPost(author: String, imageRes: Int, text: String) {

        val newPost = Post(
            id = nextId++,
            author = author,
            imageRes = imageRes,
            likes = 0,
            isLiked = false,
            comments = listOf(text)
        )

        posts = posts + newPost
    }

}
