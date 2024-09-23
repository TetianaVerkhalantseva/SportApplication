package com.example.sportapplication.database.firebase.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseFirestore @Inject constructor() {


    private val uid get() = FirebaseAuth.getInstance().currentUser?.uid!!
    private val db = Firebase.firestore

    /**
     * Set up user's start data and field,
     * Whenever we need to add new fields or delete old one - change [startUserData] map
     */

    fun setRegisteredUserData(): Task<Void> {

        val startUserData = mapOf(
            NAME_FIELD to null,
            AGE_FIELD to null
        )

        return db.collection(USERS_COLLECTION)
            .document(uid)
            .set(startUserData)

    }


    companion object {
        private const val USERS_COLLECTION = "users"

        private const val NAME_FIELD = "name"
        private const val AGE_FIELD = "age"
    }
}