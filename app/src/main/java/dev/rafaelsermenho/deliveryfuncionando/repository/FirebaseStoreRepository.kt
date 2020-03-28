package dev.rafaelsermenho.deliveryfuncionando.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirebaseStoreRepository {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun loadStores(): Query {
        return db.collection("stores").whereEqualTo("state", "Rio de Janeiro").whereEqualTo("city", "Rio de Janeiro")
    }
}