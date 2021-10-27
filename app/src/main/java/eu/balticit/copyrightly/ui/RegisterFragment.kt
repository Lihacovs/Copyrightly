package eu.balticit.copyrightly.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import eu.balticit.copyrightly.R
import eu.balticit.copyrightly.databinding.FragmentRegisterBinding
import eu.balticit.copyrightly.utils.AppUtils
import eu.balticit.copyrightly.viewmodels.RegisterViewModel

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        registerViewModel =
            ViewModelProvider(this).get(RegisterViewModel::class.java)

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
                userEmail.isEmpty() -> onError(R.string.register_empty_email, view)
                !AppUtils.isValidEmail(userEmail) -> onError(R.string.register_invalid_email, view)
                userPassword.isEmpty() -> onError(R.string.register_empty_password, view)
                userPassword.length < 6 -> onError(R.string.register_short_password, view)
                userName.isEmpty() -> onError(R.string.register_empty_name, view)
                userName.length < 2 -> onError(R.string.register_short_name, view)
                userSurname.isEmpty() -> onError(R.string.register_empty_surname, view)
                userName.length < 2 -> onError(R.string.register_short_name, view)
                else -> registerViewModel.createFirebaseUser(userEmail,userPassword)

            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun onError(resId: Int, view: View) {
        Snackbar.make(view, getString(resId), Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }
}