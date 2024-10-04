package com.example.sportapplication.repository

import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.entity.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
){

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}