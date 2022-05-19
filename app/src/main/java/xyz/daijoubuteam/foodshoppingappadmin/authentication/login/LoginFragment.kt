package xyz.daijoubuteam.foodshoppingappadmin.authentication.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentLoginBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.utils.hideKeyboard

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by lazy {
        val viewModelFactory = LoginViewModelFactory()
        ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        setNavigateToForgetPasswordObserver()
        setLoginResultObserver()
        setupSoftKeyboardUI()
        setupMessageObserver()

        return binding.root
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

//    private fun setNavigateToForgetPasswordObserver() {
//        viewmodel.navigateToForgetPassword.observe(viewLifecycleOwner){
//            if(it){
//                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment())
//                viewmodel.onNavigateToForgetPasswordComplete()
//            }
//        }
//    }

    private fun setLoginResultObserver() {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            it?.let { result ->
                if (result.isSuccess) {
                    val eatery: Eatery? = result.getOrNull()
                    eatery?.let {
                        if (eatery.password == viewModel.password.value.toString()) {
                            viewModel.onLoginComplete()
                        } else
                            viewModel.onShowMessage("Wrong password")
                    }
                } else if (result.isFailure) {
                    viewModel.onShowMessage("${result.exceptionOrNull()?.message}")
                    viewModel.onLoginComplete()
                }
            }
        }
    }

    private fun setupSoftKeyboardUI() {
        binding.etUsername.setOnFocusChangeListener { view, hasFocus ->
            if (!shouldShowSoftKeyboard()) {
                hideKeyboard()
            }
        }
        binding.etPassword.setOnFocusChangeListener { view, hasFocus ->
            if (!shouldShowSoftKeyboard()) {
                hideKeyboard()
            }
        }
    }

    private fun shouldShowSoftKeyboard(): Boolean {
        return binding.etUsername.hasFocus() || binding.etPassword.hasFocus()
    }
}