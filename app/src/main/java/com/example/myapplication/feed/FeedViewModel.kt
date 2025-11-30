package com.example.myapplication.feed
//practice week 11 (practice 7)
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    val repository: UserRepository
) : ViewModel() {

    val posts = repository.posts

    fun likePost(id: Int) = viewModelScope.launch {
        repository.likePost(id)
    }

    fun addComment(id: Int, comment: String) = viewModelScope.launch {
        repository.addComment(id, comment)
    }
}
