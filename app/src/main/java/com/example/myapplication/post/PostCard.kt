import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.myapplication.Post
import com.example.myapplication.PostsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun PostCard(post: Post, viewModel: PostsViewModel) {
    var commentText by remember { mutableStateOf("") }
    var cardVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cardVisible = true
    }

    AnimatedVisibility(
        visible = cardVisible,
        enter = fadeIn(tween(600))
    ){Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(post.author, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Image(
                painter = painterResource(post.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )

            Row(verticalAlignment = Alignment.CenterVertically) {

                var likePressed by remember { mutableStateOf(false) }

                val likeScale by animateFloatAsState(
                    targetValue = if (likePressed) 1.5f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    label = "likeScale"
                )

                val scope = rememberCoroutineScope()

                IconButton(
                    onClick = {
                        likePressed = true
                        viewModel.toggleLike(post.id)

                        scope.launch {
                            delay(150)
                            likePressed = false
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (post.isLiked) Color.Red else Color.Gray,
                        modifier = Modifier.scale(likeScale)
                    )
                }




                Text("${post.likes} likes", fontSize = 16.sp)
            }

            Text("Comments:", fontWeight = FontWeight.Bold)

            post.comments.forEach { c ->
                Text("• $c")
            }

            OutlinedTextField(
                value = commentText,
                onValueChange = { commentText = it },
                placeholder = { Text("Write a comment...") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (commentText.isNotBlank()) {
                        viewModel.addComment(post.id, commentText)
                        commentText = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Post comment")
            }
        }
    }
    }
}
