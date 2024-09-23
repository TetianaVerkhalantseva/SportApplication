package com.example.sportapplication.repository

import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.entity.User
import com.example.sportapplication.database.firebase.authentication.FirebaseAuthentication
import com.example.sportapplication.database.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val firebaseAuthentication: FirebaseAuthentication,
    private val firebaseFirestore: FirebaseFirestore
){

    val currentUser get() = firebaseAuthentication.currentUser

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        doOnRegistrationComplete: OnCompleteListener<AuthResult>
    ) {
        val onCompleteListener =
            OnCompleteListener<AuthResult> { p0 ->
                doOnRegistrationComplete.onComplete(p0)
                if (p0.isSuccessful) {
                    p0.result.user?.uid?.let { uid ->
                        CoroutineScope(Dispatchers.IO).launch {
                            firebaseFirestore.setRegisteredUserData()
                        }
                    }
                }
            }

        firebaseAuthentication.createUserWithEmailAndPassword(email, password, onCompleteListener)
    }

    suspend fun signInUserWithEmailAndPassword(
        email: String,
        password: String,
        doOnComplete: OnCompleteListener<AuthResult>
    ) {
        val onCompleteListener =
            OnCompleteListener { p0 -> doOnComplete.onComplete(p0) }

        firebaseAuthentication.signInUserWithEmailAndPassword(email, password, onCompleteListener)
    }



}