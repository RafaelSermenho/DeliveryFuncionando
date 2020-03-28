package dev.rafaelsermenho.deliveryfuncionando.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.rafaelsermenho.deliveryfuncionando.R
import dev.rafaelsermenho.deliveryfuncionando.model.Store
import kotlinx.android.synthetic.main.row_store.view.*

class StoreAdapter(private val storeList: List<Store>) :
    RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_store,
            parent,
            false
        ) as View
        return ViewHolder(view, parent.context)
    }

    class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.txtNameValue
        private val profile: TextView = view.txtProfileValue
        private val product: TextView = view.txtProductsValue
        private val workingHours: TextView = view.txtWorkingHoursValue
        private val area: TextView = view.txtAreaValue

        fun bindView(store: Store) {
            name.text = store.name
            profile.text = store.profile
            product.text = store.products
            workingHours.text = store.working_hours
            area.text = store.area
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = storeList[position]
        holder.bindView(store)
    }

    override fun getItemCount() = storeList.size
}
