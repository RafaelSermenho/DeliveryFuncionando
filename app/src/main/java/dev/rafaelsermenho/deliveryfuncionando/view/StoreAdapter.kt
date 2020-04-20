package dev.rafaelsermenho.deliveryfuncionando.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.rafaelsermenho.deliveryfuncionando.R
import dev.rafaelsermenho.deliveryfuncionando.model.Store
import kotlinx.android.synthetic.main.row_store.view.*
import java.util.*

class StoreAdapter(private val storeList: List<Store>) :
    RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_store,
            parent,
            false
        ) as View
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.txtName
        private val profile: TextView = view.txtProfile
        private val product: TextView = view.txtProducts
        private val workingHours: TextView = view.txtWorkingHours
        private val contact: TextView = view.txtContact
        private val city: TextView = view.txtCity
        private val state: TextView = view.txtState

        @UseExperimental(ExperimentalStdlibApi::class)
        fun bindView(store: Store) {
            name.text = store.name.toLowerCase(Locale.getDefault()).capitalize(Locale.getDefault())
            profile.text = store.profile
            product.text = store.products
            workingHours.text = store.working_hours
            contact.text = store.contact
            city.text = store.city
            state.text = store.state
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = storeList[position]
        holder.bindView(store)
    }

    override fun getItemCount() = storeList.size
}
