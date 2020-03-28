package dev.rafaelsermenho.deliveryfuncionando.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.rafaelsermenho.deliveryfuncionando.R
import dev.rafaelsermenho.deliveryfuncionando.model.Store
import dev.rafaelsermenho.deliveryfuncionando.viewmodel.StoresViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        val model = ViewModelProviders.of(this)[StoresViewModel::class.java]

        recycleViewStoreList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = StoreAdapter(ArrayList())
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }


        searchView.clearFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query, model)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText, model)
                return true
            }
        })



        model.loadStores().observe(this, Observer { storeList ->
            updateAdapter(storeList)
        })
    }

    private fun filterList(
        value: String?,
        model: StoresViewModel
    ) {
        model.filterList(value).observe(this, Observer { storeList ->
            updateAdapter(storeList)
        })
    }

    private fun updateAdapter(storeList: List<Store>) {
        recycleViewStoreList.adapter = StoreAdapter(storeList)
    }
}
