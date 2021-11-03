package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentLoginBinding
import eu.balticit.copyrightly.utils.AppUtils
import eu.balticit.copyrightly.viewmodels.LoginViewModel

class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.btnLoginServer.setOnClickListener { view ->
            hideKeyboard()
            val email: String = binding.etLoginEmail.text.toString()
            val password: String = binding.etLoginPassword.text.toString()
            when {
                email.isEmpty() -> showSnackbar(R.string.login_empty_email, view)
                !AppUtils.isValidEmail(email) -> showSnackbar(R.string.login_invalid_email, view)
                password.isEmpty() -> showSnackbar(R.string.login_empty_password, view)
                password.length < 6 -> showSnackbar(R.string.login_short_password, view)
                else ->{
                    showLoading()
                    loginViewModel.signInFirebaseUser(email, password)
                }
            }
        }

        loginViewModel.user.observe(viewLifecycleOwner, Observer {
            hideLoading()
        })

        loginViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            hideLoading()
            showSnackbar(it, root)
        })

        binding.btnLoginGoogle.setOnClickListener { view ->
            Snackbar.make(view, "Login through Google", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.btnLoginFacebook.setOnClickListener { view ->
            Snackbar.make(view, "Login through Facebook", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.btnLoginRegister.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_login_to_nav_register)
        }

        /*val textView: TextView = binding.textLogin
        loginViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}