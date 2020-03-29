package dev.rafaelsermenho.deliveryfuncionando.view

import android.Manifest
import android.location.Address
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import dev.rafaelsermenho.deliveryfuncionando.R
import dev.rafaelsermenho.deliveryfuncionando.model.Store
import dev.rafaelsermenho.deliveryfuncionando.viewmodel.StoresViewModel
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: StoresViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupRecyclerView()
        setupSearch()
        setupAdmob()
        loadAds()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(StoresViewModel::class.java)
    }

    private fun setupRecyclerView() {
        viewManager = LinearLayoutManager(this)
        recycleViewStoreList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = StoreAdapter(ArrayList())
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun setupSearch() {
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    private fun filterList(
        value: String?
    ) {
        viewModel.filterList(value).observe(this, Observer { storeList ->
            updateAdapter(storeList)
        })
    }

    private fun updateAdapter(storeList: List<Store>) {
        recycleViewStoreList.adapter = StoreAdapter(storeList)
    }

    private fun loadStores(address: Address?) {
        viewModel.loadStores(address).observe(this, Observer { storeList ->
            updateAdapter(storeList)
        })
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        finish()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        getLastLocation()
    }

    private fun getLastLocation() {
        viewModel.getLocation(this).observe(this, Observer { address ->
            loadStores(address)
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onResume() {
        super.onResume()
        if (EasyPermissions.hasPermissions(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            getLastLocation()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.permission_rationale),
                REQUEST_ID,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun setupAdmob() {
        MobileAds.initialize(this) {

        }

    }

    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    companion object {
        private const val REQUEST_ID = 998
    }
}
