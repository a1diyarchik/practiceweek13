package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

data class Follower(val id: Int, val name: String, val avatarRes: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                ProfileCardScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCardScreen() {
    var isFollowed by rememberSaveable { mutableStateOf(false) }
    var followerCount by rememberSaveable { mutableStateOf(119) }

    val buttonColor by animateColorAsState(
        targetValue = if (isFollowed) Color.Red else Color.Blue
    )

    val animatedFollowerCount = remember { Animatable(followerCount.toFloat()) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val stories = listOf(
        R.drawable.f1,
        R.drawable.f2,
        R.drawable.f4
    )

    val followers = listOf(
        Follower(1, "Alex", R.drawable.f1),
        Follower(2, "John", R.drawable.f3),
        Follower(3, "Aruzhan", R.drawable.f2),
        Follower(4, "Madi", R.drawable.f4),
    )

    val followStates = remember {
        mutableStateMapOf<Int, Boolean>().apply {
            followers.forEach { put(it.id, false) }
        }
    }

    LaunchedEffect(isFollowed) {
        val targetValue = if (isFollowed) followerCount + 1 else followerCount - 1
        animatedFollowerCount.animateTo(
            targetValue = targetValue.toFloat(),
            animationSpec = tween(durationMillis = 300)
        )
        followerCount = targetValue
    }

    var showUnfollowDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Student Profile", fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                colors = centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFB74D),
                    titleContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(stories) { res ->
                    Image(
                        painter = painterResource(id = res),
                        contentDescription = "Story",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Blue, CircleShape)
                    )
                }
            }



            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(10.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .padding(start = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        val image = painterResource(id = R.drawable.profile_image)
                        Image(
                            painter = image,
                            contentDescription = "Profile Image",
                            modifier = Modifier.size(150.dp)
                        )

                        Text(
                            text = "Aldiyar Nassyrov",
                            style = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Game Designer",
                            style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                        )

                        Text(
                            text = "Followers: ${animatedFollowerCount.value.toInt()}",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.animateContentSize()
                        )

                        Button(
                            onClick = {
                                if (!isFollowed) {
                                    isFollowed = true
                                    coroutineScope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "You subscribed",
                                            actionLabel = "Cancel"
                                        )

                                        if (result == SnackbarResult.ActionPerformed) {
                                            isFollowed = false
                                        }
                                    }
                                } else {
                                    showUnfollowDialog = true
                                }
                            },
                            modifier = Modifier
                                .width(280.dp)
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                        ) {
                            Text(
                                text = if (isFollowed) "Unsubscribe" else "Subscribe",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }

            Text(
                text = "Recommendation",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.Start)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(followers) { follower ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = follower.avatarRes),
                                contentDescription = follower.name,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = follower.name,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        val isFollowed = followStates[follower.id] == true

                        Button(
                            onClick = {
                                if (!isFollowed) {
                                    followStates[follower.id] = true
                                    coroutineScope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "You subscribed to ${follower.name}",
                                            actionLabel = "Cancel"
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            followStates[follower.id] = false
                                        }
                                    }
                                } else {
                                    followStates[follower.id] = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isFollowed) Color.Red else Color.Blue
                            )
                        ) {
                            Text(
                                text = if (isFollowed) "Unfollow" else "Follow",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

    if (showUnfollowDialog) {
        AlertDialog(
            onDismissRequest = { showUnfollowDialog = false },
            title = { Text("Unfollow Confirmation") },
            text = { Text("Are you sure you want to unfollow this user?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        isFollowed = false
                        showUnfollowDialog = false
                    }
                ) {
                    Text("Yes", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showUnfollowDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
