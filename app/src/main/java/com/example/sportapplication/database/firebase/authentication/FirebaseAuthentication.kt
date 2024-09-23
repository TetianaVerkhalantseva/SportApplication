package com.example.sportapplication.database.firebase.authentication

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthentication @Inject constructor() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    val currentUser get() = firebaseAuth.currentUser

    fun createUserWithEmailAndPassword(email: String, password: String, onCompleteListener: OnCompleteListener<AuthResult>) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(onCompleteListener)
    }
    fun signInUserWithEmailAndPassword(email: String, password: String, onCompleteListener: OnCompleteListener<AuthResult>) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(onCompleteListener)
    }


}