package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.base.BaseFragment
import eu.balticit.copyrightly.databinding.FragmentRegisterBinding
import eu.balticit.copyrightly.utils.AppUtils
import eu.balticit.copyrightly.viewmodels.LoginViewModel

class RegisterFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tvRegisterTerms.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_register_to_nav_about)
        }

        binding.btnRegisterRegister.setOnClickListener { view ->
            val userEmail: String = binding.etRegisterEmail.text.toString()
            val userPassword: String = binding.etRegisterPassword.text.toString()
            val userName: String = binding.etRegisterName.text.toString()
            val userSurname: String = binding.etRegisterSurname.text.toString()
            //Validates register data
            when {
                userEmail.isEmpty() -> showSnackbar(R.string.register_empty_email, view)
                !AppUtils.isValidEmail(userEmail) -> showSnackbar(
                    R.string.register_invalid_email,
                    view
                )
                userPassword.isEmpty() -> showSnackbar(R.string.register_empty_password, view)
                userPassword.length < 6 -> showSnackbar(R.string.register_short_password, view)
                userName.isEmpty() -> showSnackbar(R.string.register_empty_name, view)
                userName.length < 2 -> showSnackbar(R.string.register_short_name, view)
                userSurname.isEmpty() -> showSnackbar(R.string.register_empty_surname, view)
                userName.length < 2 -> showSnackbar(R.string.register_short_name, view)
                else -> {
                    hideKeyboard()
                    showLoading()
                    loginViewModel.createFirebaseUser(
                        userEmail,
                        userPassword,
                        userName,
                        userSurname
                    )
                }
            }
        }

        loginViewModel.user.observe(viewLifecycleOwner, {
            hideLoading()
        })

        loginViewModel.errorMessage.observe(viewLifecycleOwner, {
            hideLoading()
            showSnackbar(it, root)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}