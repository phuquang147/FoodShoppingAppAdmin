package xyz.daijoubuteam.foodshoppingappadmin.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.authentication.AuthActivity
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentProfileBinding
import xyz.daijoubuteam.foodshoppingappadmin.utils.Constants
import xyz.daijoubuteam.foodshoppingappadmin.utils.Constants.USERNAME


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
        setupNavigateToEditProfileFragment()
        setupNavigateToLoginFragment()
        return binding.root
    }

    private fun setupNavigateToEditProfileFragment() {
        binding.btnProfileAndAddress.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToEditProfileFragment(
                MainApplication.eatery.value?.addressEatery!!
            ))
        }
    }

    private fun setupNavigateToLoginFragment() {
        val sharedPref = this.requireActivity()
            .getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE)
        binding.btnLogout.setOnClickListener {
            with(sharedPref?.edit()) {
                this?.remove(USERNAME)
                this?.apply()
            }
            MainApplication.eatery.value = null
            val intent = Intent(activity, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}