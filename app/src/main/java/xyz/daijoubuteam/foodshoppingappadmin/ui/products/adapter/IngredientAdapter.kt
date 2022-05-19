package xyz.daijoubuteam.foodshoppingappadmin.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.ItemIngredientBinding

class IngredientAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<String, IngredientAdapter.IngredientViewHolder>(DiffCallBack) {
    class IngredientViewHolder(private var binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: String) {
            binding.ingredient = ingredient
            binding.executePendingBindings()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredientItem = getItem(position)
        val btnDelete = holder.itemView.findViewById<ImageView>(R.id.imageDelete)
        btnDelete.setOnClickListener {
            onClickListener.onClick(ingredientItem)
        }
        holder.bind(ingredientItem)
    }

    class OnClickListener(val clickListener: (ingredient: String) -> Unit) {
        fun onClick(ingredient: String) = clickListener(ingredient)
    }
}