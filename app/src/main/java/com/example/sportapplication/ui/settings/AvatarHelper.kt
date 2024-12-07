package com.example.sportapplication.ui.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AvatarHelper {
    private val _avatarId = MutableStateFlow(0) // Default avatar ID
    val avatarId: StateFlow<Int> = _avatarId

    private val _nickname = MutableStateFlow("Player") // Default nickname
    val nickname: StateFlow<String> = _nickname

    fun updateAvatar(newAvatarId: Int) {
        _avatarId.value = newAvatarId
    }

    fun updateNickname(newNickname: String) {
        _nickname.value = newNickname
    }
}
