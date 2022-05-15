package xyz.daijoubuteam.foodshoppingappadmin.authentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentLoginBinding
import xyz.daijoubuteam.foodshoppingappadmin.utils.hideKeyboard

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewmodel: LoginViewModel by lazy {
        val viewModelFactory = LoginViewModelFactory()
        ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.viewmodel = viewmodel
        binding.lifecycleOwner = viewLifecycleOwner

//        setNavigateToForgetPasswordObserver()
        setLoginResultObserver()
        setupSoftKeyboardUI()
        setupMessageObserver()

        return binding.root
    }

    private fun setupMessageObserver(){
        viewmodel.message.observe(viewLifecycleOwner){
            if(!it.isNullOrBlank()){
                hideKeyboard()
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
                viewmodel.onShowMessageComplete()
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
        viewmodel.loginResult.observe(viewLifecycleOwner) {
            it?.let { result ->
                if (result.isSuccess) {
                    viewmodel.onLoginComplete()
                    viewmodel.onShowMessage("Thành công nè")
                } else if (result.isFailure) {
                    viewmodel.onShowMessage("${result.exceptionOrNull()?.message}")
                    viewmodel.onLoginComplete()
                }
            }
        }
    }

    private fun setupSoftKeyboardUI(){
        binding.etUsername.setOnFocusChangeListener { view, hasFocus ->
            if(!shouldShowSoftKeyboard()){
                hideKeyboard()
            }
        }
        binding.etPassword.setOnFocusChangeListener { view, hasFocus ->
            if(!shouldShowSoftKeyboard()){
                hideKeyboard()
            }
        }
    }

    private fun shouldShowSoftKeyboard(): Boolean {
        return binding.etUsername.hasFocus() || binding.etPassword.hasFocus()
    }
}