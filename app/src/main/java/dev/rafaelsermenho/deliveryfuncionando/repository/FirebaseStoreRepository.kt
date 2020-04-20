package dev.rafaelsermenho.deliveryfuncionando.repository

import android.location.Address
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirebaseStoreRepository {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun loadStores(address: Address?): Query {
        return if (address != null) {
            db.collection("stores").orderBy("name")
                .whereEqualTo("state", address.adminArea)
                .whereEqualTo("city", address.subAdminArea)
        } else {
            db.collection("stores").orderBy("name")
        }

    }
}