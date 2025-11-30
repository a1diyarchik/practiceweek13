package com.example.myapplication.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.Post
import com.example.myapplication.data.Posts

@Composable
fun FeedsScreen(
    viewModel: FeedViewModel = hiltViewModel()
) {
    val posts by viewModel.posts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.repository.loadPosts()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(posts) { post ->
            FeedPostCard(
                post = post,
                onLike = { viewModel.likePost(post.id) },
                onComment = { text -> viewModel.addComment(post.id, text) }
            )
        }
    }
}

private fun LazyItemScope.FeedPostCard(
    post: Post,
    onLike: () -> Unit,
    onComment: (String) -> Unit
) {
    TODO("Not yet implemented")
}

@Composable
fun PostCard(
    post: Posts,
    onLike: () -> Unit,
    onComment: (String) -> Unit
) {
    Card(modifier = Modifier.padding(12.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(post.author, style = MaterialTheme.typography.titleMedium)
            Text(post.text)
            Text("Likes: ${post.likes}")
            Button(onClick = onLike) { Text("Like") }
        }
    }
}
