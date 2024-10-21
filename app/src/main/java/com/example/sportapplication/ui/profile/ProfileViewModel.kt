package com.example.sportapplication.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    // MutableStateFlow to hold the current nickname. Initially set to "CurrentNickname"
    private val _nickname = MutableStateFlow("CurrentNickname")
    val nickname: StateFlow<String> = _nickname

    // Fetch the user data from the database
    init {
        viewModelScope.launch {
            val user = userDao.getUser()
            user?.let {
                _nickname.value = it.name
            }
        }
    }
    // Function to update the nickname. True/false.
    suspend fun updateNickname(newNickname: String): Boolean {
        return try {
            val user = userDao.getUser()
            if (user != null) {
                val updatedUser = user.copy(name = newNickname)
                userDao.updateUser(updatedUser)
            } else {
                val newUser = User(name = newNickname)
                userDao.insertUser(newUser)
            }
            _nickname.value = newNickname
            true
        } catch (e: Exception) {
            false
        }
    }
}
