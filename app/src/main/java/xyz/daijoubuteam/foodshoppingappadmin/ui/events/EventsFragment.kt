package xyz.daijoubuteam.foodshoppingappadmin.ui.events

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentEventsBinding
import xyz.daijoubuteam.foodshoppingappadmin.ui.events.adapter.EventAdapter
import xyz.daijoubuteam.foodshoppingappadmin.utils.hideKeyboard

class EventsFragment : Fragment() {
    private lateinit var binding: FragmentEventsBinding

    private val viewModel: EventsViewModel by lazy {
        val viewModelFactory = EventsViewModelFactory()
        ViewModelProvider(this, viewModelFactory)[EventsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        hideActionBar()
        setupEventListViewAdapter()
        setupMessageObserver()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupEventListViewAdapter() {
        binding.eventRecyclerView.adapter = EventAdapter(
            EventAdapter.OnClickListener {
                viewModel.onSubscribe(it)
            },
            EventAdapter.OnClickListener {
                viewModel.onUnSubscribe(it)
            }
        )
        val adapter = binding.eventRecyclerView.adapter as EventAdapter
        adapter.submitList(viewModel.eventList.value)
        viewModel.eventList.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun hideActionBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
    }

    private fun setupMessageObserver() {
        viewModel.message.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                hideKeyboard()
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
                viewModel.onShowMessageComplete()
            }
        }
    }
}