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
        setupSoftKeyboardUI()
        setupIngredientListViewAdapter()
        setupOnProfileEditUserAvatarClick()

        return binding.root
    }

    private fun setupIngredientListViewAdapter() {
        binding.rvIngredients.adapter = IngredientAdapter(
            IngredientAdapter.OnClickListener {

            }
        )
        val adapter = binding.rvIngredients.adapter as IngredientAdapter
        viewModel.categoryList.observe(viewLifecycleOwner) {
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

    private fun setupSoftKeyboardUI() {
        binding.profileEditFirstNameTextField.editText?.setOnFocusChangeListener { view, hasFocus ->
            if (!shouldShowSoftKeyboard()) {
                hideKeyboard()
            }
        }
        binding.profileEditLastNameTextField.editText?.setOnFocusChangeListener { view, hasFocus ->
            if (!shouldShowSoftKeyboard()) {
                hideKeyboard()
            }
        }
        binding.profileEditPhoneNumber.editText?.setOnFocusChangeListener { view, hasFocus ->
            if (!shouldShowSoftKeyboard()) {
                hideKeyboard()
            }
        }
    }

    private fun shouldShowSoftKeyboard(): Boolean {
        return binding.profileEditFirstNameTextField.editText?.hasFocus() == true ||
                binding.profileEditLastNameTextField.editText?.hasFocus() == true ||
                binding.profileEditPhoneNumber.editText?.hasFocus() == true
    }

    private fun setupOnProfileEditUserAvatarClick() {
        binding.profileEditUserAvatar.setOnClickListener {
            startCrop()
        }
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uriContent = result.uriContent
            binding.profileEditUserAvatar.setImageURI(uriContent)
            if (uriContent != null) {
                viewmodel.uploadUserAvatar(uriContent)
            }

        } else {
            viewmodel.onShowMessage(result.error?.message)
        }
    }

    private fun startCrop() {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setCropShape(CropImageView.CropShape.OVAL)
                setFixAspectRatio(true)
            }
        )
    }
}