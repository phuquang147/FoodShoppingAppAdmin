package xyz.daijoubuteam.foodshoppingappadmin.ui.orders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.daijoubuteam.foodshoppingappadmin.databinding.ItemProductInOrderBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.ProductInOrder

class ProductInOrderAdapter() :
    ListAdapter<ProductInOrder, ProductInOrderAdapter.ProductInOrderViewHolder>(DiffCallBack) {
    class ProductInOrderViewHolder(private var binding: ItemProductInOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductInOrder) {
            binding.product = product
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<ProductInOrder>() {
        override fun areItemsTheSame(oldItem: ProductInOrder, newItem: ProductInOrder): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProductInOrder, newItem: ProductInOrder): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductInOrderViewHolder {
        val binding =
            ItemProductInOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductInOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductInOrderViewHolder, position: Int) {
        val productItem = getItem(position)
        holder.bind(productItem)
    }
}