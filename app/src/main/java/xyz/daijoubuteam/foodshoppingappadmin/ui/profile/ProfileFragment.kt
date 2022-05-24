package xyz.daijoubuteam.foodshoppingappadmin.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentEditProductBinding
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentProfileBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.ProductsViewModelFactory
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.editproduct.EditProductFragmentArgs
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.editproduct.EditProductViewModel
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.editproduct.EditProductViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by lazy {
        val viewModelFactory = ProfileViewModelFactory()
        ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }
}