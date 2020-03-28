package dev.rafaelsermenho.deliveryfuncionando.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import dev.rafaelsermenho.deliveryfuncionando.model.Store
import dev.rafaelsermenho.deliveryfuncionando.repository.FirebaseStoreRepository

class StoresViewModel() : ViewModel() {
    private val repository = FirebaseStoreRepository()
    val stores = repository.loadStores()
    var savedStores: MutableLiveData<List<Store>> = MutableLiveData()
    var filteredStores: MutableLiveData<List<Store>> = MutableLiveData()

    fun loadStores(): LiveData<List<Store>> {
        repository.loadStores().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.d("MyTag", "erro")
                return@EventListener
            }

            var storeList: MutableList<Store> = mutableListOf()
            for (doc in value!!) {
                var store = doc.toObject(Store::class.java)
                storeList.add(store)
            }
            savedStores.value = storeList
        })
        return savedStores
    }

    fun filterList(value: String?): MutableLiveData<List<Store>> {
        if (value == null) {
            return savedStores
        }
        filteredStores.value = savedStores.value?.filter {
            it.products.toLowerCase().contains(value.toLowerCase()) || it.name.toLowerCase().contains(
                value.toLowerCase()
            )
        }
        return filteredStores
    }


}