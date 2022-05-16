package xyz.daijoubuteam.foodshoppingappadmin.ui.products.editproduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentEditProductBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Product

class EditProductFragment : Fragment() {
    private lateinit var binding: FragmentEditProductBinding
    private lateinit var productProperty: Product
    private val viewModel: EditProductViewModel by lazy {
        val application = requireNotNull(activity).application
        productProperty = EditProductFragmentArgs.fromBundle(requireArguments()).productSelected
        val viewModelFactory = EditProductViewModelFactory(productProperty,application)
        ViewModelProvider(this, viewModelFactory)[EditProductViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_product, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }
}