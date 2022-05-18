package xyz.daijoubuteam.foodshoppingappadmin.ui.products.editproduct

import android.app.ActionBar
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.google.android.material.bottomnavigation.BottomNavigationView
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentEditProductBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.adapter.IngredientAdapter


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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_product, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        hideBottomNavigationView()
        setupIngredientListViewAdapter()
        setupOnProductImageClick()

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

    private fun hideBottomNavigationView() {
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.VISIBLE
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
}