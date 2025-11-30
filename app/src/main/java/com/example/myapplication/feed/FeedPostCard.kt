package com.example.myapplication.feed

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.Post

@Composable
fun FeedPostCard(
    post: Post,
    onLike: () -> Unit,
    onComment: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = post.author,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))


            Text(
                text = post.text,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "{post.likes}$ likes",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onLike) {
                Text("Like")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { onComment("Post") }) {
                Text("Post comment...")
            }
        }
    }
}
