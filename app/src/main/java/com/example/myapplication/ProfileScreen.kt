package com.example.myapplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    darkMode: Boolean,
    onToggleDarkMode: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val name by remember { derivedStateOf { viewModel.name } }
    val bio by remember { derivedStateOf { viewModel.bio } }
    val followers by remember { derivedStateOf { viewModel.followers } }
    val isFollowed by remember { derivedStateOf { viewModel.isFollowed } }
    val isOnline by remember { derivedStateOf { viewModel.isOnline } }
    val isSyncing by remember { derivedStateOf { viewModel.isSyncing } }
    val isAvatarLoading by remember { derivedStateOf { viewModel.isAvatarLoading } }
    val statsVisible by remember { derivedStateOf { viewModel.statsVisible } }



    var isBioExpanded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1200)
        viewModel.setAvatarLoaded()
    }


    val avatarScale by animateFloatAsState(
        targetValue = if (isSyncing) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "avatarScale"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "onlinePulse")
    val onlineDotScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "onlineDotScale"
    )

    LaunchedEffect(Unit) {
        delay(1200)
        viewModel.setAvatarLoaded()
        viewModel.showStats()
    }
    val followButtonColor by animateColorAsState(
        targetValue = if (isFollowed) Color.Red else Color.Blue,
        animationSpec = tween(durationMillis = 300),
        label = "followButtonColor"
    )

    val stories = listOf(R.drawable.photo1, R.drawable.photo3, R.drawable.photo4)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Student Profile", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                },
                colors = centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFB74D),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onToggleDarkMode) {
                        Icon(
                            imageVector = if (darkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = "Toggle Theme",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { navController.navigate(Screen.EditProfile.route) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(stories) { res ->
                    Image(
                        painter = painterResource(id = res),
                        contentDescription = "Story",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
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
                        val image = painterResource(id = R.drawable.photo3)

                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .clickable {
                                    scope.launch {
                                        viewModel.toggleOnline()
                                        viewModel.runSync()
                                    }

                                },
                            contentAlignment = Alignment.BottomEnd
                        ) {

                            if (isAvatarLoading) {
                                ShimmerBox(
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(CircleShape)
                                )
                            } else {
                                if (isAvatarLoading) {
                                    Box(
                                        modifier = Modifier
                                            .size(150.dp)
                                            .clip(CircleShape)
                                            .background(Color.LightGray.copy(alpha = 0.4f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(
                                            color = MaterialTheme.colorScheme.primary,
                                            strokeWidth = 4.dp
                                        )
                                    }
                                } else {
                                    Image(
                                        painter = image,
                                        contentDescription = "Profile Image",
                                        modifier = Modifier
                                            .size(150.dp)
                                            .clip(CircleShape)
                                            .scale(avatarScale)
                                    )
                                }

                            }

                            if (isOnline && !isAvatarLoading) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .scale(onlineDotScale)
                                        .clip(CircleShape)
                                        .border(
                                            2.dp,
                                            MaterialTheme.colorScheme.background,
                                            CircleShape
                                        )
                                        .background(Color.Green)
                                        .align(Alignment.BottomEnd)
                                )
                            }
                        }


                        Text(
                            text = name,
                            style = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Biography",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.clickable { isBioExpanded = !isBioExpanded }
                            )
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                imageVector = if (isBioExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable { isBioExpanded = !isBioExpanded }
                            )
                        }

                        AnimatedVisibility(visible = isBioExpanded) {
                            Text(
                                text = bio,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }

                        var buttonPressed by remember { mutableStateOf(false) }
                        val buttonScale by animateFloatAsState(
                            targetValue = if (buttonPressed) 1.05f else 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "buttonScale"
                        )

                        AnimatedVisibility(
                            visible = statsVisible,
                            enter = fadeIn(animationSpec = tween(700))
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Life is beautiful",
                                    style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                                )

                                Text(
                                    text = "Followers: ${followers.size}",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    ),
                                    modifier = Modifier.animateContentSize()
                                )

                                Button(
                                    onClick = {
                                        val willFollow = !isFollowed
                                        viewModel.toggleFollow()

                                        scope.launch {
                                            buttonPressed = true
                                            delay(150)
                                            buttonPressed = false
                                        }

                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                if (willFollow) "You subscribed" else "You unsubscribed"
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .width(280.dp)
                                        .height(50.dp)
                                        .scale(buttonScale),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = followButtonColor)
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
                }
            }

            Text(
                text = "Followers (${followers.size})",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            var newFollowerName by rememberSaveable { mutableStateOf("") }

            OutlinedTextField(
                value = newFollowerName,
                onValueChange = { newFollowerName = it },
                label = { Text("Enter follower name") },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Button(
                onClick = {
                    if (newFollowerName.isNotBlank()) {
                        viewModel.addFollower(
                            name = newFollowerName.trim(),
                            avatarRes = R.drawable.photo5
                        )
                        newFollowerName = ""
                        scope.launch { snackbarHostState.showSnackbar("Follower added") }
                    } else {
                        scope.launch { snackbarHostState.showSnackbar("Please enter a name") }
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .fillMaxWidth()
            ) {
                Text("Add follower")
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = followers, key = { it.id }) { follower ->

                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { fullHeight -> fullHeight }
                        ) + fadeIn(),
                        exit = fadeOut()
                    ) {
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
                                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                )
                            }
                            IconButton(
                                onClick = {
                                    viewModel.removeFollower(follower.id)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Removed ${follower.name}")
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove follower"
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}
