package xyz.daijoubuteam.foodshoppingappadmin.ui.profile.editprofile

import android.app.ActionBar
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import xyz.daijoubuteam.foodshoppingappadmin.MainActivity
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentEditProfileBinding
import xyz.daijoubuteam.foodshoppingappadmin.utils.hideKeyboard

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private var uriContent: Uri? = null
    private val viewModel: EditProfileViewModel by lazy {
        var eateryAddress = EditProfileFragmentArgs.fromBundle(requireArguments()).eateryAddress
        val viewModelFactory = EditProfileViewModelFactory(eateryAddress)
        ViewModelProvider(this, viewModelFactory)[EditProfileViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            checkForNavigate()
        }
        callback.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupOnProductImageClick()
        setupMessageObserver()
        setupOnChangeAddressClick()
        requireActivity()
        val activity = requireActivity() as MainActivity
        activity.setAppBarTitle("Eatery Information")

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.VISIBLE
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

    private fun setupOnProductImageClick() {
        binding.btnSelectImage.setOnClickListener {
            startCrop()
        }
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            uriContent = result.uriContent
            binding.imgDescription.setImageURI(uriContent)
            binding.imgDescription.layoutParams.width = ActionBar.LayoutParams.MATCH_PARENT
            if (uriContent != null) {
                viewModel.uploadProductImage(uriContent!!)
            }

        } else {
            viewModel.onShowMessage(result.error?.message)
        }
    }

    private fun startCrop() {
        Log.i("cropimage", cropImage.toString())
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setCropShape(CropImageView.CropShape.RECTANGLE)
                setFixAspectRatio(false)
            }
        )
    }

    private fun checkForNavigate() {
        if (viewModel.originalEatery?.name != viewModel.eatery.value?.name
            || viewModel.originalEatery?.description != viewModel.eatery.value?.description
            || viewModel.originalEatery?.work_time != viewModel.eatery.value?.work_time
            || viewModel.originalEatery?.addressEatery != viewModel.eatery.value?.addressEatery
            || viewModel.originalEatery?.photoUrl != viewModel.eatery.value?.photoUrl
        ) {
            MaterialAlertDialogBuilder(this.requireContext())
                .setTitle(resources.getString(R.string.save_eatery))
                .setMessage(resources.getString(R.string.save_confirm))
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                }
                .setNegativeButton(resources.getString(R.string.no)) { _, _ ->
                    findNavController().navigateUp()
                }
                .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                    if (viewModel.updateProfileInfo())
                        findNavController().navigateUp()
                    else dialog.cancel()
                }
                .show()
        } else {
            findNavController().navigateUp()
        }
    }

    private fun setupOnChangeAddressClick() {
        binding.btnChangeAddress.setOnClickListener {
            findNavController().navigate(
                EditProfileFragmentDirections.actionEditProfileFragmentToSelectLocationFragment(
                    MainApplication.eatery.value?.addressEatery?.copy()
                )
            )
        }
    }
}