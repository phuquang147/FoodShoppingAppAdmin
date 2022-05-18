package xyz.daijoubuteam.foodshoppingappadmin.ui.products.newproduct

//import com.canhub.cropper.CropImageContract
//import com.canhub.cropper.CropImageView
//import com.canhub.cropper.options
import android.app.ActionBar
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentNewProductBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.adapter.IngredientAdapter

class NewProductFragment : Fragment() {

    var uriContent: Uri?
    private lateinit var binding: FragmentNewProductBinding
    private val viewmodel: NewProductViewModel by lazy {
        val factory = NewProductViewModelFactory()
        ViewModelProvider(this, factory)[NewProductViewModel::class.java]
    }
    init {
        uriContent = null
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
        hideBottomNavigationView()
        setupOnProductImageClick()
        return binding.root
    }

    private fun setupIngredientListViewAdapter() {
        binding.rvIngredients.adapter = IngredientAdapter(
            IngredientAdapter.OnClickListener {
         //       viewmodel.removeIngredient(it)
            }
        )
        val adapter = binding.rvIngredients.adapter as IngredientAdapter
        adapter.submitList(viewmodel.ingredients.value)
        viewmodel.ingredients.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(null)
                adapter.submitList(it)
                Log.i("it", it.size.toString())
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
    private fun setupOnProductImageClick() {
        binding.btnSelectImage.setOnClickListener {
            startCrop()
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

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            uriContent = result.uriContent
            binding.imgDescription.setImageURI(uriContent)
            binding.cardNewProductAvatar.layoutParams.width = ActionBar.LayoutParams.MATCH_PARENT
            if (uriContent != null) {
                viewmodel.uploadProductImage(uriContent!!)
            }

        } else {
            viewmodel.onShowMessage(result.error?.message)
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