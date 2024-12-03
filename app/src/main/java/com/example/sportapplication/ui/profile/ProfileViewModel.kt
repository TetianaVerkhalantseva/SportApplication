package com.example.sportapplication.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.entity.User
import com.example.sportapplication.ui.settings.AvatarHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    private val _nickname = MutableStateFlow("Player") // Default value
    val nickname: StateFlow<String> get() = _nickname

    private val _avatarId = MutableStateFlow(0) // Default avatar ID
    val avatarId: StateFlow<Int> get() = _avatarId

    // Initialiser brukerdata ved oppstart
    init {
        viewModelScope.launch {
            val user = userDao.getUser()
            if (user != null) {
                _nickname.value = user.name
                _avatarId.value = user.avatarId
            } else {
                _nickname.value = "Player"
                _avatarId.value = 0
                userDao.insertUser(User(name = "Player", avatarId = 0))
            }
            // Oppdater AvatarHelper med initial verdier
            AvatarHelper.updateNickname(_nickname.value)
            AvatarHelper.updateAvatar(_avatarId.value)
        }
    }

    // Oppdater brukernavn
    suspend fun updateNickname(newNickname: String): Boolean {
        return try {
            val user = userDao.getUser()
            if (user != null) {
                val updatedUser = user.copy(name = newNickname)
                userDao.updateUser(updatedUser)
            } else {
                val newUser = User(name = newNickname, avatarId = _avatarId.value)
                userDao.insertUser(newUser)
            }
            _nickname.value = newNickname
            AvatarHelper.updateNickname(newNickname) // Update AvatarHelper as well
            true
        } catch (e: Exception) {
            false
        }
    }

    // Oppdater avatar
    suspend fun updateAvatar(newAvatarId: Int): Boolean {
        return try {
            val user = userDao.getUser()
            if (user != null) {
                val updatedUser = user.copy(avatarId = newAvatarId)
                userDao.updateUser(updatedUser)
            } else {
                val newUser = User(name = _nickname.value, avatarId = newAvatarId)
                userDao.insertUser(newUser)
            }
            _avatarId.value = newAvatarId
            AvatarHelper.updateAvatar(newAvatarId) // Update AvatarHelper as well
            true
        } catch (e: Exception) {
            false
        }
    }
}
