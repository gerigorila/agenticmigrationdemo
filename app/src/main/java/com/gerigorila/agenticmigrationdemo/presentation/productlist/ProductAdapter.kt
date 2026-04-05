package com.gerigorila.agenticmigrationdemo.presentation.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerigorila.agenticmigrationdemo.R
import com.gerigorila.agenticmigrationdemo.domain.model.Product
import java.util.Locale

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val products = mutableListOf<Product>()
    private var listener: ((Int) -> Unit)? = null

    fun setOnProductClickListener(listener: (Int) -> Unit) {
        this.listener = listener
    }

    fun setProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.txtName.text = product.name
        holder.txtPrice.text = String.format(Locale.US, "$%.2f", product.price)
        holder.itemView.setOnClickListener {
            listener?.invoke(product.id)
        }
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txt_name)
        val txtPrice: TextView = itemView.findViewById(R.id.txt_price)
    }
}
