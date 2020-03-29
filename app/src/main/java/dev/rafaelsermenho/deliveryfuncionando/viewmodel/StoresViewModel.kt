package dev.rafaelsermenho.deliveryfuncionando.viewmodel

import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import dev.rafaelsermenho.deliveryfuncionando.model.Store
import dev.rafaelsermenho.deliveryfuncionando.repository.FirebaseStoreRepository
import dev.rafaelsermenho.deliveryfuncionando.repository.provider.GpsLocationProvider
import java.util.*

class StoresViewModel : ViewModel() {
    private val repository = FirebaseStoreRepository()
    private val gpsLocationProvider = GpsLocationProvider()
    private var savedStores: MutableLiveData<List<Store>> = MutableLiveData()
    private var filteredStores: MutableLiveData<List<Store>> = MutableLiveData()
    private var lastAddress: MutableLiveData<Address> = MutableLiveData()

    fun loadStores(address: Address?): LiveData<List<Store>> {
        repository.loadStores(address)
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    return@EventListener
                }

                val storeList: MutableList<Store> = mutableListOf()
                for (doc in value!!) {
                    val store = doc.toObject(Store::class.java)
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
            it.products.toLowerCase(Locale.ROOT).contains(value.toLowerCase(Locale.ROOT)) || it.name.toLowerCase(
                Locale.ROOT
            ).contains(
                value.toLowerCase(Locale.ROOT)
            )
        }
        return filteredStores
    }

    fun getLocation(activity: Activity): MutableLiveData<Address> {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        gpsLocationProvider.getLocation(fusedLocationClient)
            ?.addOnSuccessListener { location: Location? ->
                location?.let {
                    lastAddress.value =
                        convertLocationToAddress(location, fusedLocationClient.applicationContext)
                }
            }
            ?.addOnFailureListener { _: Exception ->
                lastAddress.value = null
            }
        return lastAddress

    }

    private fun convertLocationToAddress(location: Location, context: Context): Address {
        val address = Geocoder(context).getFromLocation(
            location.latitude, location.longitude, 1
        )
        return address[0]
    }


}