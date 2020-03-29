package dev.rafaelsermenho.deliveryfuncionando.repository.provider

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task

class GpsLocationProvider {
    fun getLocation(fusedLocationClient: FusedLocationProviderClient): Task<Location>? {
        return fusedLocationClient.lastLocation
    }
}