package xyz.daijoubuteam.foodshoppingappadmin.eatery.newproduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentNewProductBinding
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.adapter.IngredientAdapter

class NewProductFragment : Fragment() {
    private lateinit var binding: FragmentNewProductBinding
    private val viewmodel: NewProductViewModel by lazy {
        val factory = NewProductViewModelFactory()
        ViewModelProvider(this, factory)[NewProductViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewProductBinding.inflate(inflater, container, false)
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = viewLifecycleOwner

        setupMessageSnackbar()
        setupIngredientListViewAdapter()

        return binding.root
    }

    private fun setupIngredientListViewAdapter() {
        binding.rvIngredients.adapter = IngredientAdapter(
            IngredientAdapter.OnClickListener {
                viewmodel.ingredients.value?.remove(it.toString())
            }
        )
        val adapter = binding.rvIngredients.adapter as IngredientAdapter
        viewmodel.ingredients.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
            }
        }
    }


    private fun setupMessageSnackbar() {
        viewmodel.message.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty() && it.isNotBlank()) {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}