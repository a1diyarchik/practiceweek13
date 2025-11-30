package com.example.myapplication.data

import com.example.myapplication.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val api: ApiService,
    private val db: AppDatabase
) {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts = _posts.asStateFlow()

    suspend fun loadPosts() {
        _posts.value = api.getPosts()
    }

    suspend fun likePost(id: Int) {
        _posts.value = _posts.value.map {
            if (it.id == id) it.copy(likes = it.likes + 1) else it
        }
    }

    suspend fun addComment(id: Int, comment: String) {
        _posts.value = _posts.value.map {
            if (it.id == id) it.copy(comments = it.comments + comment) else it
        }
    }
}
