package xyz.daijoubuteam.foodshoppingappadmin.eatery.newproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
//import com.canhub.cropper.CropImageContract
//import com.canhub.cropper.CropImageView
//import com.canhub.cropper.options
import com.google.android.material.snackbar.Snackbar
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
//    private fun setupOnProductImageClick() {
//        binding.btnSelectImage.setOnClickListener {
//            startCrop()
//        }
//    }
//    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
//        if (result.isSuccessful) {
//            val uriContent = result.uriContent
//            binding.imgDescription.setImageURI(uriContent)
//            if (uriContent != null) {
//                viewmodel.uploadProductImage(uriContent)
//            }
//
//        } else {
//            viewmodel.onShowMessage(result.error?.message)
//        }
//    }
//    private fun startCrop() {
//        cropImage.launch(
//            options {
//                setGuidelines(CropImageView.Guidelines.ON)
//                setCropShape(CropImageView.CropShape.OVAL)
//                setFixAspectRatio(true)
//            }
//        )
//    }
}