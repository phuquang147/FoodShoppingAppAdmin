package xyz.daijoubuteam.foodshoppingappadmin.ui.orders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_order.view.*
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.ItemOrderBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Order

class OrderAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Order, OrderAdapter.OrderViewHolder>(DiffCallBack) {

    class OrderViewHolder(private var binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.order = order
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val orderItem = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(orderItem)
        }

        when (orderItem.status) {
            "Preparing" -> holder.itemView.imageStatus.setImageResource(R.drawable.preparing)
            "Shipping" -> holder.itemView.imageStatus.setImageResource(R.drawable.shipping)
            "Completed" -> holder.itemView.imageStatus.setImageResource(R.drawable.completed)
            "Cancelled" -> holder.itemView.imageStatus.setImageResource(R.drawable.cancelled)
            else -> holder.itemView.imageStatus.setImageResource(R.drawable.pending)
        }

        holder.bind(orderItem)
    }

    class OnClickListener(val clickListener: (order: Order) -> Unit) {
        fun onClick(order: Order) = clickListener(order)
    }
}