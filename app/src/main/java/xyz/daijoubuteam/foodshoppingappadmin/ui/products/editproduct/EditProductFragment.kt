package xyz.daijoubuteam.foodshoppingappadmin.ui.products.editproduct

import android.app.ActionBar
import android.content.Context
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import xyz.daijoubuteam.foodshoppingappadmin.MainActivity
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentEditProductBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.adapter.IngredientAdapter
import xyz.daijoubuteam.foodshoppingappadmin.utils.hideKeyboard


class EditProductFragment : Fragment() {

    private var uriContent: Uri? = null
    private lateinit var binding: FragmentEditProductBinding
    private lateinit var productProperty: Product
    private val viewModel: EditProductViewModel by lazy {
        val application = requireNotNull(activity).application
        productProperty = EditProductFragmentArgs.fromBundle(requireArguments()).productSelected
        val viewModelFactory = EditProductViewModelFactory(productProperty, application)
        ViewModelProvider(this, viewModelFactory)[EditProductViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            checkForNavigate()
        }
        callback.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_product, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupMessageObserver()
        setupIngredientListViewAdapter()
        setupOnProductImageClick()
        setupOnDeleteProductClick()
        val activity = requireActivity() as MainActivity
        activity.setAppBarTitle("Edit Product")

        return binding.root
    }

    private fun setupIngredientListViewAdapter() {
        binding.rvIngredients.adapter = IngredientAdapter(
            IngredientAdapter.OnClickListener {
                viewModel.selectedProperty.value?.ingredients?.remove(it)
            }
        )
        val adapter = binding.rvIngredients.adapter as IngredientAdapter
        adapter.submitList(viewModel.selectedProperty.value?.ingredients)
        viewModel.selectedProperty.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(null)
                adapter.submitList(it.ingredients)
            }
        }
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
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setCropShape(CropImageView.CropShape.RECTANGLE)
                setFixAspectRatio(true)
            }
        )
    }

    private fun setupOnDeleteProductClick() {
        binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(this.requireContext())
                .setTitle(resources.getString(R.string.delete_product))
                .setMessage(resources.getString(R.string.delete_product_alert))
                .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                    viewModel.onDeleteProduct()
                    findNavController().navigateUp()
                }
                .show()
        }
    }

    private fun checkForNavigate() {
        if (viewModel.originalProduct.name != viewModel.selectedProperty.value?.name
            || viewModel.originalProduct.description != viewModel.selectedProperty.value?.description
            || viewModel.originalProduct.newPrice != viewModel.newPrice.value.toString()
                .toDoubleOrNull()
            || viewModel.originalProduct.oldPrice != viewModel.oldPrice.value.toString()
                .toDoubleOrNull()
            || viewModel.originalProduct.ingredients != viewModel.selectedProperty.value?.ingredients
            || viewModel.originalProduct.img != viewModel.selectedProperty.value?.img
        ) {

            MaterialAlertDialogBuilder(this.requireContext())
                .setTitle(resources.getString(R.string.save_product))
                .setMessage(resources.getString(R.string.save_confirm))
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                }
                .setNegativeButton(resources.getString(R.string.no)) { _, _ ->
                    findNavController().navigateUp()
                }
                .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                    viewModel.updateProductInfo()
                    findNavController().navigateUp()
                }
                .show()
        } else {
            findNavController().navigateUp()
        }
    }
}