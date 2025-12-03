package com.example.myapplication
//practice week 8-9(practice 5)
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import kotlinx.coroutines.delay

data class Follower(val id: Int, val name: String, val avatarRes: Int)

class ProfileViewModel : ViewModel() {


    var name by mutableStateOf("Aldiyar Nassyrov")
        private set

    var bio by mutableStateOf(
        "Hello! My name is Aldiyar. I am a game designer, " +
                "I love creating game worlds, UI design, and traveling ✈️. " +
                "I work on my own projects and am inspired by cyberpunk and fantasy."
    )
        private set

    var followers by mutableStateOf(
        listOf(
            Follower(1, "Alex", R.drawable.f1),
            Follower(2, "John", R.drawable.f3),
            Follower(3, "Aruzhan", R.drawable.f2),
            Follower(4, "Madi", R.drawable.f4),
        )
    )
        private set

    var isFollowed by mutableStateOf(false)
        private set

    private var nextFollowerId = 5



    var isOnline by mutableStateOf(true)
        private set


    fun toggleOnline() {
        isOnline = !isOnline
    }


    var isSyncing by mutableStateOf(false)
        private set

    suspend fun runSync() {
        isSyncing = true
        delay(800)
        isSyncing = false
    }


    var isAvatarLoading by mutableStateOf(true)
        private set

    fun setAvatarLoaded() {
        isAvatarLoading = false
    }


    var statsVisible by mutableStateOf(false)
        private set

    fun showStats() {
        statsVisible = true
    }



    fun updateName(newName: String) {
        name = newName
    }

    fun updateBio(newBio: String) {
        bio = newBio
    }

    fun toggleFollow() {
        isFollowed = !isFollowed
    }

    fun addFollower(name: String, avatarRes: Int) {
        followers = followers + Follower(nextFollowerId++, name, avatarRes)
    }

    fun removeFollower(id: Int) {
        followers = followers.filterNot { it.id == id }
    }
}
