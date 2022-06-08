package xyz.daijoubuteam.foodshoppingappadmin.ui.events.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_event.view.*
import xyz.daijoubuteam.foodshoppingappadmin.databinding.ItemEventBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Event

class EventAdapter(
    private val onClickSubscribeListener: OnClickListener,
    private val onClickUnSubscribeListener: OnClickListener
) :
    ListAdapter<Event, EventAdapter.EventViewHolder>(DiffCallBack) {

    class EventViewHolder(private var binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.event = event
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val eventItem = getItem(position)
        holder.bind(eventItem)
        holder.itemView.btnSubscribe.setOnClickListener {
            onClickSubscribeListener.onClick(eventItem)
        }

        holder.itemView.btnUnsubsribe.setOnClickListener {
            onClickUnSubscribeListener.onClick(eventItem)
        }

        if (eventItem.subscribed) {
            holder.itemView.btnSubscribe.visibility = View.INVISIBLE
            holder.itemView.btnUnsubsribe.visibility = View.VISIBLE
        } else {
            holder.itemView.btnSubscribe.visibility = View.VISIBLE
            holder.itemView.btnUnsubsribe.visibility = View.INVISIBLE
        }
    }

    class OnClickListener(val clickListener: (event: Event) -> Unit) {
        fun onClick(event: Event) = clickListener(event)
    }
}