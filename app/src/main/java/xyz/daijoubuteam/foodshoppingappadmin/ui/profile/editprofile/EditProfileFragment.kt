package xyz.daijoubuteam.foodshoppingappadmin.ui.profile.editprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentEditProfileBinding
import xyz.daijoubuteam.foodshoppingappadmin.ui.profile.ProfileFragmentDirections

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding

    private val viewModel: EditProfileViewModel by lazy {
        val viewModelFactory = EditProfileViewModelFactory()
        ViewModelProvider(this, viewModelFactory)[EditProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setupNavigateToEditProfileFragment()

        return binding.root
    }
    private fun setupNavigateToEditProfileFragment() {
        binding.imageChevronleft.setOnClickListener {
            findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToNavigationProfile())
        }
    }
}